package testUnit;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import elements.*;
import map.Map;
import process.SimulationBuilder;
import process.SimulationManager;

import java.util.List;

public class SimulationBuilderTest {
    
    private SimulationBuilder builder;
    
    @Before
    public void setUp() {
        builder = new SimulationBuilder();
    }
    
    @Test
    public void testBuildMap() {
        // Créer une carte de taille 10x10
        int rows = 10;
        int columns = 10;
        Map map = builder.buildMap(rows, columns);
        
        // Vérifier que la carte a bien les dimensions attendues
        assertNotNull("La carte ne devrait pas être null", map);
        assertEquals("Le nombre de lignes devrait être " + rows, rows, map.getLines());
        assertEquals("Le nombre de colonnes devrait être " + columns, columns, map.getColumns());
    }
    
    @Test
    public void testInitializeBetes() {
        // Créer une carte
        Map map = builder.buildMap(10, 10);
        
        // Initialiser des bêtes
        int count = 5;
        List<Bete> betes = builder.initializeBetes(map, count);
        
        // Vérifier que le bon nombre de bêtes a été créé
        assertEquals("Le nombre de bêtes devrait être " + count, count, betes.size());
        
        // Vérifier que chaque bête a une position valide
        for (Bete bete : betes) {
            assertNotNull("La position de la bête ne devrait pas être null", bete.getPosition());
            assertTrue("La ligne de la position devrait être valide", 
                       bete.getPosition().getLine() >= 0 && bete.getPosition().getLine() < map.getLines());
            assertTrue("La colonne de la position devrait être valide", 
                       bete.getPosition().getColumn() >= 0 && bete.getPosition().getColumn() < map.getColumns());
        }
    }
    
    @Test
    public void testInitializeObstacles() {
        // Créer une carte
        Map map = builder.buildMap(10, 10);
        
        // Initialiser des obstacles
        int count = 10;
        List<Obstacle> obstacles = builder.initializeObstacles(map, count);
        
        // Vérifier que le bon nombre d'obstacles a été créé
        assertEquals("Le nombre d'obstacles devrait être " + count, count, obstacles.size());
        
        // Vérifier que chaque obstacle a une position valide
        for (Obstacle obstacle : obstacles) {
            assertNotNull("La position de l'obstacle ne devrait pas être null", obstacle.getPosition());
            assertTrue("La ligne de la position devrait être valide", 
                       obstacle.getPosition().getLine() >= 0 && obstacle.getPosition().getLine() < map.getLines());
            assertTrue("La colonne de la position devrait être valide", 
                       obstacle.getPosition().getColumn() >= 0 && obstacle.getPosition().getColumn() < map.getColumns());
        }
    }
    
    @Test
    public void testInitializeNourriture() {
        // Créer une carte
        Map map = builder.buildMap(10, 10);
        
        // Initialiser de la nourriture
        int count = 15;
        List<Nourriture> nourritureList = builder.initializeNourriture(map, count);
        
        // Vérifier que le bon nombre de nourritures a été créé
        assertEquals("Le nombre de nourritures devrait être " + count, count, nourritureList.size());
        
        // Vérifier que chaque nourriture a une position valide
        for (Nourriture nourriture : nourritureList) {
            assertNotNull("La position de la nourriture ne devrait pas être null", nourriture.getPosition());
            assertTrue("La ligne de la position devrait être valide", 
                       nourriture.getPosition().getLine() >= 0 && nourriture.getPosition().getLine() < map.getLines());
            assertTrue("La colonne de la position devrait être valide", 
                       nourriture.getPosition().getColumn() >= 0 && nourriture.getPosition().getColumn() < map.getColumns());
        }
    }
    
    @Test
    public void testBuildSimulation() {
        // Construire une simulation
        int rows = 15;
        int columns = 15;
        int beteCount = 10;
        int obstacleCount = 20;
        int nourritureCount = 30;
        
        SimulationManager simulation = builder.buildSimulation(
            builder.buildMap(rows, columns), beteCount, obstacleCount, nourritureCount
        );
        
        // Vérifier que la simulation a été correctement construite
        assertNotNull("La simulation ne devrait pas être null", simulation);
        assertEquals("Le nombre de bêtes devrait être " + beteCount, beteCount, simulation.getBetes().size());
        assertEquals("Le nombre d'obstacles devrait être " + obstacleCount, obstacleCount, simulation.getObstacles().size());
        assertEquals("Le nombre de nourritures devrait être " + nourritureCount, nourritureCount, simulation.getNourritures().size());
    }
}