package gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.image.BufferedImage;
import elements.Bete;
import process.SimulationManager;

public class BeteInfoPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    

    private JLabel titleLabel;
    private JPanel contentPanel;
    
    
    private JLabel primaryIconLabel;
    private JLabel primaryStatusLabel;
    private JPanel primaryInfoContainer;
    

    private JLabel secondaryIconLabel;
    private JLabel secondaryStatusLabel;
    private JPanel secondaryInfoContainer;
    

    private JPanel dualBetePanel;
    private JPanel combatDetails;
    

    private final Color BG_COLOR = new Color(40, 40, 40);           // Noir foncé
    private final Color ACCENT_COLOR = new Color(220, 60, 60);      // Rouge
    private final Color TEXT_COLOR = new Color(240, 240, 240);      // Blanc cassé
    private final Color SECONDARY_COLOR = new Color(80, 80, 80);    // Gris foncé
    private final Color COMBAT_COLOR = new Color(255, 140, 0);      // Orange foncé
    private final Color REPRODUCTION_COLOR = new Color(255, 105, 180); // Rose vif
    private final Color MALE_COLOR = new Color(100, 149, 237);       // Bleu cornflower
    private final Color FEMALE_COLOR = new Color(220, 20, 60);      // Rouge cramoisi
    

    private ImageIcon maleIcon;
    private ImageIcon femaleIcon;
    private ImageIcon reproductionIcon;
    private ImageIcon combatIconMale;
    private ImageIcon combatIconFemale;

    public BeteInfoPanel() {

        setLayout(new BorderLayout());
        setBorder(createStyledBorder("DÉTAILS DE LA BÊTE"));
        setBackground(SECONDARY_COLOR);
        

        loadIcons();
        

        setupComponents();
        

        showEmptyState();
    }
    
    
    
    private void loadIcons() {
        try {

            maleIcon = new ImageIcon(getClass().getResource("/img/redimMale.png"));
            femaleIcon = new ImageIcon(getClass().getResource("/img/redimFemale.png"));
            reproductionIcon = new ImageIcon(getClass().getResource("/img/redimReproduction.png"));
            combatIconFemale = new ImageIcon(getClass().getResource("/img/redimFemaleCombat.png"));
            combatIconMale = new ImageIcon(getClass().getResource("/img/redimMaleCombat.png"));
            
            
            
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des icônes: " + e.getMessage());

            maleIcon = createDefaultIcon(Color.BLUE);
            femaleIcon = createDefaultIcon(Color.RED);
            reproductionIcon = createDefaultIcon(Color.PINK);
            combatIconMale = createDefaultIcon(Color.ORANGE);
            combatIconFemale = createDefaultIcon(Color.ORANGE);
        }
    }
    
    
    
    private ImageIcon createDefaultIcon(Color color) {
        BufferedImage img = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        g.setColor(color);
        g.fillOval(8, 8, 48, 48);
        g.dispose();
        return new ImageIcon(img);
    }
    
    
    
    
    private void setupComponents() {

        titleLabel = new JLabel("DÉTAILS DE LA BÊTE");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(SECONDARY_COLOR);
        

        dualBetePanel = new JPanel(new GridLayout(1, 2, 10, 0));
        dualBetePanel.setBackground(SECONDARY_COLOR);
        

        JPanel primaryBetePanel = new JPanel();
        primaryBetePanel.setLayout(new BoxLayout(primaryBetePanel, BoxLayout.Y_AXIS));
        primaryBetePanel.setBackground(SECONDARY_COLOR);
        

        primaryIconLabel = new JLabel();
        primaryIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // État de la bête principale
        primaryStatusLabel = new JLabel();
        primaryStatusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        primaryStatusLabel.setForeground(ACCENT_COLOR);
        primaryStatusLabel.setHorizontalAlignment(JLabel.CENTER);
        primaryStatusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        primaryInfoContainer = new JPanel();
        primaryInfoContainer.setLayout(new BoxLayout(primaryInfoContainer, BoxLayout.Y_AXIS));
        primaryInfoContainer.setBackground(SECONDARY_COLOR);
        primaryInfoContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        primaryBetePanel.add(primaryIconLabel);
        primaryBetePanel.add(Box.createVerticalStrut(10));
        primaryBetePanel.add(primaryStatusLabel);
        primaryBetePanel.add(Box.createVerticalStrut(20));
        primaryBetePanel.add(primaryInfoContainer);
        

        
        
        JPanel secondaryBetePanel = new JPanel();
        secondaryBetePanel.setLayout(new BoxLayout(secondaryBetePanel, BoxLayout.Y_AXIS));
        secondaryBetePanel.setBackground(SECONDARY_COLOR);
        

        secondaryIconLabel = new JLabel();
        secondaryIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        secondaryStatusLabel = new JLabel();
        secondaryStatusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        secondaryStatusLabel.setForeground(ACCENT_COLOR);
        secondaryStatusLabel.setHorizontalAlignment(JLabel.CENTER);
        secondaryStatusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        secondaryInfoContainer = new JPanel();
        secondaryInfoContainer.setLayout(new BoxLayout(secondaryInfoContainer, BoxLayout.Y_AXIS));
        secondaryInfoContainer.setBackground(SECONDARY_COLOR);
        secondaryInfoContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        secondaryBetePanel.add(secondaryIconLabel);
        secondaryBetePanel.add(Box.createVerticalStrut(10));
        secondaryBetePanel.add(secondaryStatusLabel);
        secondaryBetePanel.add(Box.createVerticalStrut(20));
        secondaryBetePanel.add(secondaryInfoContainer);
        

        dualBetePanel.add(primaryBetePanel);
        dualBetePanel.add(secondaryBetePanel);
      
        

        contentPanel.add(dualBetePanel);
        combatDetails = new JPanel();
        combatDetails.setBackground(BG_COLOR);
        combatDetails.setLayout(new BoxLayout(combatDetails, BoxLayout.Y_AXIS));
        combatDetails.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 2));
        combatDetails.setAlignmentX(Component.CENTER_ALIGNMENT);
        combatDetails.setVisible(false); 
        contentPanel.add(Box.createVerticalStrut(15)); 
        contentPanel.add(combatDetails);
        

        add(titleLabel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    
    private Border createStyledBorder(String title) {
        return new LineBorder(ACCENT_COLOR, 2); 
    }

    
    
    private void showEmptyState() {
    	
        titleLabel.setText("DÉTAILS DE LA BÊTE");
        primaryIconLabel.setIcon(null);
        primaryStatusLabel.setText("Aucune bête sélectionnée");
        primaryInfoContainer.removeAll();
        

        dualBetePanel.setLayout(new BorderLayout());
        dualBetePanel.removeAll();
        

        JPanel emptyStatePanel = new JPanel();
        emptyStatePanel.setLayout(new BoxLayout(emptyStatePanel, BoxLayout.Y_AXIS));
        emptyStatePanel.setBackground(SECONDARY_COLOR);
        

        primaryStatusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emptyStatePanel.add(primaryStatusLabel);
        emptyStatePanel.add(Box.createVerticalStrut(20));
        

        JLabel infoLabel = new JLabel("Cliquez sur une bête pour voir ses détails");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        infoLabel.setForeground(TEXT_COLOR);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emptyStatePanel.add(infoLabel);
        
        
        dualBetePanel.add(emptyStatePanel, BorderLayout.CENTER);
        

        secondaryIconLabel.setIcon(null);
        secondaryStatusLabel.setText("");
        secondaryInfoContainer.removeAll();
        secondaryInfoContainer.setVisible(false);
        
        combatDetails.removeAll();
        combatDetails.setVisible(false);
        
        revalidate();
        repaint();
    }
    
    
    

    public void update(Bete selectedBete, SimulationManager manager) {
        if (selectedBete == null) {
            showEmptyState();
            return;
        }
        

        primaryInfoContainer.removeAll();
        secondaryInfoContainer.removeAll();
        
        Bete secondBete = null;
        boolean isInteracting = false;
        

        if (selectedBete.getCombat() || selectedBete.getReproduction()) {
            for (Bete b : manager.getBetes()) {
                if (!b.equals(selectedBete) && b.getPosition().equals(selectedBete.getPosition())) {
                    secondBete = b;
                    isInteracting = true;
                    break;
                }
            }
        }
        

        if (isInteracting && secondBete != null) {

            if (selectedBete.getCombat()) {
                titleLabel.setText("COMBAT EN COURS");
            } else if (selectedBete.getReproduction()) {
                titleLabel.setText("REPRODUCTION EN COURS");
            }
            

            dualBetePanel.setLayout(new GridLayout(1, 2, 10, 0));
            dualBetePanel.removeAll();
            

            JPanel primaryBetePanel = createBetePanel();
            JPanel secondaryBetePanel = createBetePanel();
            

            primaryBetePanel.add(primaryIconLabel);
            primaryBetePanel.add(Box.createVerticalStrut(10));
            primaryBetePanel.add(primaryStatusLabel);
            primaryBetePanel.add(Box.createVerticalStrut(20));
            primaryBetePanel.add(primaryInfoContainer);
            

            secondaryBetePanel.add(secondaryIconLabel);
            secondaryBetePanel.add(Box.createVerticalStrut(10));
            secondaryBetePanel.add(secondaryStatusLabel);
            secondaryBetePanel.add(Box.createVerticalStrut(20));
            secondaryBetePanel.add(secondaryInfoContainer);
            

            dualBetePanel.add(primaryBetePanel);
            dualBetePanel.add(secondaryBetePanel);
            

            updateBeteDisplay(selectedBete, primaryIconLabel, primaryStatusLabel, primaryInfoContainer);
            

            secondaryInfoContainer.setVisible(true);
            updateBeteDisplay(secondBete, secondaryIconLabel, secondaryStatusLabel, secondaryInfoContainer);
            
            
            
            
            Bete attaque;
            Bete deff;
            
            if(selectedBete.getAttaque()) {
             attaque=selectedBete;
             deff=secondBete;
            }
            else {
             attaque=secondBete;
             deff=selectedBete;           
            }
            
            if(attaque.getCombat()) {
            	showCombatDetails(attaque, deff);
            }
            else if(selectedBete.getReproduction()) {
                // Déterminez quelle bête est mâle et quelle bête est femelle
                Bete male, female;
                if(selectedBete.getSexe().equals("Male")) {
                    male = selectedBete;
                    female = secondBete;
                } else {
                    male = secondBete;
                    female = selectedBete;
                }
                showBirthDetails(male, female);
            }
            else {
                combatDetails.removeAll();
            }

            
        }
         else {

            titleLabel.setText("DÉTAILS DE LA BÊTE");
            

            dualBetePanel.setLayout(new BorderLayout());
            dualBetePanel.removeAll();
            

            JPanel singleBetePanel = createBetePanel();
            

            primaryIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            primaryStatusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            primaryInfoContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            singleBetePanel.add(primaryIconLabel);
            singleBetePanel.add(Box.createVerticalStrut(10));
            singleBetePanel.add(primaryStatusLabel);
            singleBetePanel.add(Box.createVerticalStrut(20));
            singleBetePanel.add(primaryInfoContainer);
            

            dualBetePanel.add(singleBetePanel, BorderLayout.CENTER);
            
            updateBeteDisplay(selectedBete, primaryIconLabel, primaryStatusLabel, primaryInfoContainer);
            

            secondaryIconLabel.setIcon(null);
            secondaryStatusLabel.setText("");
            secondaryInfoContainer.removeAll();
            secondaryInfoContainer.setVisible(false);
            combatDetails.removeAll();
            
        }
        
        revalidate();
        repaint();
    }
    


    private JPanel createBetePanel() {
        JPanel betePanel = new JPanel();
        betePanel.setLayout(new BoxLayout(betePanel, BoxLayout.Y_AXIS));
        betePanel.setBackground(SECONDARY_COLOR);
        return betePanel;
    }
    

    
    
    private void updateBeteDisplay(Bete bete, JLabel iconLabel, JLabel statusLabel, JPanel infoContainer) {

    	if (bete.getReproduction()) {
    		
            iconLabel.setIcon(reproductionIcon);
            statusLabel.setText("En reproduction");
            statusLabel.setForeground(REPRODUCTION_COLOR);
            
        } else if (bete.getCombat()) {
        	
            if (bete.getSexe().equals("Female")) {
                iconLabel.setIcon(combatIconFemale);
                statusLabel.setText("En combat");
                statusLabel.setForeground(COMBAT_COLOR);
                
            } else if (bete.getSexe().equals("Male")) {
            	
                iconLabel.setIcon(combatIconMale);
                statusLabel.setText("En combat");
                statusLabel.setForeground(COMBAT_COLOR);
            }
            
        } else {

        	if (bete.getSexe().equals("Male")) {
                iconLabel.setIcon(maleIcon);
                statusLabel.setText("Mâle");
                statusLabel.setForeground(MALE_COLOR); 
            } else {
                iconLabel.setIcon(femaleIcon);
                statusLabel.setText("Femelle");
                statusLabel.setForeground(FEMALE_COLOR);
            }
        }
        

        addInfoItem(infoContainer, "ID", Integer.toString(bete.getId()));
        addInfoItem(infoContainer, "Type", bete.getClass().getSimpleName());
        addInfoItem(infoContainer, "Sexe", bete.getSexe());
        addInfoItem(infoContainer, "Âge", Integer.toString(bete.getAge()));
        addInfoItem(infoContainer, "Âge de la mort", Integer.toString(bete.getAgeMort()));
        addInfoItem(infoContainer, "Force", Float.toString(bete.getForce()));
        addInfoItem(infoContainer, "Position", "(" + bete.getPosition().getLine() + ", " + bete.getPosition().getColumn() + ")");
    }
    
    private void addInfoItem(JPanel infoContainer, String label, String value) {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBackground(BG_COLOR);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, ACCENT_COLOR.darker()),
            BorderFactory.createEmptyBorder(6, 6, 6, 6)
        ));
        infoPanel.setMaximumSize(new Dimension(180, 40));
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.BOLD, 11));
        labelComponent.setForeground(TEXT_COLOR.darker());
        
        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Arial", Font.PLAIN, 12));
        valueComponent.setForeground(TEXT_COLOR);
        valueComponent.setHorizontalAlignment(JLabel.RIGHT);
        
        infoPanel.add(labelComponent, BorderLayout.WEST);
        infoPanel.add(valueComponent, BorderLayout.EAST);
        
        infoContainer.add(infoPanel);
    }
    
    
    private void showCombatDetails(Bete attacker, Bete defender) {
        combatDetails.removeAll();
        combatDetails.setLayout(new BoxLayout(combatDetails, BoxLayout.Y_AXIS));

        
        JLabel attackerLabel = new JLabel("Bête " + attacker.getId() + " attaque la bête " + defender.getId());
        JLabel defenderLabel = new JLabel("Bête " + defender.getId() + " se défend contre la bête " + attacker.getId());
        
        attackerLabel.setForeground(Color.WHITE);
        defenderLabel.setForeground(Color.WHITE);
        
        attackerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        defenderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        combatDetails.add(attackerLabel);
        combatDetails.add(Box.createVerticalStrut(10));
        combatDetails.add(defenderLabel);
        combatDetails.setVisible(true);
        combatDetails.revalidate();
        combatDetails.repaint();
    }
    
    private void showBirthDetails(Bete male, Bete female) {
    	
        combatDetails.removeAll();
        combatDetails.setLayout(new BoxLayout(combatDetails, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Naissance en cours");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(REPRODUCTION_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel parentLabel = new JLabel("Parents: Bête " + male.getId() + " (♂) et Bête " + female.getId() + " (♀)");
        parentLabel.setForeground(Color.WHITE);
        parentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel infoLabel = new JLabel("Un bébé va naître prochainement");
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        combatDetails.add(titleLabel);
        combatDetails.add(Box.createVerticalStrut(10));
        combatDetails.add(parentLabel);
        combatDetails.add(Box.createVerticalStrut(10));
        combatDetails.add(infoLabel);
        

        combatDetails.setVisible(true);
        combatDetails.revalidate();
        combatDetails.repaint();
    }
    
}