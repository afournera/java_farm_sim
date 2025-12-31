package hft;

import java.math.BigDecimal;
import java.time.Instant;

public class Order {
    private long id;
    private boolean isLong;
    private BigDecimal price;
    private long qty;
    private Instant timestamp;
    private static long orderCount = 0;

    public Order(boolean isLong, BigDecimal price, long qty,
            Instant timestamp) {
        if (qty < 0) {
            throw new IllegalArgumentException("Positive quantity required to validate order");
        } else {
            this.id = ++orderCount;
            this.isLong = isLong;
            this.price = (price != null) ? price : BigDecimal.ZERO;
            this.qty = qty;
            this.timestamp = (timestamp != null) ? timestamp : Instant.now();
        }
    }

    // setters
    public void setQty(long qty){
        this.qty = qty;
    }
    
    // auxiliary
    public void reduceQty(long qty){
        if (qty > this.qty) {
            throw new IllegalArgumentException("Cannot reduce quantity by more than current quantity");
        }
        this.qty -= qty;
    }
    // getters
    public long getId() {
        return this.id;
    }

    public boolean isLong() {
        return this.isLong;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public long getQty() {
        return this.qty;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public long getOrderCount() {
        return orderCount;
    }
}