package FinalProject.Internal.Observers;

import javax.swing.JTextArea;
import FinalProject.Internal.Objects.Order;

public class UITextObserver implements OrderObserver {
    private JTextArea orderTextArea;

    public UITextObserver(JTextArea orderTextArea) {
        this.orderTextArea = orderTextArea;
    }

    @Override
    public void updateOrder() {
        Order order = Order.getInstance();
        StringBuilder orderDetails = new StringBuilder("Текущий заказ:\n");
        order.getDishes().forEach(dish ->
                orderDetails.append(dish.getName()).append(" - ").append(dish.getPrice()).append(" тг\n")
        );
        orderTextArea.setText(orderDetails.toString());
    }
}
