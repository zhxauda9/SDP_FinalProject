package FinalProject;

import FinalProject.Internal.Factory.*;
import FinalProject.Internal.Objects.Dish;
import FinalProject.Internal.Objects.DishCategory;
import FinalProject.Internal.Objects.Order;
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

        menu.add(mainDishFactory.createDish("Паста", 1200));
        menu.add(mainDishFactory.createDish("Пицца", 1500));
        menu.add(snackFactory.createDish("Салат", 800));
        menu.add(drinkFactory.createDish("Кофе", 600));
        menu.add(drinkFactory.createDish("Чай", 500));
        menu.add(dessertFactory.createDish("Торт", 1200));
        menu.add(dessertFactory.createDish("Мороженое", 700));
    }

    private void initializeUI() {
        frame = new JFrame("Онлайн-заказ ресторана");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
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
                updateOrderTextArea();
            }
        });
        frame.add(addButton);

        orderTextArea = new JTextArea(10, 30);
        orderTextArea.setEditable(false);
        frame.add(new JScrollPane(orderTextArea));

        JButton finalizeButton = new JButton("Оформить заказ");
        finalizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double total = currentOrder.calculateTotal();
                JOptionPane.showMessageDialog(frame, "Общая сумма заказа: " + total + " тг");
            }
        });
        frame.add(finalizeButton);

        JButton clearButton = new JButton("Удалить все заказы");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentOrder.clearOrder();
                updateOrderTextArea();
            }
        });
        frame.add(clearButton);

        frame.setVisible(true);
    }

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

    private void updateOrderTextArea() {
        StringBuilder orderDetails = new StringBuilder("Текущий заказ:\n");
        for (Dish dish : currentOrder.getDishes()) {
            orderDetails.append(dish.getName()).append(" - ").append(dish.getPrice()).append(" тг\n");
        }
        orderTextArea.setText(orderDetails.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RestaurantAppSwing::new);
    }
}
