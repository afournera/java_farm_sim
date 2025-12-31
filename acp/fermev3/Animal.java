package acp.fermev3;

public abstract class Animal implements Comparable<Animal>{
    protected String nom;
    protected int[] ages = new int[3]; // [age actuel; age prod; age max]
    protected int[] nourritures = new int[3]; // [nourriture actuelle; ration; nourriture max]
    protected boolean isFemale;
    protected boolean a_produit = true;

    // main constructeur "animal"
    public Animal(String nom, int age, int age_prod,
            int age_max, int nourriture, int nourriture_jour,
            int nourriture_max, boolean genre) {
        this.nom = nom;
        this.ages[0] = age;
        this.ages[1] = age_prod;
        this.ages[2] = age_max;
        this.nourritures[0] = nourriture;
        this.nourritures[1] = nourriture_jour;
        this.nourritures[2] = nourriture_max;
        this.isFemale = genre;
    }

    public int nourrir(int qte) {
        int reste;
        if (qte + this.nourritures[0] <= this.nourritures[2]) {
            this.nourritures[0] += qte;
            return 0;
        } else {
            reste = this.nourritures[0] - this.nourritures[2] + qte;
            this.nourritures[0] = this.nourritures[2];
            return reste;
        }
    }

    public void vieillir() {
        this.ages[0]++;
        this.nourritures[0] -= this.nourritures[1]; // le gallus mange sa ration journalière
        a_produit = false;
    }

    public boolean estVivant() {
        if (this.ages[0] >= this.ages[2]) {
            System.out.println(this.nom + "est mort de vieillesse");
            return false;
        }
        if (this.nourritures[0] < 0) {
            System.out.println(this.nom + "est mort de faim");
            return false;
        } else {
            return true;
        }
    }

    public String getNom() {
        return this.nom;
    }

    public int getAge() {
        return this.ages[0];
    }

    public int getAgeProd() {
        return this.ages[1];
    }

    public int getAgeMax() {
        return this.ages[2];
    }

    public int getNourriture() {
        return this.nourritures[0];
    }

    public int getNourritureMax() {
        return this.nourritures[2];
    }

    public boolean getGenre() {
        return this.isFemale;
    }

    public int getRation() {
        return this.nourritures[1];
    }

    @Override
    public String toString() {
        int y_age = (int) Math.round(this.ages[0] / Utils.JOURS_PAR_AN);
        return "Gallus " + this.nom + ", "
                + this.isFemale + ", "
                + this.ages[0] + " jours "
                + "(" + y_age + " ans)";
    }

    @Override
    public int compareTo(Animal o){
        if (this.getAge() < o.getAge()){
            return -1;
        }else if (this.getAge() == o.getAge()){
            return 0;
        }else{
            return 1;
        }
    }

    public abstract void hi();

    public abstract int produire();

    public abstract String dump();

    public abstract String getNourritureType();

    public abstract String getRessourceType();

    public abstract String getGenreString();
}