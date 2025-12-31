package acp.fermev3;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;
import java.math.BigDecimal;

import hft.Pricer;


public class Ferme {
    private double capital;
    private int jour;
    private ArrayList<Animal> cheptel;
    private String nom;
    private HashMap<String, Integer> stockProd;
    private HashMap<String, Integer> stockNour;
    private HashMap<String, Double> grilleTarifs;
    private HashMap<String, Pricer> marketPricer;
    public static final String OEUF = "Oeuf";
    public static final String LAIT = "Lait";
    public static final String FOIN = "Foin";
    public static final String GRAINE = "Graine";
    public static final String GALLUS = "Gallus";
    public static final String OVIS = "Ovis";

    public Ferme(String nom, double capital){
        this.nom = nom;
        this.capital = capital;
        this.cheptel = new ArrayList<>();
        this.stockProd = new HashMap<>();
        this.stockNour = new HashMap<>();
        this.grilleTarifs = new HashMap<>();

        this.stockProd.put(OEUF, 0);
        this.stockProd.put(LAIT, 0);
        this.stockNour.put(FOIN, 0);
        this.stockNour.put(GRAINE, 0);

        this.grilleTarifs.put(OEUF, 0.0);
        this.grilleTarifs.put(LAIT, 0.0);
        this.grilleTarifs.put(FOIN, 0.0);
        this.grilleTarifs.put(GRAINE, 0.0);
        this.grilleTarifs.put(GALLUS, 0.0);
        this.grilleTarifs.put(OVIS, 0.0);
    }

    public Ferme(String nom) {
        this(
                nom,
                10000);
    }

    // get methods
    public int getJour() {
        return this.jour;
    }

    public double getCapital(){
        return this.capital;
    }

    public int getClassNumber(Class<?> className){
        int counter = 0;
        for (Animal a : this.cheptel){
            if(className.isInstance(a)){
                counter++;
            }
        }
        return counter;
    }

    public int getClassNumber(Class<?> className, boolean wantFemale){
        int counter = 0;
        for (Animal a : this.cheptel){
            if(className.isInstance(a) && a.getGenre() == wantFemale){
                counter++;
            }
        }
        return counter;
    }

    public String getEtatCheptel(){
        int nbGallus = getClassNumber(Gallus.class);
        int nbOvis = getClassNumber(Ovis.class);

        int nbBelier = getClassNumber(Ovis.class, false);
        int nbCoq = getClassNumber(Gallus.class, false);

        // je ne fais pas les "if statements" pour les accords en nombre
        return "Ovis: " + nbOvis + " (" + nbBelier + " bélier(s))"
               + " | Gallus: " + nbGallus + " (" + nbCoq + " coq(s))";
    }

    public double getTarif(String item){
        return this.grilleTarifs.getOrDefault(item, 0.0);
    }

    // set methods
    public void setTarif(String item, double prix){
        this.grilleTarifs.put(item, prix);
    }

    public void setStockProd(String item, Integer qte){
        this.stockProd.put(item, qte);
    }

    public void setStockNour(String item, Integer qte){
        this.stockNour.put(item, qte);
    }

    public void setJour(int j){
        this.jour = j;
    }
    public void initJour(){
        setJour(0);
    }

    // Animal related methods
    public void afficherAnimaux(){
        System.out.println("Liste des Animaux : " + "\n");
        for (Animal a : this.cheptel) {
            System.out.println(a);
        }
    }

    public boolean ajouterAnimal(Animal a, double p){
        if (this.capital >= p) {
            this.capital -= p;
            this.cheptel.add(a);
            return true;
        } else {
            System.out.println("Capital insuffisant pour ajouter l'animal" + a.getNom());
            return false;
        }
    }

    public boolean ajouterAnimal(Animal a){
        return ajouterAnimal(a, 0.0);
    }

    public void supprimerAnimal(Animal a){
        this.cheptel.remove(a);
    }

