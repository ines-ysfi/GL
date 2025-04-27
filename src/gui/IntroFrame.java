package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class IntroFrame {
    private JFrame frame;
    private JTextField gridSizeField;
    private JTextField betesCountField;
    private JTextField obstaclesCountField;
    private JTextField nourritureCountField;


    private final Color BG_COLOR = new Color(40, 40, 40);     
    private final Color ACCENT_COLOR = new Color(220, 60, 60);
    private final Color TEXT_COLOR = new Color(240, 240, 240);  
    private final Color SECONDARY_COLOR = new Color(80, 80, 80); 


    public IntroFrame() {
        frame = new JFrame("Simulation Bêtes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        
        setupMainPanel();
        frame.setVisible(true);
    }

    private void setupMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panneau de gauche pour le texte d'introduction
        JPanel introPanel = createIntroPanel();
        
        // Panneau de droite pour les contrôles
        JPanel configPanel = createConfigPanel();
        
        mainPanel.add(introPanel, BorderLayout.WEST);
        mainPanel.add(configPanel, BorderLayout.CENTER);
        
        frame.setContentPane(mainPanel);
    }
    
    private JPanel createIntroPanel() {
        JPanel introPanel = new JPanel(new BorderLayout(0, 20));
        introPanel.setBackground(BG_COLOR);
        introPanel.setPreferredSize(new Dimension(580, 500));
        introPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel titleLabel = new JLabel("SIMULATION DE BÊTES");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(ACCENT_COLOR);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        
        // Texte d'introduction
        JTextArea introText = new JTextArea();
        introText.setText("Bienvenue dans la simulation de bêtes !\n\n" +
                "Ce programme vous permet d'observer le comportement de créatures virtuelles " +
                "évoluant dans un environnement en grille.\n\n" +
                "Vous pouvez contrôler:\n" +
                "• La taille de la grille (5-20)\n" +
                "• Le nombre de bêtes\n" +
                "• Le nombre d'obstacles\n\n" +
                "Définissez vos paramètres et appuyez sur 'Commencer' pour lancer la simulation.");
        introText.setEditable(false);
        introText.setBackground(BG_COLOR);
        introText.setForeground(TEXT_COLOR);
        introText.setFont(new Font("Arial", Font.PLAIN, 14));
        introText.setLineWrap(true);
        introText.setWrapStyleWord(true);
        

        JPanel logoPanel = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon logo = new ImageIcon(getClass().getResource("/img/tr.png"));
                Image img = logo.getImage();
                // Calculer les coordonnées pour centrer l'image
                int x = (getWidth() - img.getWidth(null)) / 2;  // Centrer horizontalement
                int y = (getHeight() - img.getHeight(null)) / 2; // Centrer verticalement
                
                g.drawImage(img, x, y, this);  // Dessiner l'image centrée
            }
        };
        logoPanel.setPreferredSize(new Dimension(200, 200));
        logoPanel.setBackground(BG_COLOR);
        
        // Ajout des composants au panneau d'introduction
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(BG_COLOR);
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(logoPanel, BorderLayout.CENTER);
        
        introPanel.add(titlePanel, BorderLayout.NORTH);
        introPanel.add(introText, BorderLayout.CENTER);
        
        return introPanel;
    }
    
    

    
    private JPanel createConfigPanel() {
        JPanel configPanel = new JPanel();
        configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));
        configPanel.setBackground(BG_COLOR);
        configPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        gridSizeField = createStyledTextField();
        betesCountField = createStyledTextField();
        obstaclesCountField = createStyledTextField();
        nourritureCountField = createStyledTextField();
        
        // Par défaut
        gridSizeField.setText("10");
        betesCountField.setText("20");
        obstaclesCountField.setText("10");
        nourritureCountField.setText("8");
        
        // Créer des sous-panneaux pour chaque ligne
        JPanel gridSizePanel = createInputPanel("Taille de la grille:", gridSizeField);
        JPanel betesPanel = createInputPanel("Nombre de bêtes:", betesCountField);
        JPanel obstaclesPanel = createInputPanel("Nombre d'obstacles:", obstaclesCountField);
        JPanel nourriturePanel = createInputPanel("Nombre de nourriture:", nourritureCountField);
        
        // Bouton de démarrage
        JButton startButton = new JButton("Commencer");
        startButton.setBackground(ACCENT_COLOR);
        startButton.setForeground(TEXT_COLOR);
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.addActionListener(new StartButtonListener());        
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(BG_COLOR);
        buttonPanel.add(startButton);
        configPanel.add(gridSizePanel);

        configPanel.add(betesPanel);

        configPanel.add(obstaclesPanel);
        
        configPanel.add(nourriturePanel);

        configPanel.add(buttonPanel);
        
        return configPanel;
    }
    
    private JTextField createStyledTextField() {
        JTextField field = new JTextField(10);
        field.setBackground(SECONDARY_COLOR);
        field.setForeground(TEXT_COLOR);
        field.setCaretColor(TEXT_COLOR);
        field.setFont(new Font("Arial", Font.PLAIN, 14));

        return field;
    }
    
    private JPanel createInputPanel(String labelText, JTextField field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(BG_COLOR);
        
        JLabel label = new JLabel(labelText);
        label.setForeground(TEXT_COLOR);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        
        panel.add(label);
        panel.add(field);
        
        return panel;
    }
    
    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int gridSize = Integer.parseInt(gridSizeField.getText());
                int nbBetes = Integer.parseInt(betesCountField.getText());
                int nbObstacles = Integer.parseInt(obstaclesCountField.getText());
                int nbNourriture = Integer.parseInt(nourritureCountField.getText());
                
                if (gridSize < 5 || gridSize > 20) {
                    JOptionPane.showMessageDialog(frame,
                            "La taille de la grille doit être entre 5 et 20.",
                            "Valeur incorrecte",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                else if(nbBetes >= (gridSize*gridSize)-(gridSize/2)) {
                	JOptionPane.showMessageDialog(frame,
                            "le nombre de betes depasse la limite",
                            "Valeur incorrecte",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                

                frame.dispose();
                System.out.println("grid"+gridSize);
                SimulationGUI sim = new SimulationGUI(gridSize, nbBetes, nbObstacles, nbNourriture);
                Thread t = new Thread(sim);
                t.start();

                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, 
                        "Veuillez entrer des valeurs numériques valides.", 
                        "Erreur", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}