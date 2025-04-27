package elements;

import map.Case;

public class Riviere extends Obstacle {

  
    public Riviere(Case position) {
        super(position);
    }
    
    public String getType() {
        return "Rivière";
    }

   
    @Override
    public int impactEnergie() {
        return -4;
    }
}