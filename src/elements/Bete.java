package elements;

import map.*;

import org.apache.log4j.*;
import log.LoggerUtility;
import java.util.ArrayList;


public class Bete {
	
	public static int nbBetes=0;
	
	private boolean enReproduction;
    private String sexe;
    private int age;
    private float force;
    private Case position;
    private boolean alive;
    private int id=0;
    private int ageMort;
    private int compteurVieillir=3;
    private boolean Suivi=false;
    private boolean enCombat=false;
    
    private boolean isAttaque=false;
    private boolean isDeff=false;
    
    private static final Logger logger = LoggerUtility.getLogger(Bete.class, "html");
    
    public Bete(String sexe, int age, float force, Case position,int ageMort) {
        this.sexe = sexe;
        this.age = age;
        this.force = force;
        this.position = position;
        this.alive = true;
        this.enReproduction = false;
        this.id=nbBetes++;
        this.ageMort=ageMort;

    }



    public Case getPosition() {
        return position;
    }

    public void consommerEnergie(int amount) {
		  this.force += amount;
    	if (force >= 100) {
    		  this.force = 100;
    	}
      
        if (this.force <= 0) {
        	this.force = 0;
            this.alive = false; 
        }
    }
    
    public void viellir() {
    	
    	if(compteurVieillir==0) {
    		this.age++;
    		compteurVieillir=3;
    	}
    	else {
    		compteurVieillir--;
    	}
    	
    	if(age>ageMort) {
    		this.alive=false;
    	}
    }

    public boolean getAlive() {
        return alive;
    }

    
    public void seDeplacer(Map map) {
       Case newPosition=choixCaseDeplacement(position,map);
       
       if(!newPosition.equals(position)) {
    	   logger.debug("la bete #"+id +"se deplace de la case:"+position +"vers : "+ newPosition);
    	   position.setOccupied(false);
    	   position.decrementerBete();
    	   position=newPosition;
    	   position.setOccupied(true);
    	   position.incrementerBete();
       }
       else {
    	   logger.debug("la bete #"+id +"reste dans la meme case: "+position);
       }
       
    }
    
    public Case choixCaseDeplacement(Case position,Map map) {
    	 ArrayList<Case> voisines = map.CasesVoisines(position);
    	 ArrayList<Case> possibles=new ArrayList<Case>();
    	 
    	 for(Case c: voisines) {
    		 if(c.contientNourriture() && c.getNombreBetes()<2) {
    			 possibles.add(c);
    		 }
    		 
    	 }
    	 
    	 if(!possibles.isEmpty()) {
    		 Case newPos= possibles.get((int)(Math.random() * possibles.size()));
    		 return newPos;
    	 }
    	 
    	 for (Case c : voisines) {
    		    if (!c.isReproduction() && !c.isCombat() && c.getNombreBetes() < 2) {
    		        possibles.add(c);
    		    }
    		}
    	 if(!possibles.isEmpty()) {
    		 Case newPos= possibles.get((int)(Math.random() * possibles.size()));
    		 return newPos;
    	 }
    	 else {
    		 return position;
    	 }
    	 
    	
    }

	public float getForce() {

		return force;
	}
	
	public void setReproduction(boolean Val) {
		this.enReproduction=Val;
	}
	public void setSuivi(boolean Val) {
		this.Suivi=Val;
	}
	public boolean getSuivi() {
		return Suivi;
	}
	public boolean getReproduction() {
		return enReproduction;
	}
	public String getSexe() {
		return sexe;
	}
	
	public int getAge() {
		return age;
	}
	
	public int getAgeMort() {
		return ageMort;
	}
	
	public int getId() {
		return id;
	}
	public void setCombat(boolean Val) {
		this.enCombat=Val;
	}
    
	public boolean getCombat() {
		return enCombat;
	}
	
	public void setAttaque(boolean Val) {
		this.isAttaque=Val;
	}
    
	public boolean getAttaque () {
		return isAttaque;
	}
	
	
	public void setDeff(boolean Val) {
		this.isDeff=Val;
	}
    
	public boolean getDeff() {
		return isDeff;
	}
	
	
	public String toString() {
		return "id bete:"+id+"age:"+age+"position"+position.toString()+"sexe:"+sexe+" surCase:"+ position.getNombreBetes();
	}
}
