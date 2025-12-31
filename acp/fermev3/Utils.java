package acp.fermev3;

import java.util.Random;

public class Utils {
    public static final double JOURS_PAR_AN = 365.25;
    public static final Random rnd = new Random();

    // cette fonction genere une gaussienne centrée en a pour eviter les nombres
    // negatifs...
    public static double generateRandom(int moy) {
        double rdm;
        double sigma = moy*0.2;
        do {
            rdm = moy + (sigma*Utils.rnd.nextGaussian());
        } while (rdm <= 0);
        return rdm;
    }

    public int calc_max(int[] li) {
        int max = li[0];
        for (int i = 1; i < li.length; i++) {
            if (li[i] > max)
                max = li[i];
        }
        return max;
    }
}