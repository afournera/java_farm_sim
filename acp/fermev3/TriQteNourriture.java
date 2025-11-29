package acp.fermev3;

import java.util.Comparator;

public class TriQteNourriture implements Comparator<Animal>{
    public int compare(Animal a1, Animal a2){
        Integer qte1 = a1.getRation();
        Integer qte2 = a2.getRation();

        return -1*qte1.compareTo(qte2);
    }
}