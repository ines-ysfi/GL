package process;

import map.*;
import elements.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import elements.Foret;     
import elements.Riviere;   
import elements.Montagne; 

public class SimulationBuilder {

    public Map buildMap(int rows, int columns) {
        return new Map(rows, columns);
    }

    public List<Bete> initializeBetes(Map map, int count) {
        List<Bete> betes = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Case position = map.getRandomFreeCase();
            if (position != null) {
            	position.incrementerBete();
                position.setOccupied(true);
                Random random = new Random();
                String sexe = random.nextBoolean() ? "Male" : "Female";
                int age = new Random().nextInt(5) + 1;
                float force = 100;
                int ageMoyenMort = 15 + random.nextInt(11);

                betes.add(new Bete(sexe, age, force, position,ageMoyenMort));
            }
        }
        return betes;
    }

    public List<Obstacle> initializeObstacles(Map map, int count) {
        List<Obstacle> obstacles = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            Case position = map.getRandomFreeCase();
            if (position == null) {
                
                System.out.println("Plus de cases libres disponibles pour placer des obstacles.");
                break;
            }
           
            position.setOccupied(true);

  
            int type = random.nextInt(3); 
            Obstacle obstacle = null;

            switch (type) {
                case 0:
                    obstacle = new Foret(position);
                    break;
                case 1:
                    obstacle = new Riviere(position);
                    break;
                case 2:
                    obstacle = new Montagne(position);
                    break;
            }

            if (obstacle != null) {
                obstacles.add(obstacle);
                position.setObstacle(obstacle); 
            }
        }

        return obstacles;
    }

    public List<Nourriture> initializeNourriture(Map map, int count) {
        List<Nourriture> nourritureList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Case position = map.getRandomFreeCase();
            if (position != null) {
            	position.setOccupied(true);
                position.setNourriture(true);
                nourritureList.add(new Nourriture(position));
            }
        }
        return nourritureList;
    }
    
    public SimulationManager buildSimulation(Map map, int beteCount, int obstacleCount, int nourritureCount) {
        List<Bete> betes = initializeBetes(map, beteCount);
        List<Obstacle> obstacles = initializeObstacles(map, obstacleCount);
        List<Nourriture> nourritures = initializeNourriture(map, nourritureCount);
        return new SimulationManager(map, betes, obstacles, nourritures);
    }
}
