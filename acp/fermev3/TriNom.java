package acp.fermev3;

import java.util.Comparator;

public class TriNom implements Comparator<Animal>{
    public int compare(Animal a1, Animal a2){
        String nom1 = a1.getNom();
        String nom2 = a2.getNom();

        return nom1.compareTo(nom2);
    }
}
