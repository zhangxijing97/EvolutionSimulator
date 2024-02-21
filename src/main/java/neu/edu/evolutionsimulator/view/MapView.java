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
            g.fillOval(creature.getX(), creature.getY(), 10, 10); // Assuming creature is represented by a circle
        }
    }
}