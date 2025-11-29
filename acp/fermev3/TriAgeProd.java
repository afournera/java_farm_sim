package acp.fermev3;

import java.util.Comparator;

public class TriAgeProd implements Comparator<Animal>{
    public int compare(Animal a1, Animal a2){
        Integer ageProd1 = a1.getAgeProd();
        Integer ageProd2 = a2.getAgeProd();

        return ageProd1.compareTo(ageProd2);
    }
}
