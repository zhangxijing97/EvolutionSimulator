package neu.edu.evolutionsimulator.model;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private int width;
    private int height;
    private List<Creature> creatures;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.creatures = new ArrayList<>();
    }

    public void addCreature(Creature creature) {
        creatures.add(creature);
    }

    public void removeCreature(Creature creature) {
        creatures.remove(creature);
    }

    public List<Creature> getCreatures() {
        return creatures;
    }
}