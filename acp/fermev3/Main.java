package acp.fermev3;

import java.util.Scanner;

public class Main {
    //prix comme defini dans le sujet
    public static final double pGallus = 10.;
    public static final double pOvis = 120.;
    public static final double pFoin = 10.;
    public static final double pGraine = 5.;
    public static final double PRIX_LAIT = 1.; //au litre
    public static final double PRIX_OEUF = 0.6; // a l'unité

    public static void main(String[] args){
        //initialise la ferme:
        Ferme newFerme = new Ferme("La ferme ta gueule", 10000);

        //initialize 3 gallus
        Gallus newGallus = new Gallus("gallus0");
        newFerme.ajouterAnimal(newGallus);

        newGallus = new Gallus("gallus1");
        newFerme.ajouterAnimal(newGallus);

        newGallus = new Gallus("gallus2");
        newFerme.ajouterAnimal(newGallus);

        //pour la boucle
        Ovis newOvis = new Ovis("Shaun");

        //setup des prix
        newFerme.setTarif(Ferme.GALLUS, pGallus);
        newFerme.setTarif(Ferme.OVIS, pOvis);
        newFerme.setTarif(Ferme.LAIT, 1.0);
        newFerme.setTarif(Ferme.OEUF, 0.6);
        newFerme.setTarif(Ferme.FOIN, pFoin);
        newFerme.setTarif(Ferme.GRAINE, 5.0);

        newFerme.initMarket();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        int actionJour = 0;  //pour verifier les 2 actions MAX par jour

        int gallusSequence = 3;
        int ovisSequence = 0;

        //boucle de l'interface
        while(running){
            printMenu(newFerme);

            System.out.print(actionJour + "/2 actions | Jour : " + newFerme.getJour() + " >");

            //on verifie que l'input est bien un entier
            if(scanner.hasNextInt()){
                int choix = scanner.nextInt();
                scanner.nextLine();

                //get des prix
                double pGallus = newFerme.getTarif(Ferme.GALLUS);
                double pOvis = newFerme.getTarif(Ferme.OVIS);
                double pFoin = newFerme.getTarif(Ferme.FOIN);
                double pGraine = newFerme.getTarif(Ferme.GRAINE);

                switch(choix){
                    case 1: {//achat d'une poule
                        System.out.println(">>> Achat d'un gallus (" + pGallus + "écus)...");
                        if(newFerme.getCapital() >= pGallus){
                            String nomNewGallus = "Gallus" + (gallusSequence++);
                            newGallus = new Gallus(nomNewGallus);
                            newFerme.ajouterAnimal(newGallus, pGallus);
                            actionJour++;
                            System.out.println(">>> Achat effectué!");
                        }else{
                            System.out.println("/!\\ ATTENTION /!\\ pas assez de capital pour achater un gallus");
                        }
                        break;
                    }

                    case 2: {//achat d'un ovis
                        System.out.println(">>> Achat d'un ovis (" + pOvis + "écus)...");
                        if(newFerme.getCapital() >= pOvis){
                            String nomNewOvis = "Ovis" + (ovisSequence++);
                            newOvis = new Ovis(nomNewOvis);
                            newFerme.ajouterAnimal(newOvis, pOvis);
                            actionJour++;
                            System.out.println(">>> Achat effectué!");
                        }else{
                            System.out.println("/!\\ ATTENTION /!\\ pas assez de capital pour achater un ovis");
                        }
                        break;
                    }

                    case 3: //Vendre prod
                        System.out.println(">>> Vente de la production...");
                        newFerme.vendreProd();
                        System.out.println("Production vendue !");
                        actionJour++;
                        break;
                    
                    case 4: //Acheter Foin
                        System.out.print(">>> Quantité de foin à acheter en kg (int > 0): ");
                        if (scanner.hasNextInt()){
                            int qteFoin = scanner.nextInt();
                            scanner.nextLine();

                            if (qteFoin > 0){
                                double coutTotal = qteFoin*pFoin;
                                newFerme.acheterNourriture("Foin", 1000*qteFoin, coutTotal);
                                System.out.println("Achat de Foin pour " + coutTotal + " écu effectué.");
                                actionJour++;
                            }else{
                                System.out.println("Quantité invalide; entrez un nombre positif");
                            }
                        }else{
                            System.out.println("Entrez un nombre entier.");
                            scanner.next(); //clear le mauvais input
                        }
                        break;
                    
                    case 5: //Acheter graines
                        System.out.print(">>> Quantité de graines à acheter en kg (int > 0): ");
                        if (scanner.hasNextInt()){
                            int qteGraine = scanner.nextInt();
                            scanner.nextLine();

                            if (qteGraine > 0){
                                double coutTotal = qteGraine*pGraine;
                                newFerme.acheterNourriture("Graine", 1000*qteGraine, coutTotal);
                                System.out.println("Achat de graines pour " + coutTotal + " écu effectué.");
                                actionJour++;
                            }else{
                                System.out.println("Quantité invalide; entrez un nombre positif");
                            }
                        }else{
                            System.out.println("Entrez un nombre entier.");
                            scanner.next(); //clear le mauvais input
                        }
                        break;
                    
                    case 6: // Jour suivant
                        newFerme.jourSuivant();
                        actionJour = 0;
                        break;
                    
                    case 7: //save
                        System.out.println(">>> Saving ferme data...");
                        newFerme.saveFermeData("ferme_save.csv");
                        System.out.println("\n >>> Saving animal data...");
                        newFerme.saveAnimalData("animal_save.csv");
                        break;

                    case 8: //load
                        Ferme potentielleNewFerme= FermeReader.loadFerme("ferme_save.csv", "animal_save.csv");
                        if (potentielleNewFerme != null){
                            newFerme = potentielleNewFerme;
                            System.out.println("Farm loaded successfully!");
                            System.out.println(newFerme);
                            newFerme.afficherAnimaux();
                        }else{
                            System.out.println("Echec du chargement. On garde la ferme actuelle.");
                        }
                        break;

                    case 9: //liste csv des animaux
                        printAnimalCsv();
                        int choix2 = scanner.nextInt();

                        switch(choix2){
                            case 1:
                                System.out.println(">>> Tri selon le genre");
                                printSavingMenu1(newFerme);
                                newFerme.saveSortedAnimal(new TriGenre(), "tri_genre");
                                break;
                            
                            case 2:
                                System.out.println(">>> Tri selon l'age de production");
                                printSavingMenu1(newFerme);
                                newFerme.saveSortedAnimal(new TriAgeProd(), "tri_age_prod");
                                break;

                            case 3:
                                System.out.println(">>> Tri selon le Nom");
                                printSavingMenu1(newFerme);
                                newFerme.saveSortedAnimal(new TriNom(), "tri_nom");
                                break;
                            
                            case 4:
                                System.out.println(">>> Tri selon la ration journalière de nourriture");
                                printSavingMenu1(newFerme);
                                newFerme.saveSortedAnimal(new TriQteNourriture(), "tri_ration");
                                break;

                            case 5:
                                System.out.println(">>> Tri selon le type (gallus-ovis)");
                                printSavingMenu1(newFerme);
                                newFerme.saveSortedAnimal(new TriType(), "tri_type");
                                break;

                            case 6:
                                System.out.println(">>> Tri selon l'age");
                                printSavingMenu1(newFerme);
                                newFerme.saveSortedAnimal();
                                break;

                            case 0:
                                System.out.println(">>> Annulation...");
                                break;
                        }
                        break;
                        
                    
                    case 0: //quitter
                        running = false;
                        System.out.println("Au revoir!");
                        break;
                        
                    default:
                        System.out.println("Choix invalide");
                }
                if (running && actionJour >=2){
                    System.out.println("\n========================================");
                    System.out.println(">>> 2 actions effectuées. Fin de la journée!");
                    System.out.println("========================================\n");

                    newFerme.jourSuivant();
                    actionJour = 0;
                }
            }
        }
        scanner.close();
    }

