package FinalProject.Internal.Adapter;

import FinalProject.Internal.Objects.Order;
import javax.swing.JTextArea;
import java.util.Comparator;
import java.util.List;

public class OrderHistoryAdapter {
    private List<Order> orders;

    public OrderHistoryAdapter(List<Order> orders) {
        this.orders = orders;
    }

    public void updateOrderHistoryDisplay(JTextArea textArea) {
        StringBuilder displayText = new StringBuilder("История заказов:\n");
        orders.sort(Comparator.comparing(Order::getDate).reversed());
        for (Order order : orders) {
            displayText.append("Заказ от ").append(order.getDate())
                    .append(" - Сумма: ").append(order.calculateTotal()).append(" тг\n");
        }
        textArea.setText(displayText.toString());
    }

}
