package neu.edu.evolutionsimulator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreatureInitializer {

    private Map map;

    public CreatureInitializer(Map map) {
        this.map = map;
    }

    public List<Creature> initializeCreatures(int numberOfCreatures) {
        Random random = new Random();
        int mapWidth = map.getWidth();
        int mapHeight = map.getHeight();


        
        List<Creature> creatures = new ArrayList<>();

        for (int i = 0; i < numberOfCreatures; i++) {
            int x = random.nextInt(mapWidth);
            int y = random.nextInt(mapHeight);
            List<Integer> emptyList = new ArrayList<>();
            Creature creature = new Creature(x, y, 100, true, emptyList, 1); // Assuming speed is 1
            creatures.add(creature);
        }

        return creatures;
    }
    
    public void reinitializeCreatures(int numberOfCreatures) {
        // Clear existing creatures from the map
        this.map.clearCreatures();  // Make sure your Map class has the clearCreatures method

        // Initialize new creatures
        List<Creature> newCreatures = initializeCreatures(numberOfCreatures);
        for (Creature creature : newCreatures) {
            this.map.addCreature(creature);
        }
    }



}