package FinalProject.Internal.Command;

import FinalProject.Internal.Objects.Dish;
import FinalProject.Internal.Objects.Order;

public class AddDishCommand implements OrderCommand {
    private Order order;
    private Dish dish;

    // command to add dishes
    public AddDishCommand(Order order, Dish dish) {
        this.order = order;
        this.dish = dish;
    }

    @Override
    public void execute() {
        order.addDish(dish);
    }
}
