package process;

import java.util.ArrayList;
import org.apache.log4j.*;

import map.Map;
import java.util.Random;

import elements.Bete;
import log.LoggerUtility;
import map.Case;

public class Reproduction {

    private Bete male;
    private Bete female;
    private int tempsRestant;
    private static Logger logger=LoggerUtility.getLogger(Reproduction.class, "html");
    
    public Reproduction(Bete m, Bete f, int dureeReproduction) {
        this.male = m;
        this.female = f;
        this.tempsRestant = dureeReproduction;
    }
    
    
    
    public boolean estTerminee() {
        return tempsRestant <= 0;
    }
    
    public void decrementeTemps() {
        tempsRestant--;
    }
    
    public Bete getMale() {
        return male;
    }
    
    public Bete getFemale() {
        return female;
    }



    public Bete creerBete(Map map) {
        Random random = new Random();
        String sexe = random.nextBoolean() ? "Male" : "Female";
        int age = 0;
        Case positionMere = female.getPosition();
        ArrayList<Case> voisinesLibres = map.CasesVoisines(positionMere);

        // 🔍 Filtrage des cases valides
        ArrayList<Case> voisinesValides = new ArrayList<>();
        for (Case c : voisinesLibres) {
            if (c.getNombreBetes() < 2 && !c.isReproduction() && !c.isCombat()) {
                voisinesValides.add(c);
            }
        }

        Case position;
        if (!voisinesValides.isEmpty()) {
            position = voisinesValides.get(random.nextInt(voisinesValides.size()));
        } else if (positionMere.getNombreBetes() < 2 && !positionMere.isReproduction() && !positionMere.isCombat()) {
            // fallback: place in mother's case only if valid
            position = positionMere;
        } else {
            // no valid position available
        	logger.warn("Reroduction echouee( aucune case dispo)");
            System.out.println("🚫 Aucune case disponible pour placer le bébé.");
            return null;
        }

        float force = (female.getForce() + male.getForce()) / 2;
        int ageMoyenMort = 15 + random.nextInt(11);

        Bete bebe = new Bete(sexe, age, force, position, ageMoyenMort);
        logger.warn("Une nouvelle bete vient de naitre dans la case "+ bebe.getPosition());
        position.incrementerBete(); // Important: mettre à jour le nombre de bêtes sur la case
        return bebe;
    }


}
