# Simulation de Gestion de Ferme (Java)

Ce repository contient une application de simulation de ferme réalisée en Java. L'objectif principal de ce projet est de mettre en pratique les concepts fondamentaux de la **Programmation Orientée Objet (POO)** : héritage, polymorphisme, classes abstraites, interfaces, gestion des collections et programmation concurrente.

Le programme propose une interface en ligne de commande (CLI) permettant de gérer un cheptel, les stocks de nourriture et la production, avec un système de sauvegarde via fichiers CSV et une *légère* simulation de marché financier dynamique.

**Auteur :** Antonin Fournera  
**Profil :** Étudiant en double diplôme, Mines de Saint-Étienne (Ingénieur Civil des Mines) & EM Lyon Business School (PGE).

## 1\. Fonctionnalités

L'application permet de simuler la vie quotidienne d'une ferme (dénomée "La ferme ta gueule") avec les fonctionnalités suivantes :

  * **Gestion du cheptel :** Achat et élevage d'animaux (`Gallus` pour les poules/coqs, `Ovis` pour les moutons/béliers).
  * **Cycle de vie :** Vieillissement des animaux, consommation de nourriture quotidienne et cycle de mort naturelle.
  * **Économie :** Gestion d'un capital financier, achat de nourriture (Graines, Foin) et vente des productions (Œufs, Lait). Les prix fluctuent en temps réel grâce à un algorithme de *Geometric Brownian Motion* (qui implemente Black & Sholes). Les prix d'animaux restent fixe.
  * **Persistance des données :** Sauvegarde et chargement de l'état de la ferme et des animaux via des fichiers CSV (`ferme_save.csv`, `animal_save.csv`).
  * **Tri et Analyse :** Exportation de listes d'animaux triées selon divers critères (Nom, Âge, Production, Ration) grâce à l'implémentation de `Comparator`.

## 2\. Structure du projet

Le code source est organisé dans le package `acp.fermev3` :

  * `Main.java` : Point d'entrée de l'application et gestion du menu utilisateur.
  * `Ferme.java` : Cœur de la logique de gestion (inventaire, capital, liste des animaux).
  * `Animal.java` : Classe abstraite définissant les comportements communs.
  * `Gallus.java` & `Ovis.java` : Implémentations spécifiques des animaux.
  * `FermeReader.java` : Gestion de la lecture/écriture des fichiers de sauvegarde.
  * `Tri*.java` : Différentes classes utilitaires pour le tri des collections d'animaux.

Et emprunte une classe au package `hft` :
 * `Pricer.java`: modèle mathématique qui simulte l'avancé des prix
Le reste du package n'est pas implémenté ici.

## 3\. Installation et Exécution

### Prérequis

Assurez-vous d'avoir un **JDK (Java Development Kit)** installé sur votre machine (version 9 ou supérieure recommandée).

### Étape 1 : Cloner ou télécharger le projet

Téléchargez le code source et placez-vous dans le répertoire racine du projet (le dossier contenant le répertoire `acp`), possibilité de cloner avec git: 

```bash
git clone https://github.com/afournera/java_farm_sim.git
cd java_farm_sim
```

### Étape 2 : Compilation

Ouvrez votre terminal dans ce répertoire racine et compilez l'ensemble des fichiers Java avec les commandes suivantes :

```bash
javac acp/fermev3/*.java
javac hft/*.java
```

### Étape 3 : Lancement

Une fois la compilation terminée, lancez l'application en appelant la classe principale via la JVM. **Attention**, vous devez respecter le chemin du package :

```bash
java acp.fermev3.Main
```

## 4\. Utilisation

Une fois l'application lancée, un menu interactif s'affiche dans la console. Vous disposez de 2 actions par jour (achat, vente, approvisionnement). Une fois vos actions terminées, passez au "Jour suivant" pour voir vos animaux vieillir et produire.

Exemple de menu :

```text
[1] Acheter un gallus
[2] Acheter un ovis
[3] Vendre la production
...
[7] Save game
[8] Load game
```

## 5\. Notes
  * *Ce projet est réalisé dans un cadre académique pour démontrer la maîtrise des structures objets en Java.*
  * *Auto-feed (Désactivé) : Une logique de "survie automatique" (achat automatique de nourriture si les stocks sont vides) est présente dans le code de `Ferme.java` (méthode `jourSuivant`) mais a été commentée/désactivée.*
  * *Ce readme a été rédiger a l'aide de gemini ^^ <3*