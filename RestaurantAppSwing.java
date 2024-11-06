package FinalProject;

import FinalProject.Internal.Adapter.OrderHistoryAdapter;
import FinalProject.Internal.Factory.*;
import FinalProject.Internal.Objects.Dish;
import FinalProject.Internal.Objects.DishCategory;
import FinalProject.Internal.Objects.Order;
import FinalProject.Internal.Observers.UITextObserver;
import FinalProject.Internal.RestaurantController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantAppSwing {
    private JFrame frame;
    private JComboBox<Dish> dishComboBox;
    private JTextArea orderTextArea;
    private List<Dish> menu;
    private RestaurantController controller;
    private JComboBox<String> categoryComboBox;

    public RestaurantAppSwing() {
        Order currentOrder = Order.getInstance();
        this.controller = new RestaurantController(currentOrder);
        this.menu = new ArrayList<>();

        initializeMenu();
        initializeUI();

        // Register observer for real-time order updates
        UITextObserver textObserver = new UITextObserver(orderTextArea);
        currentOrder.addObserver(textObserver);
    }

    private void initializeMenu() {
        DishFactory mainDishFactory = new MainDishFactory();
        DishFactory drinkFactory = new DrinkFactory();
        DishFactory snackFactory = new SnackFactory();
        DishFactory dessertFactory = new DessertFactory();

        //main dish
        menu.add(mainDishFactory.createDish("Pasta \"Alfredo\"", 1290));
        menu.add(mainDishFactory.createDish("Burger x2 cheese chicken", 1790));
        menu.add(mainDishFactory.createDish("Pizza kazakh", 1500));
        menu.add(mainDishFactory.createDish("Lasagna", 2590));
        menu.add(mainDishFactory.createDish("Xinkali", 1200));
        menu.add(mainDishFactory.createDish("Salmon Steak", 2300));



        //drink
        menu.add(drinkFactory.createDish("Tashkent tea", 900));
        menu.add(drinkFactory.createDish("Espresso Coffee", 600));
        menu.add(drinkFactory.createDish("Fruit tea", 500));
        menu.add(drinkFactory.createDish("Orange fresh", 500));
        menu.add(drinkFactory.createDish("Strawberry milkshake", 500));


        //dessert
        menu.add(dessertFactory.createDish("Milky Girl", 1200));
        menu.add(dessertFactory.createDish("Ice-cream", 700));
        menu.add(dessertFactory.createDish("Maccaron", 1100));
        menu.add(dessertFactory.createDish("Croissant", 1100));
        menu.add(dessertFactory.createDish("Pistache roulet", 1100));


        //snack
        menu.add(snackFactory.createDish("Chickpea Salad", 1190));
        menu.add(snackFactory.createDish("Broccoli Apple Salad", 1290));
        menu.add(snackFactory.createDish("Caeser Salad", 1790));
        menu.add(snackFactory.createDish("Crab Salad", 1090));
    }

    private void initializeUI() {
        frame = new JFrame("Online-order restaurant");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 400);
        frame.setLayout(new FlowLayout());

        JLabel categoryLabel = new JLabel("Choose category:");
        frame.add(categoryLabel);

        categoryComboBox = new JComboBox<>(new String[]{"All", "Main dish", "Drinks", "Shacks", "Desserts"});
        categoryComboBox.addActionListener(e -> updateDishComboBox());
        frame.add(categoryComboBox);

        JLabel menuLabel = new JLabel("Choose dish:");
        frame.add(menuLabel);

        dishComboBox = new JComboBox<>(menu.toArray(new Dish[0]));
        frame.add(dishComboBox);

        JButton addButton = new JButton("Add to order");
        addButton.addActionListener(e -> {
            Dish selectedDish = (Dish) dishComboBox.getSelectedItem();
            controller.addDishToOrder(selectedDish);
        });
        frame.add(addButton);

        JButton removeButton = new JButton("Delete from order");
        removeButton.addActionListener(e -> {
            Dish selectedDish = (Dish) dishComboBox.getSelectedItem();
            controller.removeDishFromOrder(selectedDish);
        });
        frame.add(removeButton);

        orderTextArea = new JTextArea(10, 30);
        orderTextArea.setEditable(false);
        frame.add(new JScrollPane(orderTextArea));

        JButton finalizeButton = new JButton("Make order");
        finalizeButton.addActionListener(e -> {
            double total = controller.finalizeOrder();
            JOptionPane.showMessageDialog(frame, "Total amount + 10% (service): " + total + " KZT");
        });
        frame.add(finalizeButton);

        JButton clearButton = new JButton("Delete all orders");
        clearButton.addActionListener(e -> controller.clearOrder());
        frame.add(clearButton);

        JButton historyButton = new JButton("Order History");
        historyButton.addActionListener(e -> showOrderHistory());
        frame.add(historyButton);

        frame.setVisible(true);
    }

    private void updateDishComboBox() {
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        List<Dish> filteredDishes = menu;

        if ("Main dishes".equals(selectedCategory)) {
            filteredDishes = menu.stream().filter(dish -> dish.getCategory() == DishCategory.MAIN_COURSE).collect(Collectors.toList());
        } else if ("Drinks".equals(selectedCategory)) {
            filteredDishes = menu.stream().filter(dish -> dish.getCategory() == DishCategory.DRINK).collect(Collectors.toList());
        } else if ("Snacks".equals(selectedCategory)) {
            filteredDishes = menu.stream().filter(dish -> dish.getCategory() == DishCategory.SNACK).collect(Collectors.toList());
        } else if ("Desserts".equals(selectedCategory)) {
            filteredDishes = menu.stream().filter(dish -> dish.getCategory() == DishCategory.DESSERT).collect(Collectors.toList());
        }

        dishComboBox.setModel(new DefaultComboBoxModel<>(filteredDishes.toArray(new Dish[0])));
    }

    private void showOrderHistory() {
        JFrame historyFrame = new JFrame("Order History");
        historyFrame.setSize(500, 400);

        JTextArea historyTextArea = new JTextArea(15, 40);
        historyTextArea.setEditable(false);

        OrderHistoryAdapter historyAdapter = new OrderHistoryAdapter(controller.getOrderHistory());
        historyAdapter.updateOrderHistoryDisplay(historyTextArea);

        historyFrame.add(new JScrollPane(historyTextArea));
        historyFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RestaurantAppSwing::new);
    }
}
