package hft;

import java.math.BigDecimal;
import java.time.Instant;

public class BookTest {
    public static void main(String[] args) {
        testMatching();
    }

    private static void testMatching() {
        Book book = new Book();

        // Add Sell Order (Ask): 100 @ 10.50
        Order ask1 = new Order(false, new BigDecimal("10.50"), 100, Instant.now());
        book.addOrder(ask1);
        System.out.println("Added Ask: 100 @ 10.50. ID: " + ask1.getId());

        // Add Buy Order (Bid): 50 @ 10.50 (Matches)
        Order bid1 = new Order(true, new BigDecimal("10.50"), 50, Instant.now());
        book.addOrder(bid1);
        System.out.println("Added Bid: 50 @ 10.50. ID: " + bid1.getId());

        // Check remaining quantity
        if (ask1.getQty() == 50) {
            System.out.println("PASS: Ask quantity reduced to 50");
        } else {
            System.out.println("FAIL: Ask quantity is " + ask1.getQty());
        }

        if (bid1.getQty() == 0) {
            System.out.println("PASS: Bid quantity reduced to 0");
        } else {
            System.out.println("FAIL: Bid quantity is " + bid1.getQty());
        }

        // Add Buy Order (Bid): 60 @ 10.60 (Matches remaining 50, leaves 10 resting)
        Order bid2 = new Order(true, new BigDecimal("10.60"), 60, Instant.now());
        book.addOrder(bid2);
        System.out.println("Added Bid: 60 @ 10.60. ID: " + bid2.getId());

        if (ask1.getQty() == 0) {
            System.out.println("PASS: Ask quantity reduced to 0");
        } else {
            System.out.println("FAIL: Ask quantity is " + ask1.getQty());
        }

        if (bid2.getQty() == 10) {
            System.out.println("PASS: Bid quantity reduced to 10");
        } else {
            System.out.println("FAIL: Bid quantity is " + bid2.getQty());
        }
    }
}
