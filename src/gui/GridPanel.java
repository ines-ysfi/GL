package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import map.*;
import process.SimulationBuilder;
import process.SimulationManager;
import elements.*;


public class GridPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int cellSize;
    private Map map;
    private SimulationManager manager;
    private ImageIcon grassIcon;

    public GridPanel( Map map, SimulationManager manager) {
        this.map = map;
        this.manager = manager; 
        adjustCellSize();
    }

    
    private void adjustCellSize() {
        int width = getWidth();
        int height = getHeight();
        int columns = map.getColumns();
        int rows = map.getLines();
        cellSize = Math.min(width / columns, height / rows);
    }
    
    public int getCellSize() {
    	return cellSize;
    }

    
    @Override
    protected void paintComponent(Graphics g) {
    	adjustCellSize();
    	
    	
    	grassIcon = new ImageIcon(getClass().getResource("/img/tile3.png"));
    	this.setBackground(Color.WHITE);
        super.paintComponent(g);
        
        if (map != null) {
            for (int i = 0; i < map.getLines(); i++) {
                for (int j = 0; j < map.getColumns(); j++) {
                    if (grassIcon != null && grassIcon.getImage() != null) {
                        g.drawImage(grassIcon.getImage(), j * cellSize, i * cellSize, cellSize, cellSize, this);
                    }

                    g.setColor(Color.lightGray);
                    g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);

                    Case c = map.getCase(i, j);

                    Obstacle obstacle = c.getObstacle();
                    if (obstacle != null) {
                        ImageIcon icon = null;
                        if (obstacle.getType().equals("Rivière")) {
                            icon = new ImageIcon(getClass().getResource("/img/Water.jpg"));
                        } else if (obstacle.getType().equals("foret")) {
                            icon = new ImageIcon(getClass().getResource("/img/forest.png"));
                        } else if (obstacle.getType().equals("Montagne")) {
                            icon = new ImageIcon(getClass().getResource("/img/monta.png"));
                        }

                        if (icon != null) {
                        	g.drawImage(icon.getImage(), j * cellSize, i * cellSize, cellSize, cellSize, this);

                        }
                    }


                    for (Bete b : manager.getBetes()) {
                    	 if (b.getPosition().equals(c)) {
                         	ImageIcon beteImg = null;
                         	if(b.getReproduction()) {
                         		 g.setColor(new Color(255, 105, 180, 100));
                                 g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                         		 beteImg = new ImageIcon(getClass().getResource("/img/reproduction.png"));
                         	}
                             else if(b.getCombat() ) {
                            	 g.setColor(new Color(150, 0, 150, 100));
                                 g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                            	 if(b.getSexe().equals("Female")) {
                                     beteImg = new ImageIcon(getClass().getResource("/img/combat.png")); 
                            	 }
                            	 else if(b.getSexe().equals("Male")) {
                                     beteImg = new ImageIcon(getClass().getResource("/img/combatMale.png")); 
                            	 }
                                 
                                 
                             }
                         	else if(b.getSuivi()) {
                         			int k=b.getPosition().getLine();
                         			int l=b.getPosition().getColumn();
                         		  	Case pos=map.getCase(k,l);
                         		  	g.setColor(new Color(255, 215, 0, 200)); // Gold
                         	        g.fillRect(pos.getColumn() * cellSize, pos.getLine() * cellSize, cellSize, cellSize);
                         	        if(b.getSexe().equals("Male")) {
                         	        	beteImg = new ImageIcon(getClass().getResource("/img/ladyB.png"));
                         	        }
                         	        else {
                         	        	 beteImg = new ImageIcon(getClass().getResource("/img/ladyR.png"));
                         	        }
                         	       
                         	}
                        	else {
                        		if(b.getSexe().equals("Male")) {
                    	        	beteImg = new ImageIcon(getClass().getResource("/img/ladyB.png"));
                    	        }
                    	        else {
                    	        	 beteImg = new ImageIcon(getClass().getResource("/img/ladyR.png"));
                    	        }
                        		
                        	}
                        	
                            
                            g.drawImage(beteImg.getImage(), j * cellSize + 5, i * cellSize + 5, cellSize - 10, cellSize - 10, this);
                         // Dans la méthode paintComponent, après avoir dessiné l'image de la bête
                         // Placer ce code après la ligne g.drawImage(beteImg.getImage(), ...)

                         // Dessiner la barre d'énergie en DESSOUS de la bête
                         int barWidth = cellSize - 12; // Légèrement plus petite que la bête
                         int barHeight = 5;
                         int barX = j * cellSize + 6;
                         int barY = i * cellSize + cellSize - 8; // En bas de la case, au-dessus de la bordure

                         // Dessiner le contour de la barre
                         g.setColor(Color.BLACK);
                         g.drawRect(barX, barY, barWidth, barHeight);

                         // Calculer la proportion d'énergie
                         float energyRatio = b.getForce() / 100.0f;
                         energyRatio = Math.max(0, Math.min(1, energyRatio));

                         // Couleur en fonction du niveau d'énergie
                         if (energyRatio > 0.7f) {
                             g.setColor(Color.GREEN);
                         } else if (energyRatio > 0.4f) {
                             g.setColor(Color.YELLOW);
                         } else {
                             g.setColor(Color.RED);
                         }

                         // Remplir la barre proportionnellement
                         int filledWidth = (int)(barWidth * energyRatio);
                         g.fillRect(barX + 1, barY + 1, filledWidth - 1, barHeight - 1);


                    }
                    
                    if(manager.getNourritures().isEmpty()) {
                    	SimulationBuilder builder=new SimulationBuilder();
                    	Random rand=new Random();
                    	int count=rand.nextInt(6) + 1;
                    	manager.setNourriture(builder.initializeNourriture(map,count));  
                    	
                    }

                    for (Nourriture n : manager.getNourritures()) {
                        if (n.getPosition().equals(c)) {
                            ImageIcon icon = new ImageIcon(getClass().getResource("/img/Pataepollo.png"));
                            g.drawImage(icon.getImage(), j * cellSize + 5, i * cellSize + 5, cellSize - 10, cellSize - 10, this);
                        }
                    }
                }
            }
        }
        
        
        
    }   
    
    
    
    }}
