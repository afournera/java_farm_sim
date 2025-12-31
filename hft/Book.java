package hft;

import java.math.BigDecimal;
import java.util.TreeSet;

public class Book {
    private TreeSet<Order> bids;
    private TreeSet<Order> asks;

    public Book(){
        this.bids = new TreeSet<>(new BidComparator());
        this.asks = new TreeSet<>(new AskComparator());
    }

    public synchronized void addOrder(Order incomingOrder){
        if (incomingOrder.isLong()){
            processMatch(incomingOrder, this.asks); 
            
            if (incomingOrder.getQty() > 0){
                this.bids.add(incomingOrder);
            }
        }else{
            processMatch(incomingOrder, this.bids);

            if (incomingOrder.getQty() > 0){
                this.asks.add(incomingOrder);
            }
        }
    }

    private void processMatch(Order incomingOrder, TreeSet<Order> restingOrders) {
        // While the incoming order needs to fill... 
        // AND there are orders on the other side...
        while (incomingOrder.getQty() > 0 && !restingOrders.isEmpty()) {
            
            Order bestResting = restingOrders.first(); // Peek at the best price
            int comp = incomingOrder.getPrice().compareTo(bestResting.getPrice());
            
            boolean isBuyMatch = incomingOrder.isLong() && (comp >= 0);  // Buy Price >= Sell Price
            boolean isSellMatch = !incomingOrder.isLong() && (comp <= 0); // Sell Price <= Buy Price

            if(!isBuyMatch && !isSellMatch){
                break;
            }

            long tradeQty = Math.min(incomingOrder.getQty(), bestResting.getQty());

            incomingOrder.reduceQty(tradeQty);
            bestResting.reduceQty(tradeQty);

            if(bestResting.getQty() == 0){
                restingOrders.pollFirst();
            }
        }
    }

    //getters
    public BigDecimal getFirstBid(){
        if(bids.isEmpty()){
            return null;
        }
        return this.bids.first().getPrice();
    }

    public BigDecimal getFirstAsk(){
        if(asks .isEmpty()){
            return null;
        }
        return this.asks.first().getPrice();
    }
}