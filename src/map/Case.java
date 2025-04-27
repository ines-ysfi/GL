package map;

import elements.Obstacle;

public class Case {

    private int line; 
    private int column; 
    private boolean isOccupied;
    private boolean isnourriture;
    private Obstacle obstacle; 
	private boolean isobstacle;
	private boolean isReproduction;
	private boolean isCombat;
	private int nombreBetes = 0;

	
    public Case(int line, int column) {
        this.line = line;
        this.column = column;
        this.isOccupied = false;
        this.isnourriture = false;
        this.isobstacle=false;
        this.isReproduction=false;
        this.isCombat=false;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    public boolean contientNourriture() {
        return isnourriture;
    }
    
    public boolean contientObstacle() {
        return isobstacle;
    }
     

    public void setNourriture(boolean isNourriture) {
        this.isnourriture = isNourriture;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public boolean isReproduction() {
        return isReproduction;
    }

    public void setReproduction(boolean R) {
        this.isReproduction = R;
    }
    
    public boolean isCombat() {
        return isCombat;
    }

    public void setCombat(boolean R) {
        this.isCombat = R;
    }
    
    
    public int getNombreBetes() {
        return nombreBetes;
    }

    public void incrementerBete() {
        nombreBetes++;
    }

    public void decrementerBete() {
        if (nombreBetes > 0) nombreBetes--;
    }
    
    public boolean peutAjouterBete() {
        return nombreBetes < 2;
    }

    @Override
    public String toString() {
        return "(" + line + "," + column + ")";
    }
}
