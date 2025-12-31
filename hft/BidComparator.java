package hft;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Comparator;

public class BidComparator implements Comparator<Order> {
    public int compare(Order b1, Order b2) {
        BigDecimal bidPrice1 = b1.getPrice();
        BigDecimal bidPrice2 = b2.getPrice();

        int result = -1 * bidPrice1.compareTo(bidPrice2);

        if (result == 0) {
            Instant t1 = b1.getTimestamp();
            Instant t2 = b2.getTimestamp();

            result = t1.compareTo(t2);
        }

        if (result == 0) {
            result = Long.compare(b1.getId(), b2.getId());
        }

        return result;
    }
}