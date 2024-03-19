package neu.edu.evolutionsimulator.model;

import java.util.List;
import java.util.Random;

public class Creature {
    private double x;
    private double y;
    private double furLength;
    private double speed;
    private double energy;

    private long lastGenerationTime;

    public Creature(double x, double y, double furLength, double speed, double energy) {
        this.x = x;
        this.y = y;
        this.furLength = furLength;
        this.speed = speed;
        this.energy = energy;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setFurLength(double furLength) {
        this.furLength = furLength;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getFurLength() {
        return furLength;
    }

    public double getSpeed() {
        return speed;
    }

    public double getEnergy() {
        return energy;
    }

    public long getLastGenerationTime() {
        return lastGenerationTime;
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

    private void moveRandomly() {
        // Generate random direction
        Random random = new Random();
        double directionX = random.nextDouble() * 2 - 1; // Generates a value between -1.0 and 1.0
        double directionY = random.nextDouble() * 2 - 1; // Generates a value between -1.0 and 1.0

        // Update position based on speed and direction
        x += directionX * speed;
        y += directionY * speed;

        // Ensure the creature stays within the map bounds
        // x = Math.max(0, Math.min(x, 2000 - 1));
        // y = Math.max(0, Math.min(y, 1000 - 1));
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
    private void moveTo(double targetX, double targetY) {
        // Calculate direction towards the target
        double directionX = Double.compare(targetX, x);
        double directionY = Double.compare(targetY, y);

        // Update position based on speed and direction
        x += directionX * speed;
        y += directionY * speed;
    }

    // Method to calculate distance to a specific position
    private double calculateDistance(double targetX, double targetY) {
        double dx = targetX - x;
        double dy = targetY - y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Method to update energy based on the environment's optimal fur length
    public void updateEnergyBasedOnFur(Environment environment) {
        double optimalFurLength = environment.getOptimalFurLength();
        double furDifference = Math.abs(this.furLength - optimalFurLength);

        // Assuming the energy reduction is directly proportional to the difference in
        // fur length
        // You can adjust the formula as needed
        double energyLoss = furDifference * 0.1; // Example formula

        this.energy -= energyLoss;

        // Ensure energy does not fall below 0
        if (this.energy < 0) {
            this.energy = 0;
        }
    }

    public boolean isDead() {
        return energy <= 0;
    }

    public void setLastGenerationTime(long lastGenerationTime) {
        this.lastGenerationTime = lastGenerationTime;
    }
}