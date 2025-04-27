package elements;

import map.Case;

public abstract class Obstacle {
    protected Case position;

   
    public Obstacle(Case position) {
        this.position = position;
    }

    public Case getPosition() {
        return position;
    }

  

    public abstract int impactEnergie();

	public abstract Object getType();
	}

	
