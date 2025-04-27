package process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import elements.Bete;

/**
 * Classe qui collecte et stocke les statistiques de la simulation
 */
public class StatisticsCollector {
    // Statistiques globales
    private List<Integer> populationHistory = new ArrayList<>();
    private List<Integer> malePopulationHistory = new ArrayList<>();
    private List<Integer> femalePopulationHistory = new ArrayList<>();
    private List<Float> averageAgeHistory = new ArrayList<>();
    private List<Float> averageForceHistory = new ArrayList<>();
    private List<Integer> birthHistory = new ArrayList<>();
    private List<Integer> deathHistory = new ArrayList<>();
    private List<Integer> foodConsumedHistory = new ArrayList<>();
    private List<Integer> combatHistory = new ArrayList<>();
    
    // Statistiques par étape
    private int currentBirths = 0;
    private int currentDeaths = 0;
    private int currentFoodConsumed = 0;
    private int currentCombats = 0;
    
    /**
     * Met à jour les statistiques pour l'étape actuelle
     * @param betes Liste des bêtes actuelles
     */
    public void updateStatistics(List<Bete> betes) {
        // Population
        populationHistory.add(betes.size());
        
        // Répartition mâle/femelle
        int maleCount = 0;
        int femaleCount = 0;
        float totalAge = 0;
        float totalForce = 0;
        
        for (Bete bete : betes) {
            if (bete.getSexe().equals("Male")) {
                maleCount++;
            } else {
                femaleCount++;
            }
            totalAge += bete.getAge();
            totalForce += bete.getForce();
        }
        
        malePopulationHistory.add(maleCount);
        femalePopulationHistory.add(femaleCount);
        
        // Âge moyen et force moyenne
        float averageAge = betes.isEmpty() ? 0 : totalAge / betes.size();
        float averageForce = betes.isEmpty() ? 0 : totalForce / betes.size();
        
        averageAgeHistory.add(averageAge);
        averageForceHistory.add(averageForce);
        
        // Autres statistiques
        birthHistory.add(currentBirths);
        deathHistory.add(currentDeaths);
        foodConsumedHistory.add(currentFoodConsumed);
        combatHistory.add(currentCombats);
        
        // Réinitialiser les compteurs pour la prochaine étape
        resetCurrentStepCounters();
    }
    
    /**
     * Réinitialise les compteurs pour la prochaine étape
     */
    private void resetCurrentStepCounters() {
        currentBirths = 0;
        currentDeaths = 0;
        currentFoodConsumed = 0;
        currentCombats = 0;
    }
    
    /**
     * Incrémente le nombre de naissances
     */
    public void recordBirth() {
        currentBirths++;
    }
    
    /**
     * Incrémente le nombre de morts
     */
    public void recordDeath() {
        currentDeaths++;
    }
    
    /**
     * Incrémente le nombre de nourriture consommée
     */
    public void recordFoodConsumed() {
        currentFoodConsumed++;
    }
    
    /**
     * Incrémente le nombre de combats
     */
    public void recordCombat() {
        currentCombats++;
    }
    
    // Getters pour les statistiques
    public List<Integer> getPopulationHistory() {
        return populationHistory;
    }
    
    public List<Integer> getMalePopulationHistory() {
        return malePopulationHistory;
    }
    
    public List<Integer> getFemalePopulationHistory() {
        return femalePopulationHistory;
    }
    
    public List<Float> getAverageAgeHistory() {
        return averageAgeHistory;
    }
    
    public List<Float> getAverageForceHistory() {
        return averageForceHistory;
    }
    
    public List<Integer> getBirthHistory() {
        return birthHistory;
    }
    
    public List<Integer> getDeathHistory() {
        return deathHistory;
    }
    
    public List<Integer> getFoodConsumedHistory() {
        return foodConsumedHistory;
    }
    
    public List<Integer> getCombatHistory() {
        return combatHistory;
    }
    
    /**
     * Retourne la répartition des âges actuels
     * @param betes Liste des bêtes
     * @return Map avec l'âge comme clé et le nombre de bêtes comme valeur
     */
    public Map<Integer, Integer> getAgeDistribution(List<Bete> betes) {
        Map<Integer, Integer> ageDistribution = new HashMap<>();
        
        for (Bete bete : betes) {
            int age = bete.getAge();
            if (ageDistribution.containsKey(age)) {
                ageDistribution.put(age, ageDistribution.get(age) + 1);
            } else {
                ageDistribution.put(age, 1);
            }
        }
        
        return ageDistribution;
    }
    
    /**
     * Retourne la répartition des forces actuelles
     * @param betes Liste des bêtes
     * @return Map avec la force comme clé et le nombre de bêtes comme valeur
     */
    public Map<Integer, Integer> getForceDistribution(List<Bete> betes) {
        Map<Integer, Integer> forceDistribution = new HashMap<>();
        
        for (Bete bete : betes) {
            int force = (int) bete.getForce();
            if (forceDistribution.containsKey(force)) {
                forceDistribution.put(force, forceDistribution.get(force) + 1);
            } else {
                forceDistribution.put(force, 1);
            }
        }
        
        return forceDistribution;
    }
}