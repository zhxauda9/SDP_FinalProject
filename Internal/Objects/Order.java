package FinalProject.Internal.Objects;

import FinalProject.Internal.Objects.Dish;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private static Order instance;
    private List<Dish> dishes;

    private Order() {
        dishes = new ArrayList<>();
    }

    public static Order getInstance() {
        if (instance == null) {
            instance = new Order();
        }
        return instance;
    }

    public void addDish(Dish dish) {
        dishes.add(dish);
    }

    public void clearOrder() {
        dishes.clear();
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public double calculateTotal() {
        return dishes.stream().mapToDouble(Dish::getPrice).sum();
    }
}


