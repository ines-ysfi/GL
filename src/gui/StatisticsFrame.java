package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import process.SimulationManager;
import process.StatisticsCollector;



public class StatisticsFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private SimulationManager manager;
    private StatisticsCollector statsCollector;
    
    // Définition des couleurs thématiques
    private static final Color BACKGROUND_COLOR = new Color(240, 240, 245);
    private static final Color MALE_COLOR = new Color(70, 130, 180); // Bleu pour mâles
    private static final Color FEMALE_COLOR = new Color(220, 20, 60); // Rouge pour femelles
    private static final Color POPULATION_COLOR = new Color(46, 139, 87); // Vert pour population totale
    private static final Color AGE_COLOR = new Color(205, 133, 63); // Bronze pour l'âge
    private static final Color FORCE_COLOR = new Color(75, 0, 130); // Indigo pour la force
    private static final Color BIRTH_COLOR = new Color(50, 205, 50); // Vert clair pour naissances
    private static final Color DEATH_COLOR = new Color(178, 34, 34); // Rouge foncé pour morts
    private static final Color COMBAT_COLOR = new Color(255, 140, 0); // Orange pour combats
    private static final Color FOOD_COLOR = new Color(139, 69, 19); // Marron pour nourriture
    
    public StatisticsFrame(SimulationManager manager, StatisticsCollector statsCollector) {
        super("Statistiques de la simulation");
        this.manager = manager;
        this.statsCollector = statsCollector;
        
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
     // Appliquer un look and feel moderne
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Créer un panneau à onglets avec un style amélioré
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));
        tabbedPane.setBackground(BACKGROUND_COLOR);
        
        // Ajouter les onglets avec différents graphiques
        tabbedPane.addTab("Population", createPopulationPanel());
        tabbedPane.addTab("Répartition M/F", createGenderDistributionPanel());
        tabbedPane.addTab("Âge", createAgePanel());
        tabbedPane.addTab("Force", createForcePanel());
        tabbedPane.addTab("Événements", createEventsPanel());
        
        add(tabbedPane);
        
        // Centrer la fenêtre
        setLocationRelativeTo(null);
    }
    
    /**
     * Applique un style cohérent à un graphique
     */
    private void applyChartStyle(JFreeChart chart) {
        // Modifier l'apparence générale du graphique
        chart.setBackgroundPaint(BACKGROUND_COLOR);
        
        // Modifier le titre
        chart.getTitle().setFont(new Font("Arial", Font.BOLD, 16));
        
        // Modifier la légende
        chart.getLegend().setBackgroundPaint(BACKGROUND_COLOR);
        chart.getLegend().setItemFont(new Font("Arial", Font.PLAIN, 12));
        
        // Modifier l'apparence du plot
        if (chart.getPlot() instanceof XYPlot) {
            XYPlot plot = (XYPlot) chart.getPlot();
            plot.setBackgroundPaint(Color.WHITE);
            plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
            plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
            
            // Améliorer le rendu des lignes
            if (plot.getRenderer() instanceof XYLineAndShapeRenderer) {
                XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
                renderer.setBaseShapesVisible(true);
                renderer.setAutoPopulateSeriesStroke(false);
                renderer.setBaseStroke(new BasicStroke(2.0f));
            }
        } else if (chart.getPlot() instanceof CategoryPlot) {
            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            plot.setBackgroundPaint(Color.WHITE);
            plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
            plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        }
    }
    
    /**
     * Crée un panneau avec un graphique d'évolution de la population totale
     */
    private JPanel createPopulationPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        
        // Créer le dataset pour le graphique
        XYSeriesCollection dataset = new XYSeriesCollection();
        
        XYSeries populationSeries = new XYSeries("Population totale");
        List<Integer> populationHistory = statsCollector.getPopulationHistory();
        
        for (int i = 0; i < populationHistory.size(); i++) {
            populationSeries.add(i, populationHistory.get(i));
        }
        
        dataset.addSeries(populationSeries);
        
        // Créer le graphique
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Évolution de la population",
                "Étape",
                "Nombre de bêtes",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        
        // Appliquer le style
        applyChartStyle(chart);
        
        // Modifier la couleur de la ligne
        XYPlot plot = (XYPlot) chart.getPlot();
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, POPULATION_COLOR);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(chartPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Crée un panneau avec un graphique d'évolution de la répartition mâle/femelle
     */
    private JPanel createGenderDistributionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        
        // Créer le dataset pour le graphique
        XYSeriesCollection dataset = new XYSeriesCollection();
        
        XYSeries maleSeries = new XYSeries("Mâles");
        XYSeries femaleSeries = new XYSeries("Femelles");
        
        List<Integer> maleHistory = statsCollector.getMalePopulationHistory();
        List<Integer> femaleHistory = statsCollector.getFemalePopulationHistory();
        
        for (int i = 0; i < maleHistory.size(); i++) {
            maleSeries.add(i, maleHistory.get(i));
            femaleSeries.add(i, femaleHistory.get(i));
        }
        
        dataset.addSeries(maleSeries);
        dataset.addSeries(femaleSeries);
        
        // Créer le graphique
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Évolution de la répartition mâle/femelle",
                "Étape",
                "Nombre",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        
        // Appliquer le style
        applyChartStyle(chart);
        
        // Modifier les couleurs des lignes
        XYPlot plot = (XYPlot) chart.getPlot();
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, MALE_COLOR);
        renderer.setSeriesPaint(1, FEMALE_COLOR);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(chartPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Crée un panneau avec un graphique d'évolution de l'âge moyen
     * et un histogramme de la répartition des âges
     */
    private JPanel createAgePanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBackground(BACKGROUND_COLOR);
        
        // Graphique d'âge moyen
        XYSeriesCollection ageDataset = new XYSeriesCollection();
        XYSeries ageSeries = new XYSeries("Âge moyen");
        
        List<Float> ageHistory = statsCollector.getAverageAgeHistory();
        for (int i = 0; i < ageHistory.size(); i++) {
            ageSeries.add(i, ageHistory.get(i));
        }
        
        ageDataset.addSeries(ageSeries);
        
        JFreeChart ageChart = ChartFactory.createXYLineChart(
                "Évolution de l'âge moyen",
                "Étape",
                "Âge moyen",
                ageDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        
        // Appliquer le style
        applyChartStyle(ageChart);
        
        // Modifier la couleur de la ligne d'âge
        XYPlot agePlot = (XYPlot) ageChart.getPlot();
        XYLineAndShapeRenderer ageRenderer = (XYLineAndShapeRenderer) agePlot.getRenderer();
        ageRenderer.setSeriesPaint(0, AGE_COLOR);
        
        ChartPanel ageChartPanel = new ChartPanel(ageChart);
        ageChartPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panel.add(ageChartPanel);
        
        // Histogramme de répartition des âges
        DefaultCategoryDataset ageDistDataset = new DefaultCategoryDataset();
        
        Map<Integer, Integer> ageDistribution = statsCollector.getAgeDistribution(manager.getBetes());
        for (Map.Entry<Integer, Integer> entry : ageDistribution.entrySet()) {
            ageDistDataset.addValue(entry.getValue(), "Nombre de bêtes", entry.getKey().toString());
        }
        
        JFreeChart ageDistChart = ChartFactory.createBarChart(
                "Répartition des âges",
                "Âge",
                "Nombre de bêtes",
                ageDistDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        
        // Appliquer le style
        applyChartStyle(ageDistChart);
        
        // Modifier la couleur des barres de l'histogramme
        CategoryPlot ageDistPlot = (CategoryPlot) ageDistChart.getPlot();
        BarRenderer ageDistRenderer = (BarRenderer) ageDistPlot.getRenderer();
        ageDistRenderer.setSeriesPaint(0, new Color(210, 180, 140)); // Couleur beige
        
        ChartPanel ageDistChartPanel = new ChartPanel(ageDistChart);
        ageDistChartPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panel.add(ageDistChartPanel);
        
        return panel;
    }
    
    /**
     * Crée un panneau avec un graphique d'évolution de la force moyenne
     * et un histogramme de la répartition des forces
     */
    private JPanel createForcePanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBackground(BACKGROUND_COLOR);
        
        // Graphique de force moyenne
        XYSeriesCollection forceDataset = new XYSeriesCollection();
        XYSeries forceSeries = new XYSeries("Force moyenne");
        
        List<Float> forceHistory = statsCollector.getAverageForceHistory();
        for (int i = 0; i < forceHistory.size(); i++) {
            forceSeries.add(i, forceHistory.get(i));
        }
        
        forceDataset.addSeries(forceSeries);
        
        JFreeChart forceChart = ChartFactory.createXYLineChart(
                "Évolution de la force moyenne",
                "Étape",
                "Force moyenne",
                forceDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        
        // Appliquer le style
        applyChartStyle(forceChart);
        
        // Modifier la couleur de la ligne de force
        XYPlot forcePlot = (XYPlot) forceChart.getPlot();
        XYLineAndShapeRenderer forceRenderer = (XYLineAndShapeRenderer) forcePlot.getRenderer();
        forceRenderer.setSeriesPaint(0, FORCE_COLOR);
        
        ChartPanel forceChartPanel = new ChartPanel(forceChart);
        forceChartPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panel.add(forceChartPanel);
        
        // Histogramme de répartition des forces
        DefaultCategoryDataset forceDistDataset = new DefaultCategoryDataset();
        
        Map<Integer, Integer> forceDistribution = statsCollector.getForceDistribution(manager.getBetes());
        for (Map.Entry<Integer, Integer> entry : forceDistribution.entrySet()) {
            forceDistDataset.addValue(entry.getValue(), "Nombre de bêtes", entry.getKey().toString());
        }
        
        JFreeChart forceDistChart = ChartFactory.createBarChart(
                "Répartition des forces",
                "Force",
                "Nombre de bêtes",
                forceDistDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        
        // Appliquer le style
        applyChartStyle(forceDistChart);
        
        // Modifier la couleur des barres de l'histogramme
        CategoryPlot forceDistPlot = (CategoryPlot) forceDistChart.getPlot();
        BarRenderer forceDistRenderer = (BarRenderer) forceDistPlot.getRenderer();
        forceDistRenderer.setSeriesPaint(0, new Color(148, 0, 211)); // Violet pour l'histogramme de force
        
        ChartPanel forceDistChartPanel = new ChartPanel(forceDistChart);
        forceDistChartPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panel.add(forceDistChartPanel);
        
        return panel;
    }
    
    /**
     * Crée un panneau avec des graphiques d'événements (naissances, morts, combats)
     */
    private JPanel createEventsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBackground(BACKGROUND_COLOR);
        
        // Graphique des événements
        DefaultCategoryDataset eventDataset = new DefaultCategoryDataset();
        
        List<Integer> birthHistory = statsCollector.getBirthHistory();
        List<Integer> deathHistory = statsCollector.getDeathHistory();
        List<Integer> combatHistory = statsCollector.getCombatHistory();
        
        int dataSize = Math.min(birthHistory.size(), Math.min(deathHistory.size(), combatHistory.size()));
        
        for (int i = 0; i < dataSize; i++) {
            eventDataset.addValue(birthHistory.get(i), "Naissances", "Étape " + i);
            eventDataset.addValue(deathHistory.get(i), "Morts", "Étape " + i);
            eventDataset.addValue(combatHistory.get(i), "Combats", "Étape " + i);
        }
        
        JFreeChart eventChart = ChartFactory.createBarChart(
                "Événements par étape",
                "Étape",
                "Nombre",
                eventDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        
        // Appliquer le style
        applyChartStyle(eventChart);
        
        // Modifier les couleurs des barres
        CategoryPlot eventPlot = (CategoryPlot) eventChart.getPlot();
        BarRenderer eventRenderer = (BarRenderer) eventPlot.getRenderer();
        eventRenderer.setSeriesPaint(0, BIRTH_COLOR);
        eventRenderer.setSeriesPaint(1, DEATH_COLOR);
        eventRenderer.setSeriesPaint(2, COMBAT_COLOR);
        
        ChartPanel eventChartPanel = new ChartPanel(eventChart);
        eventChartPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panel.add(eventChartPanel);
        
        // Graphique de nourriture consommée
        XYSeriesCollection foodDataset = new XYSeriesCollection();
        XYSeries foodSeries = new XYSeries("Nourriture consommée");
        
        List<Integer> foodHistory = statsCollector.getFoodConsumedHistory();
        for (int i = 0; i < foodHistory.size(); i++) {
            foodSeries.add(i, foodHistory.get(i));
        }
        
        foodDataset.addSeries(foodSeries);
        
        JFreeChart foodChart = ChartFactory.createXYLineChart(
                "Nourriture consommée par étape",
                "Étape",
                "Nombre",
                foodDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        
        // Appliquer le style
        applyChartStyle(foodChart);
        
        // Modifier la couleur de la ligne de nourriture
        XYPlot foodPlot = (XYPlot) foodChart.getPlot();
        XYLineAndShapeRenderer foodRenderer = (XYLineAndShapeRenderer) foodPlot.getRenderer();
        foodRenderer.setSeriesPaint(0, FOOD_COLOR);
        
        ChartPanel foodChartPanel = new ChartPanel(foodChart);
        foodChartPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panel.add(foodChartPanel);
        
        return panel;
    }
}