package hft;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Comparator;

public class AskComparator implements Comparator<Order> {
    public int compare(Order a1, Order a2) {
        BigDecimal bidPrice1 = a1.getPrice();
        BigDecimal bidPrice2 = a2.getPrice();

        int result = bidPrice1.compareTo(bidPrice2);

        if (result == 0) {
            Instant t1 = a1.getTimestamp();
            Instant t2 = a2.getTimestamp();

            result = t1.compareTo(t2);
        }

        if (result == 0) {
            if (result == 0) {
            result = Long.compare(a1.getId(), a2.getId());
            }
        }
        return result;
    }
}