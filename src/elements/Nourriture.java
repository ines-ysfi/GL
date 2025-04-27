package elements;

import map.*;

public class Nourriture {

    private int energie=5;
    private Case position;

 
    public Nourriture(Case position) {

        this.position = position;
    }


    public int getEnergie() {
        return energie;
    }

    public Case getPosition() {
        return position;
    }

    
    @Override
    public String toString() {
        return "Nourriture {" +
                ", energie=" + energie +
                ", position=" + position +
                '}';
    }
}