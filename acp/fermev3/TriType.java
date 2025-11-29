package acp.fermev3;

import java.util.Comparator;

public class TriType implements Comparator<Animal>{
    public int compare(Animal a1, Animal a2){
        String type1 = a1.getClass().getSimpleName();
        String type2 = a2.getClass().getSimpleName();

        return type1.compareTo(type2);
    }
}
