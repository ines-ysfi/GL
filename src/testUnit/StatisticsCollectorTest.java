package testUnit;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import elements.Bete;
import map.Case;
import process.StatisticsCollector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatisticsCollectorTest {
    
    private StatisticsCollector collector;
    private List<Bete> betes;
    
    @Before
    public void setUp() {
        collector = new StatisticsCollector();
        betes = new ArrayList<>();
        
        // Créer quelques bêtes pour les tests
        Case position = new Case(0, 0);
        betes.add(new Bete("Male", 5, 100, position, 20));
        betes.add(new Bete("Male", 3, 90, position, 20));
        betes.add(new Bete("Female", 7, 80, position, 20));
        betes.add(new Bete("Female", 2, 110, position, 20));
    }
    
    @Test
    public void testUpdateStatistics() {
        // Mettre à jour les statistiques
        collector.updateStatistics(betes);
        
        // Vérifier les statistiques de population
        assertEquals("La taille de l'historique de population devrait être 1", 1, collector.getPopulationHistory().size());
        assertEquals("La population devrait être " + betes.size(), Integer.valueOf(betes.size()), collector.getPopulationHistory().get(0));
        
        // Vérifier la répartition mâle/femelle
        assertEquals("Le nombre de mâles devrait être 2", Integer.valueOf(2), collector.getMalePopulationHistory().get(0));
        assertEquals("Le nombre de femelles devrait être 2", Integer.valueOf(2), collector.getFemalePopulationHistory().get(0));
        
        // Vérifier l'âge moyen
        float expectedAverageAge = (5 + 3 + 7 + 2) / 4.0f;
        assertEquals("L'âge moyen devrait être " + expectedAverageAge, 
                     expectedAverageAge, collector.getAverageAgeHistory().get(0), 0.001);
        
        // Vérifier la force moyenne
        float expectedAverageForce = (100 + 90 + 80 + 110) / 4.0f;
        assertEquals("La force moyenne devrait être " + expectedAverageForce, 
                     expectedAverageForce, collector.getAverageForceHistory().get(0), 0.001);
    }
    
    @Test
    public void testRecordEvents() {
        // Enregistrer quelques événements
        collector.recordBirth();
        collector.recordBirth();
        collector.recordDeath();
        collector.recordFoodConsumed();
        collector.recordFoodConsumed();
        collector.recordFoodConsumed();
        collector.recordCombat();
        
        // Mettre à jour les statistiques
        collector.updateStatistics(betes);
        
        // Vérifier que les événements ont été enregistrés
        assertEquals("Le nombre de naissances devrait être 2", Integer.valueOf(2), collector.getBirthHistory().get(0));
        assertEquals("Le nombre de morts devrait être 1", Integer.valueOf(1), collector.getDeathHistory().get(0));
        assertEquals("Le nombre de nourritures consommées devrait être 3", Integer.valueOf(3), collector.getFoodConsumedHistory().get(0));
        assertEquals("Le nombre de combats devrait être 1", Integer.valueOf(1), collector.getCombatHistory().get(0));
        
        // Enregistrer d'autres événements pour la prochaine étape
        collector.recordBirth();
        collector.recordDeath();
        collector.recordDeath();
        
        // Mettre à jour les statistiques à nouveau
        collector.updateStatistics(betes);
        
        // Vérifier que les compteurs ont été réinitialisés
        assertEquals("Le nombre de naissances devrait être 1", Integer.valueOf(1), collector.getBirthHistory().get(1));
        assertEquals("Le nombre de morts devrait être 2", Integer.valueOf(2), collector.getDeathHistory().get(1));
        assertEquals("Le nombre de nourritures consommées devrait être 0", Integer.valueOf(0), collector.getFoodConsumedHistory().get(1));
        assertEquals("Le nombre de combats devrait être 0", Integer.valueOf(0), collector.getCombatHistory().get(1));
    }
    
    @Test
    public void testGetAgeDistribution() {
        // Obtenir la distribution des âges
        Map<Integer, Integer> ageDistribution = collector.getAgeDistribution(betes);
        
        // Vérifier la distribution
        assertEquals("Il devrait y avoir 1 bête d'âge 2", Integer.valueOf(1), ageDistribution.get(2));
        assertEquals("Il devrait y avoir 1 bête d'âge 3", Integer.valueOf(1), ageDistribution.get(3));
        assertEquals("Il devrait y avoir 1 bête d'âge 5", Integer.valueOf(1), ageDistribution.get(5));
        assertEquals("Il devrait y avoir 1 bête d'âge 7", Integer.valueOf(1), ageDistribution.get(7));
    }
    
    @Test
    public void testGetForceDistribution() {
        // Obtenir la distribution des forces
        Map<Integer, Integer> forceDistribution = collector.getForceDistribution(betes);
        
        // Vérifier la distribution
        assertEquals("Il devrait y avoir 1 bête de force 80", Integer.valueOf(1), forceDistribution.get(80));
        assertEquals("Il devrait y avoir 1 bête de force 90", Integer.valueOf(1), forceDistribution.get(90));
        assertEquals("Il devrait y avoir 1 bête de force 100", Integer.valueOf(1), forceDistribution.get(100));
        assertEquals("Il devrait y avoir 1 bête de force 110", Integer.valueOf(1), forceDistribution.get(110));
    }
    
    @Test
    public void testUpdateWithEmptyList() {
        // Mettre à jour avec une liste vide
        collector.updateStatistics(new ArrayList<>());
        
        // Vérifier les statistiques
        assertEquals("La population devrait être 0", Integer.valueOf(0), collector.getPopulationHistory().get(0));
        assertEquals("Le nombre de mâles devrait être 0", Integer.valueOf(0), collector.getMalePopulationHistory().get(0));
        assertEquals("Le nombre de femelles devrait être 0", Integer.valueOf(0), collector.getFemalePopulationHistory().get(0));
        assertEquals("L'âge moyen devrait être 0", 0.0f, collector.getAverageAgeHistory().get(0), 0.001);
        assertEquals("La force moyenne devrait être 0", 0.0f, collector.getAverageForceHistory().get(0), 0.001);
    }
}