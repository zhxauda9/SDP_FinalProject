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
        frame = new JFrame("Онлайн-заказ ресторана");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 400);
        frame.setLayout(new FlowLayout());

        // Category filter
        JLabel categoryLabel = new JLabel("Выберите категорию:");
        frame.add(categoryLabel);

        categoryComboBox = new JComboBox<>(new String[] {"Все", "Основные блюда", "Напитки", "Закуски", "Десерты"});
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDishComboBox();
            }
        });
        frame.add(categoryComboBox);

        JLabel menuLabel = new JLabel("Выберите блюдо:");
        frame.add(menuLabel);

        dishComboBox = new JComboBox<>(menu.toArray(new Dish[0]));
        frame.add(dishComboBox);

        JButton addButton = new JButton("Добавить в заказ");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dish selectedDish = (Dish) dishComboBox.getSelectedItem();
                currentOrder.addDish(selectedDish);
            }
        });
        frame.add(addButton);

        JButton removeButton = new JButton("Удалить из заказа");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dish selectedDish = (Dish) dishComboBox.getSelectedItem();
                currentOrder.removeDish(selectedDish);
            }
        });
        frame.add(removeButton);

        orderTextArea = new JTextArea(10, 30);
        orderTextArea.setEditable(false);
        frame.add(new JScrollPane(orderTextArea));

        // Register the UITextObserver to listen to changes in the order
        UITextObserver textObserver = new UITextObserver(orderTextArea);
        currentOrder.addObserver(textObserver);

        JButton finalizeButton = new JButton("Оформить заказ");
        finalizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double total = currentOrder.calculateTotal() + currentOrder.calculateTotal() * 0.1;
                JOptionPane.showMessageDialog(frame, "Общая сумма заказа + 10% (обслуживание): " + total + " тг");

                // Сохраняем заказ в истории
                Order savedOrder = currentOrder.copy();
                savedOrder.saveOrderToHistory(); // Добавляем в историю заказов

                // Очищаем текущий заказ
                currentOrder.clearOrder();
            }
        });
        frame.add(finalizeButton);

        JButton clearButton = new JButton("Удалить все заказы");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentOrder.clearOrder();
            }
        });
        frame.add(clearButton);


        // Adding History buttton
        JButton historyButton = new JButton("История заказов");
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showOrderHistory();
            }
        });
        frame.add(historyButton);

        frame.setVisible(true);
    }
//б
    private void updateDishComboBox() {
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        List<Dish> filteredDishes = menu;

        if (selectedCategory.equals("Основные блюда")) {
            filteredDishes = menu.stream().filter(dish -> dish.getCategory() == DishCategory.MAIN_COURSE).collect(Collectors.toList());
        } else if (selectedCategory.equals("Напитки")) {
            filteredDishes = menu.stream().filter(dish -> dish.getCategory() == DishCategory.DRINK).collect(Collectors.toList());
        } else if (selectedCategory.equals("Закуски")) {
            filteredDishes = menu.stream().filter(dish -> dish.getCategory() == DishCategory.SNACK).collect(Collectors.toList());
        } else if (selectedCategory.equals("Десерты")) {
            filteredDishes = menu.stream().filter(dish -> dish.getCategory() == DishCategory.DESSERT).collect(Collectors.toList());
        }

        dishComboBox.setModel(new DefaultComboBoxModel<>(filteredDishes.toArray(new Dish[0])));
    }

    private void showOrderHistory() {
        JFrame historyFrame = new JFrame("История заказов");
        historyFrame.setSize(500, 400);

        JTextArea historyTextArea = new JTextArea(15, 40);
        historyTextArea.setEditable(false);

        // Пример: здесь нужно адаптировать список заказов через адаптер
        OrderHistoryAdapter historyAdapter = new OrderHistoryAdapter(Order.getAllOrders()); // Получаем все заказы
        historyAdapter.updateOrderHistoryDisplay(historyTextArea);

        historyFrame.add(new JScrollPane(historyTextArea));
        historyFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RestaurantAppSwing::new);
    }
}
