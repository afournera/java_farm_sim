package acp.fermev3;

import java.util.HashMap;

public class Market {
    private HashMap<String, Double> grilleTarifs;
    private double mu = 0.1;
    private double sigma = 0.02;
    private double delta_t = 1/Utils.JOURS_PAR_AN;

    double GBM(double S_t, double mu, double sigma, double delta_t){
        double epsi = Utils.rnd.nextGaussian();
        return S_t*Math.exp((mu-sigma*sigma/2)*delta_t + sigma*epsi*Math.sqrt(delta_t));
    }

    double updatesPrices(double S_t, double mu, double sigma, double delta_t){
        for (Double p : this.grilleTarifs);

    }
    
}
