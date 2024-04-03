package neu.edu.evolutionsimulator;

import javax.swing.JFrame;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

import neu.edu.evolutionsimulator.model.Creature;
import neu.edu.evolutionsimulator.model.CreatureGenerator;
import neu.edu.evolutionsimulator.model.CreatureInitializer;
import neu.edu.evolutionsimulator.model.Environment;
import neu.edu.evolutionsimulator.model.Map;
import neu.edu.evolutionsimulator.view.MapView;
import neu.edu.evolutionsimulator.view.StartView;
import neu.edu.evolutionsimulator.controller.MapController;

public class App {

    public static void main(String[] args) {
        // Get screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        // Set map to match screen size
        Map map = new Map(screenWidth, screenHeight);

        // Create and add mapView to the frame
        MapView mapView = new MapView(map);
        MapController controller = new MapController(map);
        CreatureInitializer creatureInitializer = new CreatureInitializer(map);
        CreatureGenerator creatureGenerator = new CreatureGenerator(map);
        Environment environment = new Environment(110);

        // Initialize the creatures
        List<Creature> creatures = creatureInitializer.initializeCreatures(30);
        for (Creature creature : creatures) {
            map.addCreature(creature);
        }

        // Testing
        StartView startView = new StartView();

        JFrame frame = new JFrame("Evolution Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mapView);
        // frame.add(startView);
        frame.addKeyListener(controller);

        // Set frame size to match screen size
        frame.setSize(screenWidth, screenHeight);
        frame.setVisible(true);

        // Game loop
        while (true) {
            // Update the view
            mapView.repaint();

            // Move creatures
            for (Creature creature : map.getCreatures()) {
                creature.move();
                map.checkForFoodProximity(creature, map);
                // creature.updateEnergyBasedOnFur(environment);
                creature.updateSurvivalRate(environment);
                creature.determineSurvival();

                // Check if the creature is dead and remove it from the map
            }

            // Create a list to store creatures generateOffspring
            List<Creature> creaturesToGenerateOffspring = new ArrayList<>();
            for (Creature creature : map.getCreatures()) {
                creaturesToGenerateOffspring.add(creature);
            }

            // Generate Offspring
            creatureGenerator.generateOffspring(creaturesToGenerateOffspring);

            // Create a list to store creatures to be removed
            List<Creature> creaturesToRemove = new ArrayList<>();

            // Iterate over the creatures and mark the dead ones for removal
            for (Creature creature : map.getCreatures()) {
                if (creature.isDead()) {
                    creaturesToRemove.add(creature); // Mark for removal
                }
            }

            // Remove the marked creatures from the map
            for (Creature creature : creaturesToRemove) {
                map.removeCreature(creature);
            }

            // Optional: Add a delay to control the speed of movement
            try {
                Thread.sleep(100); // Adjust the delay time as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}