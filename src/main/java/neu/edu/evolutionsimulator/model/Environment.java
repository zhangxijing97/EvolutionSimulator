package neu.edu.evolutionsimulator.model;

import java.util.ArrayList;
import java.util.List;

public class Environment {
    private double optimalFurLength; // Optimal fur length for the environment
    private List<Creature> creatures; // Ensure this list is never null

    public Environment(double temperature) {
        this.optimalFurLength = calculateOptimalFurLength(temperature);
        this.creatures = new ArrayList<>(); // Initialize the list here
    }

    public double getOptimalFurLength() {
        return optimalFurLength;
    }

    public void setOptimalFurLength(double optimalFurLength) {
        this.optimalFurLength = optimalFurLength;
    }

    public double calculateOptimalFurLength(double temperature) {
        return 100 - temperature;
    }

    public List<Creature> getCreatures() {
        return creatures;
    }

    public void setCreatures(List<Creature> creatures) {
        this.creatures = creatures;
    }

    public double calculateAverageFurLength() {
        if (creatures.isEmpty())
            return 0;
        double totalFurLength = creatures.stream().mapToDouble(Creature::getFurLength).sum();
        return totalFurLength / creatures.size();
    }

}
