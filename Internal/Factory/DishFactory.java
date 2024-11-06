package FinalProject.Internal.Factory;

import FinalProject.Internal.Objects.Dish;
import FinalProject.Internal.Objects.DishCategory;

public abstract class DishFactory {
    public abstract Dish createDish(String name, double price);
}
