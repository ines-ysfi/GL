package elements;

import map.Case;

public class Foret extends Obstacle {

    
    public Foret(Case position) {
        super(position);
    }
    public String getType() {
        return "foret";
    }


    @Override
    public int impactEnergie() {
        return 10;
    }
}
