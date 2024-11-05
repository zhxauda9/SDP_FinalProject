package FinalProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RestaurantAppSwing {
    private JFrame frame;
    private JComboBox<Dish> dishComboBox;
    private JTextArea orderTextArea;
    private List<Dish> menu;
    private Order currentOrder;

    public RestaurantAppSwing() {
        menu = new ArrayList<>();
        currentOrder = new Order();
        initializeMenu();
        initializeUI();
    }

    private void initializeMenu() {
        menu.add(new Dish("Паста", 1200));
        menu.add(new Dish("Пицца", 1500));
        menu.add(new Dish("Салат", 800));
    }

    private void initializeUI() {
        frame = new JFrame("Онлайн-заказ ресторана");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

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

        frame.setVisible(true);
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

class Dish {
    private String name;
    private double price;

    public Dish(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " - " + price + " тг";
    }
}

class Order {
    private List<Dish> dishes;

    public Order() {
        dishes = new ArrayList<>();
    }

    public void addDish(Dish dish) {
        dishes.add(dish);
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public double calculateTotal() {
        return dishes.stream().mapToDouble(Dish::getPrice).sum();
    }
}