    private static void printMenu(Ferme ferme){
        //to string
        System.out.println("\n \n=%=%=%=%=%=%=%=%=%=%=%=%=%=%=%=%=%=%=%=%");
        System.out.println("========================================\n");
        System.out.println(ferme);

        //etat animaux
        System.out.println(ferme.getEtatCheptel()+"\n");

        //interface
        System.out.println("========================================");
        System.out.printf("[1] Acheter un gallus (%.2f écu)\n", ferme.getTarif(Ferme.GALLUS));
        System.out.printf("[2] Acheter un ovis (%.2f écu)\n", ferme.getTarif(Ferme.OVIS));
        System.out.println("[3] Vendre la production");
        System.out.printf("[4] Acheter du foin (%.2f écu/kg)\n", ferme.getTarif(Ferme.FOIN));
        System.out.printf("[5] Acheter des graines (%.2f écu/kg)\n \n", ferme.getTarif(Ferme.GRAINE));
        System.out.println("........................................");
        System.out.println("[6] Jour suivant \n");
        System.out.println("----------------------------------------");
        System.out.println("[7] Save game (aka Sauvegarder l'état)");
        System.out.println("[8] Load game (aka charger la sauvegarde)");
        System.out.println("[9] Créer une table csv triée des animaux");
        System.out.println("[0] Quit");
        System.out.println("========================================");
    }

    private static void printAnimalCsv(){
        System.out.println("\n \n==========Choix du trix===========\n");
        
        System.out.println("[1] Trier par genre");
        System.out.println("[2] Trier par age de production");
        System.out.println("[3] Trier par Nom");
        System.out.println("[4] Trier par ration journalière");
        System.out.println("[5] Trier par type (gallus-ovis)");
        System.out.println("[6] Trier par age");
        System.out.println("----------------------------------------");
        System.out.println("[0] Annuler");
        System.out.println("========================================");
    }

    private static void printSavingMenu1(Ferme ferme){
        System.out.println(">>> Trying to save la liste des animaux dans le fichier liste_animaux_jour_" + ferme.getJour() + ".csv ...");
    }
}
