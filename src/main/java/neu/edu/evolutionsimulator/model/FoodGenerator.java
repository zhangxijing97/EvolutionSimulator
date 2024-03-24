package neu.edu.evolutionsimulator.model;
import java.util.Hashtable;


import java.util.Random;

import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.util.List;
import neu.edu.evolutionsimulator.model.Food;
import neu.edu.evolutionsimulator.model.Map;

public class FoodGenerator {
    private Map map;
    private int maxFoodItems;
    private Timer generationTimer;

    public FoodGenerator(Map map) {
        this.map = map;
        this.maxFoodItems = 50; // Default value

        // Initialize the timer with some default interval and action
        this.generationTimer = new Timer(1000, e -> generateFood(this.maxFoodItems)); // Example: Generate food every second based on maxFoodItems
        this.generationTimer.start(); // Start the timer to generate food
    }

    public void setMaxFoodItems(int maxFoodItems) {
        this.maxFoodItems = maxFoodItems;
    }

    // Method to generate food items
    public void generateFood(int numberOfFoodItems) {

        Random random = new Random();
        int mapWidth = map.getWidth();
        int mapHeight = map.getHeight();
        // int mapWidth = 500;
        // int mapHeight = 500;

        for (int i = 0; i < numberOfFoodItems; i++) {
            int x = random.nextInt(mapWidth);
            int y = random.nextInt(mapHeight);
            int energy = random.nextInt(20) + 1; // Random energy value between 1 and 20
            Food food = new Food(x, y, energy);
            map.addFood(food);
        }

    }

    // Method to set the generation interval for the food generation timer
    public void setGenerationInterval(int interval) {
        if (generationTimer != null) {
            generationTimer.setDelay(interval);
        }
    }


    // Initializes a GUI slider to control the maximum number of food items 
    public JSlider initializeFoodItemsGUI() {
        JSlider foodItemsSlider = new JSlider(0, 100, maxFoodItems);
        foodItemsSlider.setMajorTickSpacing(10);
        foodItemsSlider.setPaintTicks(true);
        foodItemsSlider.setPaintLabels(true);
        foodItemsSlider.setBorder(BorderFactory.createTitledBorder("Max Number of Food Items"));

        foodItemsSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    setMaxFoodItems(source.getValue());
                }
            }
        });

        return foodItemsSlider;
    }

    public JSlider initializeGenerationIntervalGUI() {
        JSlider generationIntervalSlider = new JSlider(100, 5000, generationTimer.getDelay());
        generationIntervalSlider.setMajorTickSpacing(1000);
        generationIntervalSlider.setMinorTickSpacing(500);
        generationIntervalSlider.setPaintTicks(true);
        generationIntervalSlider.setPaintLabels(true);
        generationIntervalSlider.setBorder(BorderFactory.createTitledBorder("Generation Interval (ms)"));

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(100, new JLabel("100"));
        labelTable.put(2500, new JLabel("2500")); 
        labelTable.put(5000, new JLabel("5000"));
        generationIntervalSlider.setLabelTable(labelTable); 
    
        generationIntervalSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    setGenerationInterval(source.getValue());
                }
            }
        });
    
        return generationIntervalSlider;
    }
}
