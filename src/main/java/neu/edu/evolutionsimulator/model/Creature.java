package neu.edu.evolutionsimulator.model;

import java.util.List;
import java.util.Random;

public class Creature {
    private static int nextId = 0;
    private int id;
    private List<Integer> ancestors;
    private double x;
    private double y;
    private double furLength;
    private boolean isAlive;
    private double survivalRate; // 0.00 - 1.00
    private long lastGenerationTime;

    public Creature(double x, double y, double furLength, boolean isAlive, List<Integer> ancestorIds,
            double survivalRate) {
        this.id = getNextId();
        this.x = x;
        this.y = y;
        this.furLength = furLength;
        this.isAlive = isAlive;
        this.lastGenerationTime = System.currentTimeMillis();
        this.ancestors = ancestorIds;
        this.ancestors.add(this.id);
        this.survivalRate = survivalRate;
    }

    private static synchronized int getNextId() {
        return nextId++;
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

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void setSurvivalRate(double survivalRate) {
        this.survivalRate = survivalRate;
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

    public boolean getIsAlive() {
        return isAlive;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getAncestors() {
        return ancestors;
    }

    public String getAncestorsAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < ancestors.size(); i++) {
            sb.append(ancestors.get(i));
            if (i < ancestors.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public long getLastGenerationTime() {
        return lastGenerationTime;
    }

    public double getSurvivalRate() {
        return survivalRate;
    }

    // Move
    public void move() {
        moveRandomly();
    }

    private void moveRandomly() {
        // Generate random direction
        Random random = new Random();
        double directionX = random.nextDouble() * 2 - 1; // Generates a value between -1.0 and 1.0
        double directionY = random.nextDouble() * 2 - 1; // Generates a value between -1.0 and 1.0

        // Update position based on speed and direction
        x += directionX * 1;
        y += directionY * 1;

        // Ensure the creature stays within the map bounds
        // x = Math.max(0, Math.min(x, 2000 - 1));
        // y = Math.max(0, Math.min(y, 1000 - 1));
    }

    // Method to move towards a specific position
    private void moveTo(double targetX, double targetY) {
        // Calculate direction towards the target
        double directionX = Double.compare(targetX, x);
        double directionY = Double.compare(targetY, y);

        // Update position based on speed and direction
        x += directionX * 1;
        y += directionY * 1;
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
    }

    public void updateSurvivalRate(Environment environment) {
        double optimalFurLength = environment.getOptimalFurLength();
        double furDifference = Math.abs(this.furLength - optimalFurLength);

        // Example formula to adjust survival rate based on fur difference.
        // This is a simple linear relationship; you may want to use a
        // more complex formula depending on your simulation's needs.
        // Assuming a certain threshold beyond which survival rate is 0.
        final double maxTolerableDifference = 1000.0; // Maximum difference tolerated

        // Ensure that furDifference does not exceed maxTolerableDifference.
        furDifference = Math.min(furDifference, maxTolerableDifference);

        // Calculate survival rate - linear decrease from 1 to 0.
        this.survivalRate = 1.0 - (furDifference / maxTolerableDifference);

        // Ensure survival rate is not less than 0.
        this.survivalRate = Math.max(0.0, this.survivalRate);
    }

    public void determineSurvival() {
        // Generate a random number between 0.0 (inclusive) and 1.0 (exclusive)
        Random random = new Random();
        double sample = random.nextDouble();

        // Compare the sample to the survival rate to determine survival
        if (sample > this.survivalRate) {
            // The creature does not survive
            this.isAlive = false;
        } else {
            // The creature survives, no need to change isAlive to true here
            // because it should already be true by default; however, if there's
            // any logic that may set isAlive to false elsewhere and you want to
            // allow for "revival", you could uncomment the next line.
            // this.isAlive = true;
        }
    }

    public boolean isDead() {
        return isAlive == false;
    }

    public void setLastGenerationTime(long lastGenerationTime) {
        this.lastGenerationTime = lastGenerationTime;
    }
}