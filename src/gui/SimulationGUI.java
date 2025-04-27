package gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;

import process.*;
import map.*;
import elements.*;

import java.util.List;

public class SimulationGUI implements Runnable {
	
    private JPanel infoPanel; 
    private SimulationManager manager;
    private Map map;
    private boolean isPaused = false;  
    private JButton pauseButton;
    private JButton statsButton;
    private JButton rapideButton;
    private JButton moyenButton;
    private JButton lentButton;
    private GridPanel gridPanel;
    private JPanel mainPanel;
    private JPanel southPanel;
    private JPanel eastPanel;
    private BeteInfoPanel beteInfoPanel;
    private JFrame frame;
    private int simulationSpeed = 2000; // Vitesse par défaut (2000ms)
    
    private final int MAX_STEP=100;
    

    
    // Couleurs thématiques (reprises d'IntroFrame pour cohérence)
    private final Color BG_COLOR = new Color(40, 40, 40);           // Noir foncé
    private final Color ACCENT_COLOR = new Color(220, 60, 60);      // Rouge
    private final Color TEXT_COLOR = new Color(240, 240, 240);      // Blanc cassé
    private final Color SECONDARY_COLOR = new Color(80, 80, 80);    // Gris foncé
    private final Color BUTTON_COLOR = new Color(220, 60, 60);      // Rouge pour boutons
    private final Color CONTROL_PANEL = new Color(28, 28, 28);
    private final Color STAT_COLOR = new Color(0, 121, 107);
    private final Color RLM = new Color(142, 68, 173);
    
    

