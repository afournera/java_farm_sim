package hft;

import java.math.BigDecimal;
import java.util.Random;

public class Pricer {
    private BigDecimal value;

    private double vol;   //sigma
    private double drift;   //mu
    private double delta_t;

    private final Random rnd = new Random();

    public Pricer(BigDecimal startValue, double vol, double drift, double delta_t){
        this.value = startValue;
        this.vol = vol;
        this.drift = drift;
        this.delta_t = delta_t;
    }


    private double gbmCalculation(double currentPrice) {
        double epsilon = rnd.nextGaussian();
        
        double driftTerm = (drift - 0.5*vol*vol)*delta_t;
        double randomTerm = vol*epsilon*Math.sqrt(delta_t);
        
        return currentPrice*Math.exp(driftTerm + randomTerm);
    }

    public BigDecimal tick(){
        double priceAsDouble = this.value.doubleValue();
        
        double nextPrice = gbmCalculation(priceAsDouble);

        // using String constructor is safer for precision than double constructor
        this.value = new BigDecimal(String.valueOf(nextPrice)); 
        
        return this.value;
    }

    public BigDecimal getPrice() {
        return this.value;
    }
}