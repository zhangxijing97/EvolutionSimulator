package neu.edu.evolutionsimulator;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import neu.edu.evolutionsimulator.model.Creature;
import neu.edu.evolutionsimulator.model.CreatureGenerator;
import neu.edu.evolutionsimulator.model.CreatureInitializer;
import neu.edu.evolutionsimulator.model.Environment;
import neu.edu.evolutionsimulator.model.Map;
import neu.edu.evolutionsimulator.view.MapView;

import neu.edu.evolutionsimulator.controller.MapController;

public class App {

    private static JFrame frame;
    private static Map map;
    private static MapView mapView; 
    private static CreatureGenerator creatureGenerator;
    private static CreatureInitializer creatureInitializer;
   
    private static Environment environment;
    private static Timer simulationTimer;
    private static boolean simulationRunning = false;

    public static void main(String[] args) {
        map = new Map(1000, 1000);
        creatureGenerator = new CreatureGenerator(map);
        creatureInitializer = new CreatureInitializer(map);
        environment = new Environment(110);
        SwingUtilities.invokeLater(App::createAndShowGUI);
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {

        // Get screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        // Set map to match screen size
        
        mapView = new MapView(map);
        mapView.setPreferredSize(new Dimension(screenWidth, screenHeight));

        
        // Initialize the creatures
        List<Creature> creatures = creatureInitializer.initializeCreatures(30);
        for (Creature creature : creatures) {
            map.addCreature(creature);
        }


        // Setup the main JFrame
        frame = new JFrame("Evolution Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenWidth, screenHeight);
        frame.setLayout(new BorderLayout());
        
        // Create control panel with a vertical BoxLayout for the buttons
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        
       
        JButton initializeButton = new JButton("Initialize Creatures");
        initializeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                creatureInitializer.reinitializeCreatures(20);
                mapView.repaint();
            }
        });
        controlPanel.add(initializeButton);

        JButton generateButton = new JButton("Generate Creatures");

        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Creature> newCreatures = creatureGenerator.generateCreatures(); // Assuming this method returns a List<Creature>
                for (Creature creature : newCreatures) {
                    map.addCreature(creature);
                }
                mapView.repaint();
            }
        });
        controlPanel.add(generateButton);

        // 创建按钮并添加到buttonPanel
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            
                startSimulation();
            }
        });
        controlPanel.add(startButton);

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopSimulation();
            }
        });

        controlPanel.add(stopButton);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetSimulation();
            }
        });

        controlPanel.add(resetButton);

        

        // Add key listener to the frame
        MapController controller = new MapController(map);
        frame.addKeyListener(controller);

       // Add control panel to the EAST side of the frame
        frame.add(controlPanel, BorderLayout.EAST);
        // Add mapView to the frame
        frame.add(mapView, BorderLayout.CENTER);


        // Setup the simulation Timer
        simulationTimer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateSimulation();
            }
        });
    

       // Show the window
        frame.setVisible(true);
    }


        private static void startSimulation() {
            simulationRunning = true;
            simulationTimer.start();
        }


        private static void stopSimulation() {
            simulationRunning = false;
            simulationTimer.stop();
        }


        private static void resetSimulation() {
            stopSimulation();
            map.clearCreatures();  // You need to implement this method in the Map class
            List<Creature> newCreatures = creatureInitializer.initializeCreatures(20);
            for (Creature creature : newCreatures) {
                map.addCreature(creature);
            }
            mapView.repaint();
        }

        private static void updateSimulation() {
            if (!simulationRunning) return;
        
            try {
                // Update the view
                mapView.repaint();
            
                // Move creatures and handle other updates
                for (Creature creature : map.getCreatures()) {
                    creature.move();
                    map.checkForFoodProximity(creature, map);
                    creature.updateSurvivalRate(environment);
                    creature.determineSurvival();
                }
            
                // Generate offspring for creatures
                List<Creature> creaturesToGenerateOffspring = new ArrayList<>(map.getCreatures());
                creatureGenerator.generateOffspring(creaturesToGenerateOffspring);
            
                // Remove dead creatures
                List<Creature> creaturesToRemove = new ArrayList<>();
                for (Creature creature : map.getCreatures()) {
                    if (creature.isDead()) {
                        creaturesToRemove.add(creature); // Mark for removal
                    }
                }
                for (Creature creature : creaturesToRemove) {
                    map.removeCreature(creature);
                }
            } catch (Exception e) {
                e.printStackTrace(); // 打印异常信息到控制台
            }
        }
        
        
    }
        


        
