package neu.edu.evolutionsimulator;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;

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
    private static double optimalFurLength;
    private static double temperature;
    private static Timer simulationTimer;
    private static boolean simulationRunning = false;
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static int screenWidth = (int) screenSize.getWidth();
    private static int screenHeight = (int) screenSize.getHeight();

    public static void main(String[] args) {
        map = new Map(screenWidth-400, screenHeight-200);
        creatureGenerator = new CreatureGenerator(map);
        creatureInitializer = new CreatureInitializer(map);
        temperature = 0;
        environment = new Environment(temperature);
        optimalFurLength = environment.calculateOptimalFurLength(temperature);
        SwingUtilities.invokeLater(App::createAndShowGUI);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        // Set map to match screen size
        mapView = new MapView(map);
        mapView.setPreferredSize(new Dimension(screenWidth, screenHeight));

        // Initialize the creatures
        List<Creature> creatures = creatureInitializer.initializeCreatures(20);
        for (Creature creature : creatures) {
            map.addCreature(creature);
        }

        // Setup the main JFrame
        frame = new JFrame("Evolution Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setupTemperatureSlider(frame); // Setup the temperature slider
        frame.setSize(screenWidth, screenHeight);
        frame.setLayout(new BorderLayout());

        // Create a panel for the slider with BoxLayout
        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));

        sliderPanel.add(temperatureSlider);

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
                List<Creature> newCreatures = creatureGenerator.generateCreatures(); // Assuming this method returns a
                                                                                     // List<Creature>
                for (Creature creature : newCreatures) {
                    map.addCreature(creature);
                }
                mapView.repaint();
            }
        });
        controlPanel.add(generateButton);

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

        controlPanel.add(sliderPanel, 0); // Add at index 0 to be at the top

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
        map.clearCreatures(); // You need to implement this method in the Map class
        List<Creature> newCreatures = creatureInitializer.initializeCreatures(20);
        for (Creature creature : newCreatures) {
            map.addCreature(creature);
        }
        mapView.repaint();
    }

    private static void updateSimulation() {
        if (!simulationRunning)
            return;
        int currentTemperature = temperatureSlider.getValue();

        try {
            // Update the view
            mapView.repaint();

            // Move creatures and handle other updates
            for (Creature creature : map.getCreatures()) {
                creature.move();
                map.checkForFoodProximity(creature, map);
                creature.updateSurvivalRate(environment);
                creature.adjustSurvivalRate(currentTemperature, environment);

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
            e.printStackTrace();
        }
        mapView.repaint(); // This should trigger paintComponent in MapView
    }

    // declare and initialize the temperature slider
    private static JSlider temperatureSlider;

    public static void setupTemperatureSlider(JFrame frame) {
        temperatureSlider = new JSlider(JSlider.HORIZONTAL, -10, 30, 0);
        temperatureSlider.setMajorTickSpacing(10);
        temperatureSlider.setPaintTicks(true);
        temperatureSlider.setPaintLabels(true);
        temperatureSlider.setBounds(10, 50, 300, 50); // Adjust the position and size as needed

        frame.add(temperatureSlider); // Adding the slider to the frame
        frame.getContentPane().setLayout(null); // Ensure layout manager does not interfere

        setupTemperatureSliderListener(); // Setup listener for the slider
    }

    private static void setupTemperatureSliderListener() {
        temperatureSlider.addChangeListener(e -> {
            if (!temperatureSlider.getValueIsAdjusting()) {
                int temperature = temperatureSlider.getValue();
                System.out.println("Temperature slider adjusted to: " + temperature);
                updateInterfaceColor(temperature);

                optimalFurLength = environment.calculateOptimalFurLength(temperature);
                environment.setOptimalFurLength(optimalFurLength);

                updateSimulation();

                frame.repaint();
            }
        });
    }

    private static void updateInterfaceColor(int temperature) {
        Color color;
        if (temperature < 0) {
            color = new Color(98, 164, 199); // Deep Blue
        } else if (temperature < 10) {
            color = new Color(153, 194, 223); // Light Blue
        } else if (temperature < 20) {
            color = new Color(247, 168, 168); // Light Red
        } else {
            color = new Color(194, 112, 112); // Deep Red
        }

        // Assume the background you want to change is the frame's content pane
        frame.getContentPane().setBackground(color);
    }

}
