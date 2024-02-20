package neu.edu.evolutionsimulator;

import javax.swing.JFrame;
import java.util.List;

import neu.edu.evolutionsimulator.model.Creature;
import neu.edu.evolutionsimulator.model.CreatureInitializer;
import neu.edu.evolutionsimulator.model.Map;
import neu.edu.evolutionsimulator.view.MapView;
import neu.edu.evolutionsimulator.controller.MapController;

public class App {

    public static void main(String[] args) {
        Map map = new Map(500, 500);
        MapView mapView = new MapView(map);
        MapController controller = new MapController(map);

        // Create a new creature
        // Creature creature = new Creature(100, 100, 5); // Example position (100, 100)
        // and speed 5

        // Add the creature to the map
        // map.addCreature(creature);

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

    }
}