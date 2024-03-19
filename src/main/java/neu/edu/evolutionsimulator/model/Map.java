package neu.edu.evolutionsimulator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.awt.geom.Point2D;

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

    // Method to check for collision between creature and food
    // public void checkForFoodCollision(Creature creature, Map map) {
    // int creatureX = creature.getX();
    // int creatureY = creature.getY();

    // // Get the list of food items
    // List<Food> foods = map.getFoods();

    // // Iterate through the food items
    // Iterator<Food> iterator = foods.iterator();
    // while (iterator.hasNext()) {
    // Food food = iterator.next();
    // int foodX = food.getX();
    // int foodY = food.getY();

    // // Check if the creature and food are at the same position
    // if (creatureX == foodX && creatureY == foodY) {
    // // Remove the food item
    // iterator.remove();
    // // You can also update the creature's energy here if needed
    // creature.setEnergy(creature.getEnergy() + food.getEnergy());
    // }
    // }
    // }

    public void checkForFoodProximity(Creature creature, Map map) {
        int creatureX = creature.getX();
        int creatureY = creature.getY();

        // Get the list of food items
        List<Food> foods = map.getFoods();

        // Iterate through the food items
        Iterator<Food> iterator = foods.iterator();
        while (iterator.hasNext()) {
            Food food = iterator.next();
            int foodX = food.getX();
            int foodY = food.getY();

            // Calculate the distance between creature and food using Euclidean distance
            double distance = Point2D.distance(creatureX, creatureY, foodX, foodY);

            // Check if the distance is less than 5 units
            if (distance < 5) {
                // Remove the food item
                iterator.remove();
                // You can also update the creature's energy here if needed
                creature.setEnergy(creature.getEnergy() + food.getEnergy());
            }
        }
    }

}