package neu.edu.evolutionsimulator.model;

import java.util.Random;
import java.util.List;
import neu.edu.evolutionsimulator.model.Food;
import neu.edu.evolutionsimulator.model.Map;

public class FoodGenerator {
    private Map map;

    public FoodGenerator(Map map) {
        this.map = map;
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
}