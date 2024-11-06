package FinalProject.Internal.Objects;

public class Dish {
    private String name;
    private double price;
    private DishCategory category;
    public Dish(String name, double price, DishCategory category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public DishCategory getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return name + " - " + price + " KZT";
    }
}
