package FinalProject;

import FinalProject.Internal.Adapter.OrderHistoryAdapter;
import FinalProject.Internal.Factory.*;
import FinalProject.Internal.Objects.Dish;
import FinalProject.Internal.Objects.DishCategory;
import FinalProject.Internal.Objects.Order;
import FinalProject.Internal.Observers.UITextObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantAppSwing {
    private JFrame frame;
    private JComboBox<Dish> dishComboBox;
    private JTextArea orderTextArea;
    private List<Dish> menu;
    private Order currentOrder;
    private JComboBox<String> categoryComboBox;

    public RestaurantAppSwing() {
        currentOrder = Order.getInstance();
        menu = new ArrayList<>();
        initializeMenu();
        initializeUI();
    }

    private void initializeMenu() {
        DishFactory mainDishFactory = new MainDishFactory();
        DishFactory drinkFactory = new DrinkFactory();
        DishFactory snackFactory = new SnackFactory();
        DishFactory dessertFactory = new DessertFactory();

        // main dish
        menu.add(mainDishFactory.createDish("Pasta \"Alfredo\" 🍝", 1290));
        menu.add(mainDishFactory.createDish("Burger x2 cheese chicken 🍔", 1790));
        menu.add(mainDishFactory.createDish("Pizza Kazakh 🍕", 1500));
        menu.add(mainDishFactory.createDish("Lasagna 🍲", 2590));
        menu.add(mainDishFactory.createDish("Xinkali 🥟", 1200));
        menu.add(mainDishFactory.createDish("Salmon Steak 🐟", 2300));

        // drink
        menu.add(drinkFactory.createDish("Tashkent tea 🍵", 900));
        menu.add(drinkFactory.createDish("Espresso Coffee ☕", 600));
        menu.add(drinkFactory.createDish("Fruit tea 🍹", 500));
        menu.add(drinkFactory.createDish("Orange fresh 🍊", 500));
        menu.add(drinkFactory.createDish("Strawberry milkshake 🍓", 500));

        // dessert
        menu.add(dessertFactory.createDish("Milky Girl 🍰", 1200));
        menu.add(dessertFactory.createDish("Ice-cream 🍦", 700));
        menu.add(dessertFactory.createDish("Maccaron 🍪", 1100));
        menu.add(dessertFactory.createDish("Croissant 🥐", 1100));
        menu.add(dessertFactory.createDish("Pistache roulade 🍰", 1100));

        // snack
        menu.add(snackFactory.createDish("Chickpea Salad 🥗", 1190));
        menu.add(snackFactory.createDish("Broccoli Apple Salad 🥦", 1290));
        menu.add(snackFactory.createDish("Caeser Salad 🥗", 1790));
        menu.add(snackFactory.createDish("Crab Salad 🦀", 1090));
    }

    private void initializeUI() {
        frame = new JFrame("Restaurant Online Order");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);

        // Adding external padding to the frame
        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new BorderLayout());
        outerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new GridLayout(0, 2, 20, 20)); // (rows, columns, hgap, vgap) with 20px padding
        outerPanel.add(innerPanel, BorderLayout.CENTER);
        frame.add(outerPanel);

        // Create components with size and font settings
        Font largeFont = new Font("Arial", Font.PLAIN, 18);
        Font smallFont = new Font("Arial", Font.PLAIN, 14);

        JLabel categoryLabel = new JLabel("Select Category:");
        categoryLabel.setPreferredSize(new Dimension(150, 50)); // Increased for text
        categoryLabel.setFont(largeFont);  // Enlarged font
        innerPanel.add(categoryLabel);

        categoryComboBox = new JComboBox<>(new String[]{"All", "Main Dishes", "Drinks", "Snacks", "Desserts"});
        categoryComboBox.setPreferredSize(new Dimension(150, 50)); // Increased for better visibility
        categoryComboBox.setFont(smallFont); // Smaller font for the dropdown list

        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDishComboBox();
            }
        });
        innerPanel.add(categoryComboBox);

        JLabel menuLabel = new JLabel("Select Dish:");
        menuLabel.setPreferredSize(new Dimension(150, 50)); // Increased for text
        menuLabel.setFont(largeFont); // Enlarged font
        innerPanel.add(menuLabel);

        dishComboBox = new JComboBox<>(menu.toArray(new Dish[0]));
        dishComboBox.setPreferredSize(new Dimension(150, 50)); // Increased for better visibility
        dishComboBox.setFont(smallFont); // Smaller font for the dropdown list
        innerPanel.add(dishComboBox);

        JButton addButton = new JButton("Add to Order");
        addButton.setPreferredSize(new Dimension(120, 30)); // Reduced button size
        addButton.setFont(smallFont); // Button font
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dish selectedDish = (Dish) dishComboBox.getSelectedItem();
                currentOrder.addDish(selectedDish);
            }
        });
        innerPanel.add(addButton);

        JButton removeButton = new JButton("Remove from Order");
        removeButton.setPreferredSize(new Dimension(120, 40)); // Reduced button size
        removeButton.setFont(smallFont); // Button font
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dish selectedDish = (Dish) dishComboBox.getSelectedItem();
                currentOrder.removeDish(selectedDish);
            }
        });
        innerPanel.add(removeButton);

        JLabel orderLabel = new JLabel("Your Order:");
        orderLabel.setPreferredSize(new Dimension(150, 50)); // Increased for text
        orderLabel.setFont(largeFont); // Enlarged font
        innerPanel.add(orderLabel);

        orderTextArea = new JTextArea(10, 30);
        orderTextArea.setEditable(false);
        orderTextArea.setPreferredSize(new Dimension(600, 200)); // Made text area larger
        orderTextArea.setFont(new Font("Arial", Font.PLAIN, 16));  // Font for the order text
        innerPanel.add(new JScrollPane(orderTextArea));

        // Attach UITextObserver
        UITextObserver textObserver = new UITextObserver(orderTextArea);
        currentOrder.addObserver(textObserver);

        JButton finalizeButton = new JButton("Finalize Order");
        finalizeButton.setPreferredSize(new Dimension(120, 40)); // Reduced button size
        finalizeButton.setFont(smallFont); // Button font
        finalizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double total = currentOrder.calculateTotal() + currentOrder.calculateTotal() * 0.1;
                JOptionPane.showMessageDialog(frame, "Total Order + 10% (Service): " + total + " KZT");

                Order savedOrder = currentOrder.copy();
                savedOrder.saveOrderToHistory();
                currentOrder.clearOrder();
            }
        });
        innerPanel.add(finalizeButton);

        JButton clearButton = new JButton("Clear All Orders");
        clearButton.setPreferredSize(new Dimension(120, 40)); // Reduced button size
        clearButton.setFont(smallFont); // Button font
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentOrder.clearOrder();
            }
        });
        innerPanel.add(clearButton);

        JButton historyButton = new JButton("Order History");
        historyButton.setPreferredSize(new Dimension(120, 40)); // Reduced button size
        historyButton.setFont(smallFont); // Button font
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showOrderHistory();
            }
        });
        innerPanel.add(historyButton);

        frame.setVisible(true);
    }

    private void updateDishComboBox() {
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        List<Dish> filteredDishes = menu;

        if (selectedCategory.equals("Main Dishes")) {
            filteredDishes = menu.stream().filter(dish -> dish.getCategory() == DishCategory.MAIN_COURSE).collect(Collectors.toList());
        } else if (selectedCategory.equals("Drinks")) {
            filteredDishes = menu.stream().filter(dish -> dish.getCategory() == DishCategory.DRINK).collect(Collectors.toList());
        } else if (selectedCategory.equals("Snacks")) {
            filteredDishes = menu.stream().filter(dish -> dish.getCategory() == DishCategory.SNACK).collect(Collectors.toList());
        } else if (selectedCategory.equals("Desserts")) {
            filteredDishes = menu.stream().filter(dish -> dish.getCategory() == DishCategory.DESSERT).collect(Collectors.toList());
        }

        dishComboBox.setModel(new DefaultComboBoxModel<>(filteredDishes.toArray(new Dish[0])));
    }

    private void showOrderHistory() {
        JFrame historyFrame = new JFrame("History of orders");
        historyFrame.setSize(500, 400);

        JTextArea historyTextArea = new JTextArea(15, 40);
        historyTextArea.setEditable(false);
        OrderHistoryAdapter historyAdapter = new OrderHistoryAdapter(Order.getAllOrders());
        historyAdapter.updateOrderHistoryDisplay(historyTextArea);

        historyFrame.add(new JScrollPane(historyTextArea));
        historyFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RestaurantAppSwing();
            }
        });
    }
}
