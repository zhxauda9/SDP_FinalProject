package FinalProject.Internal.Factory;

import FinalProject.Internal.Objects.Dish;
import FinalProject.Internal.Objects.DishCategory;

public class MainDishFactory extends DishFactory {
    @Override
    public Dish createDish(String name, double price) {
        Object Category;
        return new Dish(name, price, DishCategory.MAIN_COURSE);
    }
}
