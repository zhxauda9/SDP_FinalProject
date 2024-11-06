package FinalProject.Internal.Factory;

import FinalProject.Internal.Objects.Dish;
import FinalProject.Internal.Objects.DishCategory;

public class DessertFactory extends DishFactory {
    @Override
    public Dish createDish(String name, double price) {
        return new Dish(name, price, DishCategory.DESSERT);
    }
}

