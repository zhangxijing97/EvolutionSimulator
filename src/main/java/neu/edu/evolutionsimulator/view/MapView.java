package neu.edu.evolutionsimulator.view;

import neu.edu.evolutionsimulator.model.Map;
import neu.edu.evolutionsimulator.model.Creature;

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

        // Render creatures
        List<Creature> creatures = map.getCreatures();
        for (Creature creature : creatures) {
            int creatureX = (int) Math.round(creature.getX());
            int creatureY = (int) Math.round(creature.getY());
            double furLength = creature.getFurLength();
            double survivalRate = creature.getSurvivalRate();

            // Set color for creature
            if (furLength < 100) {
                g.setColor(Color.YELLOW);
            } else if (furLength >= 100 && furLength < 105) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.RED);
            }

            // Render creature as a circle
            g.fillOval(creatureX, creatureY, 10, 10);

            // Display energy value next to the creature
            g.setColor(Color.WHITE); // Set color for text
            g.drawString("ID: " + creature.getId(), creatureX + 15, creatureY - 20);
            g.drawString("Ancestors: " + creature.getShortAncestorsAsString(), creatureX + 15, creatureY - 0);
            g.drawString(String.format("survivalRate: %.2f%%", survivalRate*100), creatureX + 15, creatureY + 20);
            g.drawString(String.format("furLength: %.2f", furLength), creatureX + 15, creatureY + 40);
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