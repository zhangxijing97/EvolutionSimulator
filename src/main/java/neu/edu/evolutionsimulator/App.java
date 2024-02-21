package neu.edu.evolutionsimulator;

import javax.swing.JFrame;
import java.util.List;

import neu.edu.evolutionsimulator.model.Creature;
import neu.edu.evolutionsimulator.model.CreatureInitializer;
import neu.edu.evolutionsimulator.model.FoodGenerator;
import neu.edu.evolutionsimulator.model.Map;
import neu.edu.evolutionsimulator.view.MapView;
import neu.edu.evolutionsimulator.controller.MapController;

public class App {

    public static void main(String[] args) {
        Map map = new Map(500, 500);
        MapView mapView = new MapView(map);
        MapController controller = new MapController(map);
        FoodGenerator foodGenerator = new FoodGenerator(map);

        // Initialize the creatures
        List<Creature> creatures = CreatureInitializer.initializeCreatures();
        for (Creature creature : creatures) {
            map.addCreature(creature);
        }

        JFrame frame = new JFrame("Evolution Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mapView);
        frame.addKeyListener(controller);
        frame.setSize(500, 500);
        frame.setVisible(true);

        // foodGenerator.generateFood(100);

        // Game loop
        while (true) {
            // Move creatures
            for (Creature creature : creatures) {
                creature.move();
                map.checkForFoodProximity(creature, map);
            }

            // Update the view with the new positions of creatures
            mapView.repaint();
            foodGenerator.generateFood(1);

            // Optional: Add a delay to control the speed of movement
            try {
                Thread.sleep(100); // Adjust the delay time as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}