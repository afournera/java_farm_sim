package hft;

import java.math.BigDecimal;

public class MarketMaker extends Bot{
    private static final BigDecimal SPREAD = new BigDecimal("0.05"); 
    private static final long qty = 10;

    public MarketMaker(Book book, Pricer pricer, int id){
        super(book, pricer, id);
    }

    @Override
    public void trade(){
        BigDecimal value = pricer.getPrice();

        BigDecimal bidPrice = value.subtract(SPREAD);
        BigDecimal askPrice = value.add(SPREAD);

        Order buy = new Order(true, bidPrice, qty, null);
        book.addOrder(buy);

        Order sell = new Order(false, askPrice, qty, null);
        book.addOrder(sell);

        System.out.println("Bot " + this.id + " placed quotes at" + bidPrice +"(bid) " + askPrice + "(ask)");
    }
}