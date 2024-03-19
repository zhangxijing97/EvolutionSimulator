package neu.edu.evolutionsimulator.view;

import neu.edu.evolutionsimulator.model.Map;
import neu.edu.evolutionsimulator.model.Creature;
import neu.edu.evolutionsimulator.model.Food;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MapView extends JPanel {
    private Map map;

    public MapView(Map map) {
        this.map = map;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Render map
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Render foods
        g.setColor(Color.GREEN);
        List<Food> foods = map.getFoods();
        for (Food food : foods) {
            g.fillOval(food.getX(), food.getY(), 5, 5); // Assuming creature is represented by a circle
        }

        // Render creatures
        g.setColor(Color.BLUE);
        List<Creature> creatures = map.getCreatures();
        for (Creature creature : creatures) {
            int creatureX = (int) Math.round(creature.getX());
            int creatureY = (int) Math.round(creature.getY());
            double energy = creature.getEnergy();
            double furLength = creature.getFurLength();

            // Render creature as a circle
            g.fillOval(creatureX, creatureY, 10, 10);

            // Display energy value next to the creature
            g.setColor(Color.WHITE); // Set color for text
            g.drawString("Energy: " + energy, creatureX + 15, creatureY + 0);
            g.drawString("furLength: " + furLength, creatureX + 15, creatureY + 20);
            g.setColor(Color.BLUE); // Reset color back to creature color
        }

        // Calculate the mean of furLength
        double totalFurLength = 0;
        for (Creature creature : map.getCreatures()) {
            totalFurLength += creature.getFurLength();
        }
        double meanFurLength = creatures.isEmpty() ? 0 : totalFurLength / creatures.size();

        // Display the count of creatures
        g.setColor(Color.WHITE);
        g.drawString("Number of Creatures: " + creatures.size(), 10, 20);
        g.drawString("Mean of furLength: " + meanFurLength, 10, 40);
    }
}