    public SimulationGUI(int gridSize, int betesCount, int obstaclesCount, int nourritureCount) {
        frame = new JFrame("Simulation de Bêtes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);
        
        // Création des composants de l'interface
        setupComponents(gridSize, betesCount, obstaclesCount, nourritureCount);
        
        // Configuration de la fenêtre
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void setupComponents(int gridSize, int betesCount, int obstaclesCount, int nourritureCount) {
        // Panneau de contrôle en haut
        JPanel controlPanel = createControlPanelPS();
        JPanel vPanel = createControlPanelV();
        
        // Panneau d'informations à gauche
        infoPanel = createInfoPanel();
        
        // Configuration de la simulation
        SimulationBuilder simulation = new SimulationBuilder();
        map = simulation.buildMap(gridSize, gridSize);
        List<Bete> betes = simulation.initializeBetes(map, betesCount);
        List<Obstacle> obstacles = simulation.initializeObstacles(map, obstaclesCount);
        List<Nourriture> nourritures = simulation.initializeNourriture(map, nourritureCount);
        manager = new SimulationManager(map, betes, obstacles, nourritures);
        
        // Panneau de la grille central
        gridPanel = new GridPanel(map, manager);
        gridPanel.addMouseListener(new MouseControls());
        
        // Panneau pour les infos d'une bête sélectionnée à droite
        beteInfoPanel = createBeteInfoPanel();
        
        // Assemblage du panneau principal
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);

        mainPanel.add(createGridContainer(), BorderLayout.CENTER);
        mainPanel.add(infoPanel, BorderLayout.WEST);

        
        southPanel = new JPanel(new BorderLayout());
        southPanel.setPreferredSize(new Dimension(300, 100));
        southPanel.add(controlPanel, BorderLayout.NORTH);
        southPanel.add(vPanel, BorderLayout.SOUTH);
        
        
        eastPanel = new JPanel(new BorderLayout());
        eastPanel.setPreferredSize(new Dimension(300, 800));
        eastPanel.add(southPanel, BorderLayout.SOUTH);
        eastPanel.add(beteInfoPanel, BorderLayout.NORTH);
        mainPanel.add(eastPanel, BorderLayout.EAST);

    }
    
    
    private JPanel createControlPanelPS() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        controlPanel.setPreferredSize(new Dimension(300, 50));
        controlPanel.setBackground(CONTROL_PANEL);
        
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Boutons principaux
        pauseButton = createStyledButton("Pause");
        statsButton = createStyledButton("Statistiques");


        // Ajout des composants
        controlPanel.add(pauseButton);
        controlPanel.add(statsButton);
        controlPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        

        // Listeners pause/statistiques
        pauseButton.addActionListener(new PauseButtonListener());
        statsButton.addActionListener(new StatsButtonListener());
        
        return controlPanel;
    }
    
    
	private JPanel createControlPanelV() {
        JPanel vPanel = new JPanel();
        vPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
 
        vPanel.setPreferredSize(new Dimension(300, 50));

        vPanel.setBackground(CONTROL_PANEL);
        
        vPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        rapideButton = createStyledButton("Rapide");
        moyenButton = createStyledButton("Moyen");
        lentButton = createStyledButton("Lent");

        simulationSpeed = 1500;

        
        vPanel.add(lentButton);
        vPanel.add(moyenButton);
        vPanel.add(rapideButton);


        rapideButton.addActionListener(new RapideButtonListener());
        moyenButton.addActionListener(new MoyenButtonListener());
        lentButton.addActionListener(new LentButtonListener());
        
        return vPanel;
    }

    
    private JButton createStyledButton(String text) {
    	JButton button = new JButton(text);
    	
    	switch (text) {
        case "Pause":
            button.setBackground(BUTTON_COLOR);
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(BUTTON_COLOR.brighter());
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBackground(BUTTON_COLOR);
                }
            });
            break;

        case "Statistiques":
            button.setBackground(STAT_COLOR);
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(STAT_COLOR.brighter());
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBackground(STAT_COLOR);
                }
            });
            break;

        case "Rapide":
            button.setBackground(RLM);
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(RLM.brighter());
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBackground(RLM);
                }
            });
            break;
            
        case "Moyen":
            button.setBackground(RLM);
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(RLM.brighter());
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBackground(RLM);
                }
            });
            break;
            
        case "Lent":
            button.setBackground(RLM);
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(RLM.brighter());
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBackground(RLM);
                }
            });
            break;

        default:
            button.setBackground(Color.LIGHT_GRAY);
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(STAT_COLOR.brighter());
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBackground(STAT_COLOR);
                }
            });
            break;
    }
        

        button.setForeground(TEXT_COLOR);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Effet de survol
        
        
        return button;
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(SECONDARY_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 0, 2, ACCENT_COLOR),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setPreferredSize(new Dimension(250, 800));
        
        // Titre du panneau
        JLabel titleLabel = new JLabel("INFORMATIONS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        return panel;
    }
    
    private BeteInfoPanel createBeteInfoPanel() {
        BeteInfoPanel panel = new BeteInfoPanel();
        panel.setPreferredSize(new Dimension(300, 560));
        
        // Application du style (à ajouter dans BeteInfoPanel)
        panel.setBackground(SECONDARY_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 2, 0, 0, ACCENT_COLOR),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        return panel;
    }
    
    private JPanel createGridContainer() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(BG_COLOR);
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Titre au-dessus de la grille
        JLabel gridTitle = new JLabel("ENVIRONNEMENT DE SIMULATION", JLabel.CENTER);
        gridTitle.setFont(new Font("Arial", Font.BOLD, 16));
        gridTitle.setForeground(TEXT_COLOR);
        gridTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        container.add(gridTitle, BorderLayout.NORTH);
        container.add(gridPanel, BorderLayout.CENTER);
        
        // Instructions en bas de la grille
        JLabel instructionLabel = new JLabel("Cliquez sur une bête pour afficher ses informations", JLabel.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        instructionLabel.setForeground(TEXT_COLOR);
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        container.add(instructionLabel, BorderLayout.SOUTH);
        
        return container;
    }

    private void updateInfoPanel() {
        infoPanel.removeAll();
        
        // Recréer le titre
        JLabel titleLabel = new JLabel("INFORMATIONS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        infoPanel.add(titleLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        if (manager != null) {
            // Créer des panels stylisés pour chaque info
            addInfoSection(infoPanel, "Étape", Integer.toString(manager.getStepCount()));
            addInfoSection(infoPanel, "Population", Integer.toString(manager.getBetes().size()));
            addInfoSection(infoPanel, "Obstacles", Integer.toString(manager.getObstacles().size()));
            addInfoSection(infoPanel, "Nourriture restante", Integer.toString(manager.getNourritures().size()));
            addInfoSection(infoPanel, "Bêtes nées", Integer.toString(manager.getBirthCount()));
            addInfoSection(infoPanel, "Bêtes mortes", Integer.toString(manager.getDeathCount()));
            
            // Ajouter des statistiques supplémentaires
            infoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            JLabel statsTitle = new JLabel("STATISTIQUES ACTUELLES");
            statsTitle.setFont(new Font("Arial", Font.BOLD, 16));
            statsTitle.setForeground(TEXT_COLOR);
            statsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            infoPanel.add(statsTitle);
            infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            
            // Compter le nombre de bêtes par sexe
            int maleCount = 0;
            int femaleCount = 0;
            
            for (Bete bete : manager.getBetes()) {
                if (bete.getSexe().equals("Male")) {
                    maleCount++;
                } else {
                    femaleCount++;
                }
            }
            
            addInfoSection(infoPanel, "Mâles", Integer.toString(maleCount));
            addInfoSection(infoPanel, "Femelles", Integer.toString(femaleCount));
            
            // Compter les événements actuels
            int reproductionCount = 0;
            int combatCount = 0;
            
            for (Bete bete : manager.getBetes()) {
                if (bete.getReproduction()) {
                    reproductionCount++;
                }
                if (bete.getCombat()) {
                    combatCount++;
                }
            }
            
            if (reproductionCount > 0) {
                addInfoSection(infoPanel, "Reproduction en cours", Integer.toString(reproductionCount/2));
            }
            
            if (combatCount > 0) {
                addInfoSection(infoPanel, "Combats en cours", Integer.toString(combatCount/2));
            }
        }

        infoPanel.revalidate();
        infoPanel.repaint();
    }
    
    private void addInfoSection(JPanel panel, String label, String value) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
        sectionPanel.setBackground(BG_COLOR);
        sectionPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 1, 0, ACCENT_COLOR.darker()),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        sectionPanel.setMaximumSize(new Dimension(250, 60));
        sectionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel labelText = new JLabel(label);
        labelText.setFont(new Font("Arial", Font.BOLD, 12));
        labelText.setForeground(TEXT_COLOR.darker());
        
        JLabel valueText = new JLabel(value);
        valueText.setFont(new Font("Arial", Font.BOLD, 18));
        valueText.setForeground(TEXT_COLOR);
        
        sectionPanel.add(labelText);
        sectionPanel.add(valueText);
        
        panel.add(sectionPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    
    
    private class PauseButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            isPaused = !isPaused;
            pauseButton.setText(isPaused ? "Reprendre" : "Pause");
            
            // Changer la couleur du bouton aussi pour un feedback visuel
            if (isPaused) {
                pauseButton.setBackground(new Color(80, 170, 80)); // Vert pour reprendre
            } else {
                pauseButton.setBackground(BUTTON_COLOR); // Rouge pour pause
            }
        }
    }
    
    private class StatsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StatisticsFrame statsFrame = new StatisticsFrame(manager, manager.getStatisticsCollector());
            statsFrame.setVisible(true);
        }
    }
    
    
    private class RapideButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	simulationSpeed = 200;
        }
    }
    
    private class MoyenButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	simulationSpeed = 2000;
        }
    }
    
    private class LentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	simulationSpeed = 3500;
        }
    }
    
    
    @Override
    public void run() {
        while (manager.getStepCount() < MAX_STEP && !manager.getBetes().isEmpty()) {
            if (!isPaused) {
                manager.startSimulation();

                for (Bete bete : manager.getBetes()) {
                    if (bete.getSuivi()) {
                        beteInfoPanel.update(bete, manager);
                        break;
                    }
                }

                gridPanel.repaint();
                updateInfoPanel();
            }

            try {
                Thread.sleep(simulationSpeed);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        if (manager.getStepCount() >= MAX_STEP || manager.getBetes().isEmpty()) {
        	String[] options = {"Afficher les statistiques", "Fermer"};
        	int choice = JOptionPane.showOptionDialog(
        	    frame,
        	    "La simulation est terminée !",
        	    "Simulation Finie",
        	    JOptionPane.YES_NO_OPTION,
        	    JOptionPane.WARNING_MESSAGE,
        	    null,
        	    options,
        	    null
        	);

        	if (choice == 0) {
        		StatisticsFrame statsFrame = new StatisticsFrame(manager, manager.getStatisticsCollector());
                statsFrame.setVisible(true);
        	}

        }
    }

    
    private class MouseControls implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            
            int cellSize = gridPanel.getCellSize();
            int col = x / cellSize;
            int row = y / cellSize;

            for (Bete bete : manager.getBetes()) {
                Case pos = bete.getPosition();
                if (pos.getLine() == row && pos.getColumn() == col) {
                    beteInfoPanel.update(bete, manager);
                    bete.setSuivi(true);
                } else {
                    bete.setSuivi(false);
                }
                gridPanel.repaint();
            }
        }
        
        

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }
}