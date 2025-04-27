package testUnit;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import elements.Bete;
import map.*;
import process.Combat;

public class CombatTest {
    
    private Combat combat;
    private Bete bete1;
    private Bete bete2;
    private Map map;
    
    @Before
    public void setUp() {
        map = new Map(10, 10);
        Case position1 = map.getCase(5, 5);
        Case position2 = map.getCase(5, 5); // Même position pour le combat
        
        bete1 = new Bete("Male", 5, 100, position1, 20);
        bete2 = new Bete("Male", 7, 80, position2, 18);
        
        combat = new Combat(bete1, bete2);
    }
    
    @Test
    public void testFightTurn() {
        // Enregistrer les forces initiales
        float initialForce1 = bete1.getForce();
        float initialForce2 = bete2.getForce();
        
        // Exécuter un tour de combat
        combat.fightTurn();
        
        // Vérifier que la force de la bête2 a diminué (car bete1 attaque bete2)
        assertTrue(bete2.getForce() < initialForce2);
        
        // La force de la bête1 ne devrait pas changer car elle ne subit pas d'attaque
        assertEquals(initialForce1, bete1.getForce(), 0.001);
    }
    
    @Test
    public void testFightToTheDeath() {
        // On va créer une bête très forte et une très faible
        Bete strongBete = new Bete("Male", 5, 500, map.getCase(5, 5), 20);
        Bete weakBete = new Bete("Male", 5, 10, map.getCase(5, 5), 20);
        
        Combat deadlyCombat = new Combat(strongBete, weakBete);
        
        // Un seul tour devrait suffire pour tuer la bête faible
        deadlyCombat.fightTurn();
        
        // La bête faible devrait être morte ou très affaiblie
        assertTrue(weakBete.getForce() <= 0 || !weakBete.getAlive());
    }
    
    @Test
    public void testMultipleFightTurns() {
        // Créer deux bêtes identiques
        Bete beteA = new Bete("Male", 5, 100, map.getCase(5, 5), 20);
        Bete beteB = new Bete("Male", 5, 100, map.getCase(5, 5), 20);
        
        Combat equalCombat = new Combat(beteA, beteB);
        
        // Simuler plusieurs tours de combat
        for (int i = 0; i < 5; i++) {
            if (beteA.getAlive() && beteB.getAlive()) {
                equalCombat.fightTurn();
                // Inverser les rôles pour simuler l'alternance des attaques
                equalCombat = new Combat(beteB, beteA);
            }
        }
        
        // Les deux bêtes devraient avoir perdu de la force
        assertTrue(beteA.getForce() < 100);
        assertTrue(beteB.getForce() < 100);
    }
}