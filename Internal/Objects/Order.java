package FinalProject.Internal.Objects;

import FinalProject.Internal.Observers.OrderObserver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order { // Composite
    private static Order instance;
    private List<Dish> dishes;
    private final List<OrderObserver> observers;
    private static final List<Order> allOrders = new ArrayList<>();  // История заказов
    private final Date date;

    private Order() {
        dishes = new ArrayList<>();
        observers = new ArrayList<>();
        this.date = new Date();
    }

    public static Order getInstance() {
        if (instance == null) {
            instance = new Order();
        }
        return instance;
    }

    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (OrderObserver observer : observers) {
            observer.updateOrder();
        }
    }

    public void addDish(Dish dish) {
        dishes.add(dish);
        notifyObservers();
    }

    public void removeDish(Dish dish) {
        dishes.remove(dish);
        notifyObservers();
    }

    public void clearOrder() {
        dishes.clear();
        notifyObservers();
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public double calculateTotal() {
        return dishes.stream().mapToDouble(Dish::getPrice).sum();
    }

    // Метод для добавления текущего заказа в историю
    public void saveOrderToHistory() {
        allOrders.add(this);
    }

    // Метод для получения всех заказов
    public static List<Order> getAllOrders() {
        return allOrders;
    }

    // Метод для создания копии текущего заказа (для сохранения в истории)
    public Order copy() {
        Order newOrder = new Order();
        newOrder.dishes = new ArrayList<>(this.dishes);
        return newOrder;
    }

    public Date getDate() {
        return date;
    }
}
