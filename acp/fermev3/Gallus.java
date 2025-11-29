package acp.fermev3;

public class Gallus extends Animal {
    public static final double AGE_MAX = 10 * Utils.JOURS_PAR_AN;
    public static final int NOURRITURE_MAX = 400; // en g
    public static final int RATION_JOUR = 120; // en g

    public Gallus(String nom, int age, int age_prod, int age_max,
            int nourriture, int nourriture_jour, int nourriture_max,
            boolean genre) {
        super(nom, age, age_prod, age_max,
                nourriture, nourriture_jour, nourriture_max,
                genre);
    }

    public Gallus(String nom, int age, boolean genre) {
        this(
                nom,
                age,
                (int) Math.round(Utils.generateRandom(7)) * 30,
                (int) Math.round(AGE_MAX + Utils.JOURS_PAR_AN * Utils.rnd.nextGaussian()),
                0,
                (int) Math.round((int) Math.round(Utils.generateRandom(RATION_JOUR))), // une gaussienne centrée en
                                                                                       // Ration Jour
                (int) Math.round((int) Math.round(Utils.generateRandom(NOURRITURE_MAX))), // ibid centrée en
                                                                                          // Nourriture_max
                genre);
    }

    public Gallus(String nom) {
        this(
                nom,
                (int) Math.round(Utils.generateRandom(2) * Utils.JOURS_PAR_AN),
                (boolean) Utils.rnd.nextBoolean());
    }

    @Override
    public String getNourritureType() {
        return ("Graine");
    }

    @Override
    public String getRessourceType() {
        return ("Oeuf");
    }

    @Override
    public String getGenreString(){
        String s = this.isFemale ? "Poule" : "Coq";
        return s;
    }

    @Override
    public int produire() {
        if (!isFemale || this.ages[0] <= this.ages[1]) {
            return 0;
        }
        if (this.a_produit) {
            return 0;
        } else {
            a_produit = true;
            return 1;
        }
    }

    @Override
    public void hi() {
        if (isFemale) {
            System.out.println("Côt côt");
        } else {
            System.out.println("Cocorrico");
        }
    }

    @Override
    public String dump() {
        return "Gallus " + this.nom + " | "
                + "Nourriture: " + this.nourritures[0] + " | "
                + "Nourriture max:" + this.nourritures[2] + " | "
                + "Age production: " + this.ages[1] + " | "
                + "Age max: " + this.ages[2];
    }
}