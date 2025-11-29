package acp.fermev3;

public class Ovis extends Animal {
    public static final double AGE_MAX = 20 * Utils.JOURS_PAR_AN;
    public static final int NOURRITURE_MAX = 15000; // en g
    public static final int RATION_JOUR = 4000; // en g

    public Ovis(String nom, int age, int age_prod, int age_max,
            int nourriture, int nourriture_jour, int nourriture_max,
            boolean genre) {
        super(nom, age, age_prod, age_max, nourriture, nourriture_jour,
                nourriture_max, genre);
    }

    public Ovis(String nom, int age, boolean genre) {
        this(
                nom,
                age,
                (int) Math.round(Utils.generateRandom(12)) * 30,
                (int) Math.round(AGE_MAX + Utils.JOURS_PAR_AN * Utils.rnd.nextGaussian()), // je prends le risque
                0,
                (int) Math.round((int) Math.round(Utils.generateRandom(RATION_JOUR))), // une gaussienne centrée en
                                                                                       // Ration Jour
                (int) Math.round((int) Math.round(Utils.generateRandom(NOURRITURE_MAX))), // meme remarque
                genre);
    }

    public Ovis(String nom) {
        this(
                nom,
                (int) Math.round(Utils.generateRandom(2) * Utils.JOURS_PAR_AN),
                (boolean) Utils.rnd.nextBoolean());
    }

    @Override
    public String getNourritureType() {
        return ("Foin");
    }

    @Override
    public String getRessourceType() {
        return ("Lait");
    }

    @Override
    public String getGenreString(){
        String s = this.isFemale ? "Brebis" : "Bélier";
        return s;
    }

    @Override
    public void hi() {
        System.out.println("Bêêêêêh !");
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
            return (int) Math.round(1 + 0.5 * Utils.rnd.nextGaussian());
        }
    }

    @Override
    public String dump() {
        return "Ovis " + this.nom + " | "
                + "Nourriture: " + this.nourritures[0] + " | "
                + "Nourriture max:" + this.nourritures[2] + " | "
                + "Age production: " + this.ages[1] + " | "
                + "Age max: " + this.ages[2];
    }
}
