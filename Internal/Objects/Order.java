// File: FinalProject/Internal/Objects/Order.java
package FinalProject.Internal.Objects;

import FinalProject.Internal.Observers.OrderObserver;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private static Order instance;
    private List<Dish> dishes;
    private List<OrderObserver> observers;

    private Order() {
        dishes = new ArrayList<>();
        observers = new ArrayList<>();
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
}
