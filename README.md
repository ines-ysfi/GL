# Projet Betes

## Description

Le projet Betes simule un écosystème où des créatures (appelées "bêtes") interagissent avec leur environnement. Les bêtes peuvent se déplacer, se reproduire, consommer de la nourriture, éviter des obstacles, et interagir avec d'autres éléments comme des forêts, des montagnes ou des rivières. L'application inclut une interface graphique pour visualiser la simulation, ainsi que des stastistiques détaillées pour suivre les événements.

## Structure du Projet

Voici la structure principale du projet :

### Dossiers

- `elements/` : Contient les classes représentant les éléments de l'environnement, comme :
  - Bete.java : Classe principale représentant une bête.
  - Foret.java, Montagne.java, Nourriture.java, Obstacle.java, Riviere.java : Classes représentant différents éléments de l'environnement.

- `gui/` : Contient les classes pour l'interface graphique, comme :
  - BeteInfoPanel.java : Panneau d'information sur les bêtes.
  - GridPanel.java : Grille représentant l'environnement.
  - IntroFrame.java : Fenêtre d'introduction.
  - SimulationGUI.java : Interface principale de la simulation.
  - StatisticsFrame.java : Fenêtre pour afficher les statistiques.

- `log/` : Contient les fichiers de journalisation, comme betesSimulation-log.html, qui enregistre les événements de la simulation.

- `map/` : permet la creation d'une grille pour la simulation

- `process/` : Contient les classes gérant les processus internes de la simulation (par exemple, reproduction, gestion des étapes, etc.).

- `testUnit/` : Contiennent les tests pour vérifier le bon fonctionnement des différentes parties du projet.

- `img/` : Contient les ressources graphiques, comme bete.png.


## Fonctionnalités

- Simulation d'un écosystème avec des bêtes et des éléments environnementaux.
- Interface graphique interactive pour visualiser la simulation.
- Journalisation détaillée des événements (déplacements, reproductions, échecs, etc.).
- Gestion des obstacles et des interactions entre les bêtes.

## Prérequis

- Java : Le projet est écrit en Java. Assurez-vous d'avoir un JDK installé.
- Eclipse IDE (optionnel) : Le projet est configuré pour être utilisé avec Eclipse.

## Bibliothèques Externes

Le projet utilise les bibliothèques externes suivantes :

- JFreeChart : Utilisé pour générer des graphiques et des visualisations statistiques.
- JUnit : Utilisé pour les tests unitaires afin de garantir la fiabilité du code.
- Log4j : Utilisé pour la journalisation des événements et des erreurs dans le projet.

## Instructions d'Exécution

1. Compilation :
   - Compilez les fichiers Java situés dans le dossier src/ en utilisant un IDE comme Eclipse ou une commande javac.

2. Exécution :
   - Lancez la classe principale src/test/test.java qui permet le lancement de la simulation.

3. Visualisation :
   - Utilisez l'interface graphique pour observer la simulation en temps réel.

4. Propriétés log :
   - Consultez le fichier betesSimulation-log.html dans le dossier log/ pour analyser les événements de la simulation (les messages de debug)

## Auteurs

- YOUSFI Ines
- OUYAHIA Lisa
- PIAKI Deborah
