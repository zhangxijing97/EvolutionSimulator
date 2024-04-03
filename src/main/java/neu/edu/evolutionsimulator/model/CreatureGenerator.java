package neu.edu.evolutionsimulator.model;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class CreatureGenerator {
    private Map map;
    private Random random;
    private long lastGenerationTime;
    private long nextGenerationDelay;

    public CreatureGenerator(Map map) {
        this.map = map;
        this.random = new Random();
        this.lastGenerationTime = System.currentTimeMillis();
        this.nextGenerationDelay = getRandomDelay();
    }

    public List<Creature> generateOffspring(List<Creature> creatures) {
        List<Creature> offspring = new ArrayList<>();

        for (Creature parent : creatures) {
            // Calculate the time elapsed since the last generation for this creature
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - parent.getLastGenerationTime();

            // Check if enough time has passed for a new generation for this creature
            if (elapsedTime >= getRandomDelay()) {
                // Assume each creature has a chance to produce offspring at this moment
                if (random.nextBoolean()) { // Simplified logic, adjust the probability as needed
                    Creature child = createOffspring(parent);
                    map.addCreature(child);
                    offspring.add(child);
                }
                // Update the last generation time for this creature
                parent.setLastGenerationTime(currentTime);
            }
        }

        return offspring;
    }

    // public void generateOffspring(Creature parent) {
    // // List<Creature> offspring = new ArrayList<>();

    // // for (Creature parent : creatures) {
    // // Calculate the time elapsed since the last generation for this creature
    // long currentTime = System.currentTimeMillis();
    // long elapsedTime = currentTime - parent.getLastGenerationTime();

    // // Check if enough time has passed for a new generation for this creature
    // if (elapsedTime >= getRandomDelay()) {
    // // Assume each creature has a chance to produce offspring at this moment
    // if (random.nextBoolean()) { // Simplified logic, adjust the probability as
    // needed
    // Creature child = createOffspring(parent);
    // map.addCreature(child);
    // // offspring.add(child);
    // }
    // // Update the last generation time for this creature
    // parent.setLastGenerationTime(currentTime);
    // }
    // // }

    // // return offspring;
    // }

    private Creature createOffspring(Creature parent) {
        // Generate attributes for the offspring based on the parent's attributes.
        // Here, we're using a normal distribution centered at the parent's attribute
        // value
        // with a standard deviation that defines the variation from the parent.
        double furLength = generateAttributeValue(parent.getFurLength(), 10.0); // Example standard deviation
        // double speed = generateAttributeValue(parent.getSpeed(), 0.5);
        double speed = 1;
        // int energy = (int) generateAttributeValue(parent.getEnergy(), 10);
        double energy = 100;

        // Assume the x and y positions are inherited directly for simplicity,
        // or you could also generate them based on some logic.
        double x = parent.getX();
        double y = parent.getY();

        List<Integer> ancestors = parent.getAncestors();

        // Create the offspring creature with the derived attributes.
        return new Creature(x, y + 40, furLength, true, ancestors, 1);
    }

    private double generateAttributeValue(double mean, double stdDev) {
        double result = mean + (random.nextGaussian() * stdDev);
        System.out.println(result);
        return result;
    }

    // Generate a random delay between 0 and 20 seconds (inclusive)
    private long getRandomDelay() {
        return 0 + random.nextInt(500) * 1000; // Random number between 5 and 10 (inclusive) multiplied by 1000 for
                                               // milliseconds
    }
}
