package map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import elements.*;

public class Map {
	
    private int lines;
    private int columns; 
    private Case[][] grille;

    public Map(int l, int c) {
        this.lines = l;
        this.columns = c;
        grille = new Case[lines][columns];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < c; j++) {
                grille[i][j] = new Case(i, j);
            }
        }
    }

  
    public int getLines() {
        return lines;
    }

    public int getColumns() {
        return columns;
    }

    public void addObstacle(Obstacle obstacle) {
        Case position = obstacle.getPosition();
        grille[position.getLine()][position.getColumn()].setObstacle(obstacle);
    }

    public Case getCase(int line, int column) {
        return grille[line][column];
    }

    public ArrayList<Case> CasesVoisines(Case caseActuelle) {
        ArrayList<Case> voisines = new ArrayList<>();

        int x = caseActuelle.getLine();
        int y = caseActuelle.getColumn();

        if (isValidPosition(x - 1, y)) {
            Case voisine = grille[x - 1][y];
            if (voisine.peutAjouterBete()) {
                voisines.add(voisine);
            }
        }

        if (isValidPosition(x + 1, y)) {
            Case voisine = grille[x + 1][y];
            if (voisine.peutAjouterBete()) {
                voisines.add(voisine);
            }
        }

        if (isValidPosition(x, y - 1)) {
            Case voisine = grille[x][y - 1];
            if (voisine.peutAjouterBete()) {
                voisines.add(voisine);
            }
        }

        if (isValidPosition(x, y + 1)) {
            Case voisine = grille[x][y + 1];
            if (voisine.peutAjouterBete()) {
                voisines.add(voisine);
            }
        }

        return voisines;
    }



    public boolean isValidPosition(int line, int column) {
        return line >= 0 && line < lines && column >= 0 && column < columns;
    }

    public Case getRandomFreeCase() {
        List<Case> freeCases = new ArrayList<>();

        
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                Case c = getCase(i, j);
                if (!c.isOccupied() && c.getNombreBetes()==0) {
                    freeCases.add(c);
                }
            }
        }

        
        if (freeCases.isEmpty()) {
            return null;
        }

        
        Random random = new Random();
        return freeCases.get(random.nextInt(freeCases.size()));
    }



}
