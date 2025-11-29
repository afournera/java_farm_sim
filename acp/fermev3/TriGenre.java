package acp.fermev3;

import java.util.Comparator;

public class TriGenre implements Comparator<Animal>{
    public int compare(Animal g1, Animal g2){
        Boolean b1 = g1.getGenre();
        Boolean b2 = g2.getGenre();
        return b1.compareTo(b2);
    }
}