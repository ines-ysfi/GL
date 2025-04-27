package process;

import org.apache.log4j.*;
import log.LoggerUtility;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import elements.Bete;
import elements.Obstacle;
import elements.Nourriture;
import map.Case;
import map.Map;

public class SimulationManager {
    private Map map;
    private List<Bete> betes;
    private List<Obstacle> obstacles;
    private List<Nourriture> nourritures;
    private ArrayList<Reproduction> reproductions;
    private ArrayList<Bete[]> combats;
    private int stepCount=0;
    private final int DUREE_REPRODUCTION=4;
    private int deathCount = 0;
    private int birthCount = 0;
    private StatisticsCollector statisticsCollector; // Ajout du collecteur de statistiques
    
    private static Logger logger=LoggerUtility.getLogger(SimulationManager.class,"html");
    
    public SimulationManager(Map map,List<Bete> betes,List<Obstacle> obstacles,List<Nourriture> nourritures) {
        this.map = map;
        this.betes=betes;
        this.obstacles=obstacles;
        this.nourritures=nourritures;
        this.reproductions=new ArrayList<>();
        this.combats= new ArrayList<>();
        this.statisticsCollector = new StatisticsCollector(); // Initialisation du collecteur
    }
    
    // Getter pour le collecteur de statistiques
    public StatisticsCollector getStatisticsCollector() {
        return statisticsCollector;
    }
    
    public void addBete(Bete b) {
    	betes.add(b);
    }
    
    public void addObstacle(Obstacle o) {
    	obstacles.add(o);
    }
    
    public void addNourriture(Nourriture n) {
    	nourritures.add(n);
    }
    
    

    public void startSimulation() {    
            this.step();
            // Mise à jour des statistiques après chaque étape
            statisticsCollector.updateStatistics(betes);
            logger.info("deput de l'etape: "+ stepCount);
    }
 
    private void step() {
    	moveBetes();
    	checkCombat();
    	checkReproduction();
        interact();
        stepCount++;
    }

    public void moveBetes() {
        Iterator<Bete> iterator = betes.iterator(); 
        while (iterator.hasNext()) {
            Bete bete = iterator.next();
            bete.viellir();
            if(!bete.getReproduction() && !bete.getCombat()) {
            	bete.seDeplacer(map); 
            }
            
            bete.consommerEnergie(-1); 
            if (!bete.getAlive()) {
            	deathCount++;
            	bete.getPosition().decrementerBete();
                statisticsCollector.recordDeath(); // Enregistrement de la mort
                iterator.remove();  
            }
        }    
        
    }


    private void interact() {
    		
        for (Bete bete : betes) {
            Case pos = bete.getPosition();
            
            // 🍃 Effet des obstacles
            Obstacle obs = pos.getObstacle();
            if (obs != null) {
                int effet = obs.impactEnergie();
                bete.consommerEnergie(effet);
                logger.info("Bête #" + bete.getId() + " est affectée par un obstacle (" + obs.getClass().getSimpleName() + ") : " + effet + " énergie");
            }
            
            for (int i = 0; i < nourritures.size(); i++) {
                Nourriture nourriture = nourritures.get(i);
                if (nourriture.getPosition().equals(pos)) {
                	pos.setNourriture(false);
                    bete.consommerEnergie(nourriture.getEnergie());
                    nourritures.remove(i);
                    statisticsCollector.recordFoodConsumed(); // Enregistrement de la consommation de nourriture
                    break;
                }
            }
        }
    }
    
    
    