    public void saveSortedAnimal(Comparator<Animal> comparateur, String sortName){
        String nomFichier = "liste_animaux_jour_" + this.jour + ".csv";

        try (PrintWriter writer = new PrintWriter(new File(nomFichier))){
            //Header
            writer.println("Type,Nom,Age (jours),Age de Production(jours),Genre,NourritureJour (g)");

            ArrayList<Animal> copyList = new ArrayList<>(this.cheptel);

            Collections.sort(copyList, comparateur);

            for (Animal a : copyList){
                writer.printf("%s,%s,%d,%d,%s,%d%n",
                    a.getClass().getSimpleName(),
                    a.getNom(),
                    a.getAge(),
                    a.getAgeProd(),
                    a.getGenreString(),
                    a.getRation()
                );
            }
            System.out.println(">>> List animaux saved to " + nomFichier + " triée suivant " + sortName);
        }catch (IOException e){
            System.err.println("Erreur en écrivant le csv :" + nomFichier + e.getMessage());
        }
    }

    public void saveSortedAnimal(){
        String nomFichier = "liste_animaux_jour_" + this.jour + ".csv";

        try (PrintWriter writer = new PrintWriter(new File(nomFichier))){
            //Header
            writer.println("Type,Nom,Age (jours),Age de Production (jours),Genre,NourritureJour (g)");

            ArrayList<Animal> copyList = new ArrayList<>(this.cheptel);

            Collections.sort(copyList);

            for (Animal a : copyList){
                writer.printf("%s,%s,%d,%d,%s,%d%n",
                    a.getClass().getSimpleName(),
                    a.getNom(),
                    a.getAge(),
                    a.getAgeProd(),
                    a.getGenreString(),
                    a.getRation()
                );
            }
            System.out.println(">>> List animaux saved to " + nomFichier + " triée suivant l'age");
        }catch (IOException e){
            System.err.println("Erreur en écrivant le csv :" + nomFichier + e.getMessage());
        }
    }

    // stock movement related methods
    public void acheterNourriture(String n, int qte, double p_tot){
        // attention a la faillite
        if (this.capital - p_tot >= 0){
            this.capital -= p_tot;
            int stockNourActuel = this.stockNour.get(n);
            this.stockNour.put(n, stockNourActuel + qte);

        }else{
            System.out.println("Ya plus un rond pour acheter la nourriture...");
        }
    }

    public void vendreProd() { // 1. Iterate through what you HAVE (Inventory)
        for (Map.Entry<String, Integer> entry : this.stockProd.entrySet()){
            String produit = entry.getKey();
            Integer qte = entry.getValue();

            double prixUnitaire = this.grilleTarifs.get(produit);

            this.capital += prixUnitaire * qte;
        }

        for (String key : this.stockProd.keySet()){
            this.stockProd.put(key, 0); // on a vendu toute la prod
        }
    }

    public void jourSuivant(){
        this.jour += 1;
        ArrayList<Animal> mortDuJour = new ArrayList<>();
        for (Animal a : this.cheptel){
            // 1. initialisation de divers parametres
            int stockNourActuel;
            int stockProdActuel;
            String nourritureType = a.getNourritureType();
            String ressourceType = a.getRessourceType();

            // 2. l'animal vieilli
            a.vieillir(); // on note que je fais vieillir l'animal avant l'avoir fait produire

            // 3. nourriture et production
            int prod = a.produire();

            // Modification des stocks de nourriture et production
            stockNourActuel = this.stockNour.get(nourritureType);
            int qteDonnee = Math.min(stockNourActuel, a.getRation());

            if (qteDonnee > 0){
                int restNour = a.nourrir(qteDonnee);
                this.stockNour.put(nourritureType, stockNourActuel - qteDonnee + restNour);
            }else{
                System.out.println("stock de " + nourritureType + " vide!!!");
                System.out.println(a.getNom() + " n'a pas été nourri aujourd'hui");
                /**
                System.out.println(">>>> Achat de 3 mois de stock de " + nourritureType + " pour " + a.getNom()
                        + " au prix de " + 1e3 * this.grilleTarifs.get(nourritureType) + " ecu/kg" + "...");
                this.vendreProd();
                this.acheterNourriture(nourritureType, a.getRation() * 60,
                        a.getRation() * this.grilleTarifs.get(nourritureType) * 60);
                */
            }

            //mise a jour de la production
            stockProdActuel = this.stockProd.get(ressourceType);
            this.stockProd.put(ressourceType, stockProdActuel
                    + prod);

            // 4.mort du jour
            if (!a.estVivant()){
                System.out.println("Aujourd'hui est mort " + a.getNom());
                mortDuJour.add(a);
            }
        }
        this.cheptel.removeAll(mortDuJour);
        updateMarketPrice();
    }

