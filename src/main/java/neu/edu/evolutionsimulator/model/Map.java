package neu.edu.evolutionsimulator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.awt.geom.Point2D;

public class Map {
    private int width;
    private int height;
    private List<Creature> creatures;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.creatures = new ArrayList<>();
    }

    // Add Creature
    public void addCreature(Creature creature) {
        creatures.add(creature);
    }

    // Remove Creature
    public void removeCreature(Creature creature) {
        creatures.remove(creature);
    }

    // Get Creature
    public List<Creature> getCreatures() {
        return creatures;
    }

    public void checkForFoodProximity(Creature creature, Map map) {
        // int creatureX = creature.getX();
        // int creatureY = creature.getY();

        int creatureX = (int) Math.round(creature.getX());
        int creatureY = (int) Math.round(creature.getY());

    }

}