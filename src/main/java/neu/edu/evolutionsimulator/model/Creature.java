package neu.edu.evolutionsimulator.model;

import java.util.Random;

public class Creature {
    private int x;
    private int y;
    private int speed;

    public Creature(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public void move() {
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

}