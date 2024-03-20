package neu.edu.evolutionsimulator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CreatureInitializer {
    private Map map;
    private int numberOfCreatures = 30;

    public CreatureInitializer(Map map) {
        this.map = map;
    }

    public void setNumberOfCreatures(int num) {
        this.numberOfCreatures = num;
    }

    public List<Creature> initializeCreatures() {
        Random random = new Random();
        int mapWidth = map.getWidth();
        int mapHeight = map.getHeight();

        List<Creature> creatures = new ArrayList<>();

        for (int i = 0; i < numberOfCreatures; i++) {
            int x = random.nextInt(mapWidth);
            int y = random.nextInt(mapHeight);
            List<Integer> emptyList = new ArrayList<>();
            Creature creature = new Creature(x, y, 100, 1, 100, emptyList); // Assuming speed is 1
            creatures.add(creature);
        }

        return creatures;
    }

    // Add slider for number of creatures : Task4
    public JSlider initializeCreaturesGUI() {
        JSlider numberOfCreaturesSlider = new JSlider(0, 100, numberOfCreatures);
        numberOfCreaturesSlider.setMajorTickSpacing(10);
        numberOfCreaturesSlider.setPaintTicks(true);
        numberOfCreaturesSlider.setPaintLabels(true);
        numberOfCreaturesSlider.setBorder(BorderFactory.createTitledBorder("Number of Creatures"));

        numberOfCreaturesSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                if (!source.getValueIsAdjusting()) {
                    setNumberOfCreatures(source.getValue());
                }
            }});
        
        return numberOfCreaturesSlider;
    }
}