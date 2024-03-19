package neu.edu.evolutionsimulator.model;

public class Environment {
    private double optimalFurLength; // Optimal fur length for the environment

    public Environment(double optimalFurLength) {
        this.optimalFurLength = optimalFurLength;
    }

    public double getOptimalFurLength() {
        return optimalFurLength;
    }

    // Setter method if you want to allow changing the optimal fur length
    // dynamically
    public void setOptimalFurLength(double optimalFurLength) {
        this.optimalFurLength = optimalFurLength;
    }
}
