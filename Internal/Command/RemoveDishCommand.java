package FinalProject.Internal.Command;

import FinalProject.Internal.Objects.Dish;
import FinalProject.Internal.Objects.Order;

public class RemoveDishCommand implements OrderCommand {
    private Order order;
    private Dish dish;

    //command to remove dish command
    public RemoveDishCommand(Order order, Dish dish) {
        this.order = order;
        this.dish = dish;
    }

    @Override
    public void execute() {
        order.removeDish(dish);
    }
}
