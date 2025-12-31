package hft;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {
    public static void main(String[] args){
        System.out.println("--- Starting HFT simulation");


        Book orderBook = new Book();
        BigDecimal startValue = new BigDecimal(100);
        double delta_t = 1./252;

        Pricer mmPricer = new Pricer(startValue, 0.2, 0.05, delta_t);
        Bot marketMaker = new MarketMaker(orderBook, mmPricer, 0);

        Thread botThread = new Thread(marketMaker);
        botThread.start();

        System.out.println("Market Maker bot launched -- simulation running...");

        try{
            for(int i=0; i < 20; i++){
                BigDecimal newPrice = mmPricer.tick();
                System.out.println("Tick " + i + ": value: " + newPrice.setScale(2, RoundingMode.HALF_UP));
                //wait a bit for readability
                Thread.sleep(10);
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        marketMaker.stop();
        try{
            botThread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("--- Simulation ended ---");
    }
}
