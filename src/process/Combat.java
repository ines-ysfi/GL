package process;

import elements.Bete;
import log.LoggerUtility;

import java.util.Random;

import org.apache.log4j.Logger;

public class Combat {
    private Bete bete1;
    private Bete bete2;
    private Random random;
    private static Logger logger=LoggerUtility.getLogger(Reproduction.class, "html");

    public Combat(Bete bete1, Bete bete2) {
        this.bete1 = bete1;
        this.bete2 = bete2;
        this.random = new Random();
    }
    
    public void fightTurn() {
        attack(bete1, bete2);
        if (!bete2.getAlive()) {
        	logger.info("Bête #" + bete2.getId() + " est morte.");
            return;
        }
    }


    private void attack(Bete attacker, Bete defender) {
        float attackerPower = calculateCombatPower(attacker);
        float defenderPower = calculateCombatPower(defender);

        float damage = calculateDamage(attackerPower, defenderPower);

        applyDamage(defender, damage);
    }

    private float calculateCombatPower(Bete bete) {
        float power = bete.getForce();

        int age = bete.getAge();
        float ageFactor;

        if (age < 2) {
            ageFactor = 0.7f;
        } else if (age >= 2 && age <= 10) {
            ageFactor = 1.0f;
        } else {
            ageFactor = 0.9f;
        }

        float randomFactor = 0.8f + (random.nextFloat() * 0.4f);

        return power * ageFactor * randomFactor;
    }


    private float calculateDamage(float attackerPower, float defenderPower) {
        float baseDamage = defenderPower * (0.1f + (random.nextFloat() * 0.2f));
        float powerRatio = attackerPower / defenderPower;
        float finalDamage = baseDamage * powerRatio;
        return Math.max(5, Math.min(finalDamage, 50)); 
    }

    private void applyDamage(Bete defender, float damage) {
        defender.consommerEnergie((int) -damage);
        logger.debug("Bête #" + defender.getId() + " a perdu " + damage + " points de force");
    }
}