    private void checkReproduction() {       
    		
        for (int i = 0; i < betes.size() - 1; i++) {
            Bete b1 = betes.get(i);
            
            if (b1.getReproduction()) {
                continue;
            }
            
            for (int j = i + 1; j < betes.size(); j++) {
                Bete b2 = betes.get(j);
                
                if (b2.getReproduction()) {
                    continue;
                }
                
                if (b1.getPosition().equals(b2.getPosition()) && !b1.getSexe().equals(b2.getSexe())) {
                    
                    Bete male = b1.getSexe().equals("Male") ? b1 : b2;
                    Bete female = b1.getSexe().equals("Female") ? b1 : b2;
                    
                    
                    Reproduction repro = new Reproduction(male, female, DUREE_REPRODUCTION);
                    reproductions.add(repro);
                    
                    male.setReproduction(true);
                    female.setReproduction(true);
                    int line=b1.getPosition().getLine();
                    int column=b1.getPosition().getColumn();
                    map.getCase(line,column).setReproduction(true);
                    logger.info("Une reproduction vient de commencer dans la case "+b1.getPosition());
                    break;
                }
            }
        }
        if(reproductions != null) {
    		Iterator<Reproduction> iterReproduction = reproductions.iterator();
            while (iterReproduction.hasNext()) {
                Reproduction repro = iterReproduction.next();
                repro.decrementeTemps();
                
                if (repro.estTerminee()) {
                    
                	Bete bebe = repro.creerBete(map);
                	if (bebe != null) {
                	    betes.add(bebe);
                	    birthCount++;
                        int line=bebe.getPosition().getLine();
                        int column=bebe.getPosition().getColumn();
                        map.getCase(line,column).setReproduction(false);
                	}

                    
                    repro.getMale().setReproduction(false);
                    repro.getFemale().setReproduction(false);
                    
                    iterReproduction.remove();
                    statisticsCollector.recordBirth();
                }
            }
    	}
        
    }
    
    
    
    private void checkCombat() {

        for (int i = 0; i < betes.size() - 1; i++) {
            Bete b1 = betes.get(i);
            if (!b1.getAlive() || b1.getCombat()) continue;

            for (int j = i + 1; j < betes.size(); j++) {
                Bete b2 = betes.get(j);
                if (!b2.getAlive() || b2.getCombat()) continue;

                if (b1.getSexe().equals(b2.getSexe()) && b1.getPosition().equals(b2.getPosition())) {
                    combats.add(new Bete[] { b1, b2 });
                    String sexeCombat=b1.getSexe();
                    b1.setCombat(true);
                    b2.setCombat(true);
                    b1.getPosition().setCombat(true);
                    logger.info("Un combat commence entre Bête #" + b1.getId() +" et Bête #" + b2.getId()+"dans la case : " +b1.getPosition());
                    statisticsCollector.recordCombat(); // Enregistrement du combat
                    break;
                }
            }
        }

        Iterator<Bete[]> iterator = combats.iterator();
        while (iterator.hasNext()) {
            Bete[] duo = iterator.next();
            Bete b1 = duo[0];
            Bete b2 = duo[1];

            if (!b1.getAlive() || !b2.getAlive()) {

                b1.setCombat(false);
                b2.setCombat(false);
                b1.getPosition().setCombat(false);
                iterator.remove();
                deathCount++;
                logger.info("le combat entre "+b1.getId() +"et la bete "+b2.getId()+" est terminé");
            	b1.setAttaque(false);
            	b2.setAttaque(false);
            	b1.setDeff(false);
            	b2.setDeff(false);
                
                
                if (!b1.getAlive()) {
                    b1.getPosition().decrementerBete();
                    betes.remove(b1);
                    statisticsCollector.recordDeath();
                }

                if (!b2.getAlive()) {
                    b2.getPosition().decrementerBete();
                    betes.remove(b2);
                    statisticsCollector.recordDeath();
                }
                continue;
            }

            Combat round;
           
			if (!b1.getAttaque() && !b2.getAttaque()) {
            	b1.setAttaque(true);
            	b2.setAttaque(false);
            	b1.setDeff(false);
            	b2.setDeff(true);
                round = new Combat(b1, b2);
            } else {
            	b1.setAttaque(!b1.getAttaque());
            	b2.setAttaque(!b2.getAttaque());
            	b1.setDeff(!b1.getDeff());
            	b2.setDeff(!b2.getDeff());
            	
                if(b1.getAttaque()) {
                	round=new Combat(b1,b2);
                	System.out.println("la bbete "+b1.getId()+"  est attaquante ");
                	
                }
                else {
                	System.out.println("la bbete "+b2.getId()+"  est attaquante ");
                	round= new Combat(b2,b1);
                }
            }
			
            round.fightTurn();
        }
    }
    


    

    @Override
    public String toString() {
        return "SimulationManager{" +
                "stepCount=" + stepCount +
                ", betes=" + betes +
                ", obstacles=" + obstacles +
                ", nourritures=" + nourritures +
                '}';
    }

    
    public void setNourriture(List<Nourriture> n) {
        this.nourritures=n;
    }
    public List<Bete> getBetes() {
        return betes;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public List<Nourriture> getNourritures() {
        return nourritures;
    }

    public int getStepCount() {
        return stepCount;
    }
    public int getDeathCount() {
        return deathCount;
    }
    
    public int getBirthCount() {
        return birthCount;
    }
}
