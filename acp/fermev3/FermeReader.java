package acp.fermev3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FermeReader{

    public static Ferme loadFerme(String pathFerme, String pathAnimaux){
        File file = new File(pathFerme);
        Ferme ferme = null;
        try (Scanner scanner = new Scanner(file)){
            scanner.nextLine(); //on saute le "header" que je connais
            if (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] data = line.split(":");

                //on rappelle: nom:jour:capital:stock_graines:stock_foin:stock_oeufs:stock_lait
                String nom = data[0];
                int jour = Integer.parseInt(data[1]);
                double capital = Double.parseDouble(data[2]);
                int stockGraine = Integer.parseInt(data[3]);
                int stockFoin = Integer.parseInt(data[4]);
                int stockOeuf = Integer.parseInt(data[5]);
                int stockLait = Integer.parseInt(data[6]);

                //puis on construit la ferme
                ferme = new Ferme(nom, capital);
                ferme.setJour(jour);
                ferme.setStockNour("Graine", stockGraine);
                ferme.setStockNour("Foin", stockFoin);
                ferme.setStockProd("Oeuf", stockOeuf);
                ferme.setStockProd("Lait", stockLait);
            }
        }catch (FileNotFoundException e){
            System.err.println("File not found: " + pathFerme);
            return null;
        }catch (Exception e){
            System.err.println("Erreur en lisant la data de la ferme: " + e.getMessage());
            return null;
        }

    if (ferme != null){
            loadAnimaux(ferme, pathAnimaux);
        }
    return ferme;
    }

    public static void loadAnimaux(Ferme ferme, String pathAnimaux){
        File file = new File(pathAnimaux);
        try (Scanner scanner = new Scanner(file)){
            scanner.nextLine(); //on saute le header
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(":");

                //on rappelle: type:nom:genre:age:age_prod:age_max:nourriture:nourriture_jour:nourriture_max
                String type = data[0];
                String nom = data[1];
                Boolean genre = Boolean.parseBoolean(data[2]);
                int age = Integer.parseInt(data[3]);
                int age_prod = Integer.parseInt(data[4]);
                int age_max = Integer.parseInt(data[5]);
                int nourriture = Integer.parseInt(data[6]);
                int ration = Integer.parseInt(data[7]);
                int nourriture_max = Integer.parseInt(data[8]);

                Animal animal = null;

                if (type.equals("Gallus")){
                    animal = new Gallus(nom, age,
                        age_prod, age_max, nourriture,
                        ration, nourriture_max, genre
                    );
                }

                else if (type.equals("Ovis")){
                    animal = new Ovis(nom, age,
                        age_prod, age_max, nourriture,
                        ration, nourriture_max, genre
                    );
                }
                
                if (animal != null){
                    ferme.ajouterAnimal(animal);
                }
            }
        }catch (FileNotFoundException e){
            System.err.println("Animal file not found...");
        }catch (Exception e){
            System.err.println("Erreur en recoltant une ligne animal: " + e.getMessage());
        }
    }
}