package neu.edu.evolutionsimulator.model;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private int width;
    private int height;
    private List<Creature> creatures;
    private List<Food> foods;

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
        this.foods = new ArrayList<>();
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

    // Add Food
    public void addFood(Food food) {
        foods.add(food);
    }

    // Remove Food
    public void removeFood(Food food) {
        foods.remove(food);
    }

    // Get Food
    public List<Food> getFoods() {
        return foods;
    }

}