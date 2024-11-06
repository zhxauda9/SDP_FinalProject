package FinalProject.Internal;

import FinalProject.Internal.Objects.Dish;
import FinalProject.Internal.Objects.Order;
import java.util.List;

public class RestaurantController {
    private Order currentOrder;

    public RestaurantController(Order order) {
        this.currentOrder = order;
    }

    // Adds a dish to the current order
    public void addDishToOrder(Dish dish) {
        if (dish != null) {
            currentOrder.addDish(dish);
        }
    }

    // Removes a dish from the current order
    public void removeDishFromOrder(Dish dish) {
        if (dish != null) {
            currentOrder.removeDish(dish);
        }
    }

    public double finalizeOrder() {
        double total = currentOrder.calculateTotal() + currentOrder.calculateTotal() * 0.1;

        // Create a copy of the current order to save to history
        Order savedOrder = currentOrder.copy();
        savedOrder.saveOrderToHistory();

        // Clear the current order after finalizing
        currentOrder.clearOrder();
        return total;
    }

    // Clears the current order
    public void clearOrder() {
        currentOrder.clearOrder();
    }

    // Gets the order history from all saved orders
    public List<Order> getOrderHistory() {
        return Order.getAllOrders();
    }
}
