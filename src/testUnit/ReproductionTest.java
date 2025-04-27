package testUnit;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import elements.Bete;
import map.Case;
import map.Map;
import process.*;

public class ReproductionTest {
    
    private Reproduction reproduction;
    private Bete male;
    private Bete female;
    private Map map;
    private int dureeReproduction = 4;
    
    @Before
    public void setUp() {
        // Créer une carte
        map = new Map(5, 5);
        
        // Créer une position pour les bêtes
        Case position = map.getCase(2, 2);
        
        // Créer un mâle et une femelle
        male = new Bete("Male", 5, 100, position, 20);
        female = new Bete("Female", 5, 80, position, 20);
        
        // Incrémenter le nombre de bêtes sur la position
        position.incrementerBete();
        position.incrementerBete();
        
        // Créer l'objet Reproduction
        reproduction = new Reproduction(male, female, dureeReproduction);
    }
    
    @Test
    public void testDecrementeTemps() {
        // La reproduction ne devrait pas être terminée initialement
        assertFalse("La reproduction ne devrait pas être terminée initialement", reproduction.estTerminee());
        
        // Décrémenter le temps jusqu'à la fin
        for (int i = 0; i < dureeReproduction; i++) {
            reproduction.decrementeTemps();
        }
        
        // Vérifier que la reproduction est terminée
        assertTrue("La reproduction devrait être terminée après " + dureeReproduction + " étapes", reproduction.estTerminee());
    }
    
    @Test
    public void testCreerBete() {
        // Créer une case voisine libre
        Case voisine = map.getCase(2, 3);
        
        // Créer un bébé
        Bete bebe = reproduction.creerBete(map);
        
        // Vérifier que le bébé n'est pas null
        assertNotNull("Le bébé ne devrait pas être null", bebe);
        
        // Vérifier l'âge du bébé
        assertEquals("L'âge du bébé devrait être 0", 0, bebe.getAge());
        
        // Vérifier la force du bébé (moyenne des parents)
        float expectedForce = (male.getForce() + female.getForce()) / 2;
        assertEquals("La force du bébé devrait être la moyenne des forces des parents", 
                     expectedForce, bebe.getForce(), 0.001);
        
        // Vérifier que le bébé est placé sur une case valide
        assertNotNull("La position du bébé ne devrait pas être null", bebe.getPosition());
    }
    
    @Test
    public void testCreerBeteSansCasesLibres() {
        // Remplir toutes les cases voisines
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                if (i == 2 && j == 2) continue; // Ignorer la case des parents
                
                Case c = map.getCase(i, j);
                c.incrementerBete();
                c.incrementerBete(); // Maximum de bêtes
                c.setReproduction(true); // Ou en reproduction
            }
        }
        
        // La case des parents est déjà pleine avec 2 bêtes
        
        // Essayer de créer un bébé
        Bete bebe = reproduction.creerBete(map);
        
        // Le bébé devrait être null car aucune case n'est disponible
        assertNull("Le bébé devrait être null car aucune case n'est disponible", bebe);
    }
    
    @Test
    public void testGetters() {
        assertEquals("Le mâle devrait être celui passé au constructeur", male, reproduction.getMale());
        assertEquals("La femelle devrait être celle passée au constructeur", female, reproduction.getFemale());
    }
}