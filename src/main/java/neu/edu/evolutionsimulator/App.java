package neu.edu.evolutionsimulator;

import javax.swing.JFrame;
import java.util.List;
import java.awt.*;

import neu.edu.evolutionsimulator.model.Creature;
import neu.edu.evolutionsimulator.model.CreatureInitializer;
import neu.edu.evolutionsimulator.model.FoodGenerator;
import neu.edu.evolutionsimulator.model.Map;
import neu.edu.evolutionsimulator.view.MapView;
import neu.edu.evolutionsimulator.controller.MapController;

public class App {

    public static void main(String[] args) {
        // Get screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        // Set map to match screen size
        Map map = new Map(screenWidth, screenHeight);
        MapView mapView = new MapView(map);
        MapController controller = new MapController(map);
        FoodGenerator foodGenerator = new FoodGenerator(map);
        CreatureInitializer creatureInitializer = new CreatureInitializer(map);

        // Initialize the creatures
        List<Creature> creatures = creatureInitializer.initializeCreatures(30);
        for (Creature creature : creatures) {
            map.addCreature(creature);
        }

        JFrame frame = new JFrame("Evolution Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mapView);
        frame.addKeyListener(controller);

        // Set frame size to match screen size
        frame.setSize(screenWidth, screenHeight);
        frame.setVisible(true);

        // foodGenerator.generateFood(100);

        // Game loop
        while (true) {
            // Update the view
            mapView.repaint();

            // Generate Food
            foodGenerator.generateFood(1);

            // Move creatures
            for (Creature creature : creatures) {
                creature.move(map.getFoods());
                map.checkForFoodProximity(creature, map);
            }

            // Optional: Add a delay to control the speed of movement
            try {
                Thread.sleep(10); // Adjust the delay time as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}