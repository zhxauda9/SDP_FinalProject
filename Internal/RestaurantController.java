package FinalProject.Internal;

import FinalProject.Internal.Objects.Dish;
import FinalProject.Internal.Objects.Order;
import java.util.List;

public class RestaurantController {
    private Order currentOrder;

    public RestaurantController(Order order) {
        this.currentOrder = order;
    }
    public void addDishToOrder(Dish dish) {
        if (dish != null) {
            currentOrder.addDish(dish);
        }
    }
    public void removeDishFromOrder(Dish dish) {
        if (dish != null) {
            currentOrder.removeDish(dish);
        }
    }
    public double finalizeOrder() {
        if (currentOrder.getDishes().isEmpty()) {
            System.out.println("Order is empty.");
            return 0;
        }
        double total = currentOrder.calculateTotal();
        total += total * 0.1;
        Order savedOrder = currentOrder.copy();
        savedOrder.saveOrderToHistory();
        currentOrder.clearOrder();
        return total;
    }

    public void clearOrder() {
        currentOrder.clearOrder();
    }
    public List<Order> getOrderHistory() {
        return Order.getAllOrders();
    }
}