    // toString
    @Override
    public String toString(){
        return String.format("%s (jour %d) %.2fécu"
            + " | Oeuf: %du"
            + " | Lait: %dL"
            + " | Foin: %dkg"
            + " | Graine: %dkg"
            + " | Prix des oeufs: %.2fécu/u"
            + " | Prix du lait: %.2fécu/L",
            this.nom,
            this.jour,
            this.capital,
            this.stockProd.get(OEUF),
            this.stockProd.get(LAIT),
            this.stockNour.get(FOIN)/1000,
            this.stockNour.get(GRAINE)/1000,
            this.grilleTarifs.get(OEUF),
            this.grilleTarifs.get(LAIT)
        );
    }

    //download/upload related
    public void saveFermeData(String nomFichier){
        try (PrintWriter writer = new PrintWriter(new File(nomFichier))) {
            System.out.println(">>> Trying to save ferme data to " + nomFichier + "...");
            StringBuilder sb = new StringBuilder();
            sb.append("nom:jour:capital:stock_graines:stock_foin:stock_oeufs:stock_lait\n");
            writer.write(sb.toString());

            writer.printf("%s:%d:%.2f:%d:%d:%d:%d%n",
                    this.nom,
                    this.jour,
                    this.capital,
                    this.stockNour.get("Graine"),
                    this.stockNour.get("Foin"),
                    this.stockProd.get("Oeuf"),
                    this.stockProd.get("Lait"));
            writer.close();
            System.out.println(">>> Ferme data saved to " + nomFichier);
        }catch (IOException e){
            System.err.println("Erreur en ecrivant le csv :" + nomFichier + " " + e.getMessage());
        }
    }

    public void saveAnimalData(String nomFichier){
        try (PrintWriter writer = new PrintWriter(new File(nomFichier))){
            System.out.println(">>> Trying to save animal data to " + nomFichier + "...");
            StringBuilder sb = new StringBuilder();
            sb.append("type:nom:genre:age:age_prod:age_max:nourriture:nourriture_jour:nourriture_max\n");
            writer.write(sb.toString());

            for (Animal a : this.cheptel){
                writer.printf("%s:%s:%b:%d:%d:%d:%d:%d:%d%n",
                        a.getClass().getSimpleName(),
                        a.getNom(),
                        a.getGenre(),
                        a.getAge(),
                        a.getAgeProd(),
                        a.getAgeMax(),
                        a.getNourriture(),
                        a.getRation(),
                        a.getNourritureMax());
            }
            writer.close();
            System.out.println(">>> Animal data saved to " + nomFichier);
        }catch (IOException e){
            System.err.println("Erreur en ecrivant le csv :" + nomFichier + " " + e.getMessage());
        }
    }

    //market related methods
    public void initMarket(){
        this.marketPricer = new HashMap<>();
        double delta_t = 1/Utils.JOURS_PAR_AN;

        for(String prod : this.grilleTarifs.keySet()){
            //les animaux ne compte pas car peut volatile.
            if (prod.equals(GALLUS) || prod.equals(OVIS)) {
                continue; 
            }

            double currentPrice = this.grilleTarifs.get(prod);
            //on construit un pricer
            //volatilite : 10% (aberrant pour des matières premières comme les oeufs mais osef)
            //drift : 0.02 pour l'inflation (2% en temps normal)
            Pricer p = new Pricer(
                BigDecimal.valueOf(currentPrice), 
                0.1, 
                0.02, 
                delta_t);

            this.marketPricer.put(prod, p);
        }
        System.out.println(">>> Marché initialisé");
    }

    public void updateMarketPrice(){
        if(this.marketPricer == null){
            initMarket();
        }

        System.out.println("Evolution du marché:");
        for(String prod : this.marketPricer.keySet()){
            Pricer p = this.marketPricer.get(prod);

            BigDecimal newPriceBD = p.tick();

            double newPrice = newPriceBD.doubleValue();

            this.grilleTarifs.put(prod, newPrice);
            System.out.printf("     - %-6s : %.2f ecu/kg %n", prod, newPrice);
        }
    }
}