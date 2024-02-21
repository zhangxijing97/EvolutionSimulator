package neu.edu.evolutionsimulator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreatureInitializer {
    private static final int MAP_WIDTH = 500; // Assuming map width
    private static final int MAP_HEIGHT = 500; // Assuming map height

    public static List<Creature> initializeCreatures() {
        List<Creature> creatures = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 30; i++) {
            int x = random.nextInt(MAP_WIDTH);
            int y = random.nextInt(MAP_HEIGHT);
            Creature creature = new Creature(x, y, 1, 1); // Assuming speed is 1
            creatures.add(creature);
        }

        return creatures;
    }
}