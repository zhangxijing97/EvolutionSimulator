package neu.edu.evolutionsimulator.model;

public class Food {
    private int x;
    private int y;
    private int energy;

    public Food(int x, int y, int energy) {
        this.x = x;
        this.y = y;
        this.energy = energy;
    }

    // Getters and setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}