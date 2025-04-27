package elements;



import map.Case;

public class Montagne extends Obstacle {

  
    public Montagne(Case position) {
        super(position);
    }
    public String getType() {
        return "Montagne";
    }


    @Override
    public int impactEnergie() {
        return -5;
    }
}
