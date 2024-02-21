package neu.edu.evolutionsimulator.model;

import java.util.List;
import java.util.Random;

public class Creature {
    private int x;
    private int y;
    private int speed;
    private int energy;

    public Creature(int x, int y, int speed, int energy) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.energy = energy;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public int getEnergy() {
        return energy;
    }

    // Move to find the foods
    public void move(List<Food> foods) {
        if (foods.isEmpty()) {
            // If there are no food items, move randomly
            moveRandomly();
        } else {
            // Find the nearest food item
            Food nearestFood = findNearestFood(foods);
            if (nearestFood != null) {
                // Move towards the nearest food item
                moveTo(nearestFood.getX(), nearestFood.getY());
            }
        }
    }

    // Method to move randomly
    private void moveRandomly() {
        // Generate random direction
        Random random = new Random();
        int directionX = random.nextInt(3) - 1; // -1, 0, or 1
        int directionY = random.nextInt(3) - 1; // -1, 0, or 1

        // Update position based on speed and direction
        x += directionX * speed;
        y += directionY * speed;

        // Ensure the creature stays within the map bounds
        x = Math.max(0, Math.min(x, 500 - 1));
        y = Math.max(0, Math.min(y, 500 - 1));
    }

    // Method to find the nearest food item
    private Food findNearestFood(List<Food> foods) {
        Food nearestFood = null;
        double minDistance = Double.MAX_VALUE;

        for (Food food : foods) {
            double distance = calculateDistance(food.getX(), food.getY());
            if (distance < minDistance) {
                minDistance = distance;
                nearestFood = food;
            }
        }

        return nearestFood;
    }

    // Method to move towards a specific position
    private void moveTo(int targetX, int targetY) {
        // Calculate direction towards the target
        int directionX = Integer.compare(targetX, x);
        int directionY = Integer.compare(targetY, y);

        // Update position based on speed and direction
        x += directionX * speed;
        y += directionY * speed;
    }

    // Method to calculate distance to a specific position
    private double calculateDistance(int targetX, int targetY) {
        int dx = targetX - x;
        int dy = targetY - y;
        return Math.sqrt(dx * dx + dy * dy);
    }

}