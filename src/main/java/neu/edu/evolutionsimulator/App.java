package neu.edu.evolutionsimulator;
import java.util.Hashtable;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import neu.edu.evolutionsimulator.model.Creature;
import neu.edu.evolutionsimulator.model.CreatureGenerator;
import neu.edu.evolutionsimulator.model.CreatureInitializer;
import neu.edu.evolutionsimulator.model.Environment;
import neu.edu.evolutionsimulator.model.FoodGenerator;
import neu.edu.evolutionsimulator.model.Map;
import neu.edu.evolutionsimulator.view.MapView;
import neu.edu.evolutionsimulator.controller.MapController;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
    private static void createAndShowGUI() {
        // Get screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        // Set map to match screen size
        final Map map = new Map(screenWidth, screenHeight);
        final MapView mapView = new MapView(map);
        MapController controller = new MapController(map);
        final FoodGenerator foodGenerator = new FoodGenerator(map);
        CreatureInitializer creatureInitializer = new CreatureInitializer(map);
        final CreatureGenerator creatureGenerator = new CreatureGenerator(map);
        final Environment environment = new Environment(110);

        // Initialize the creatures
        List<Creature> creatures = creatureInitializer.initializeCreatures();
        for (Creature creature : creatures) {
            map.addCreature(creature);
        }

        JFrame frame = new JFrame("Evolution Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        // frame.add(mapView);
        // frame.addKeyListener(controller);
        // Set frame size to match screen size
        // frame.setSize(screenWidth, screenHeight);
        // frame.setVisible(true);

       
        // create settings panel    
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.setBorder(BorderFactory.createTitledBorder("Settings"));


        // Add various controls to the settings panel
        settingsPanel.add(creatureInitializer.initializeCreaturesGUI());
        settingsPanel.add(foodGenerator.initializeFoodItemsGUI());
        settingsPanel.add(foodGenerator.initializeGenerationIntervalGUI());
        settingsPanel.add(creatureGenerator.initializeGeneratorGUI());
        

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mapView, settingsPanel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(screenWidth - 300); // 假设设置面板宽度为 300 像素


        frame.add(splitPane, BorderLayout.CENTER);
        frame.addKeyListener(controller);
        frame.setSize(screenWidth, screenHeight);
        frame.setVisible(true);


        int delay = 100;
        new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapView.repaint();

                // Generate Food
                foodGenerator.generateFood(1);
                // creatureGenerator.generateOffspring(creatures);

                // Move creatures
                List<Creature> creaturesToRemove = new ArrayList<>();
                for (Creature creature : map.getCreatures()) {
                    creature.move(map.getFoods());
                    map.checkForFoodProximity(creature, map);
                    creature.updateEnergyBasedOnFur(environment);
                    if (creature.isDead()) {
                        creaturesToRemove.add(creature);
                    }
                }
                    map.getCreatures().removeAll(creaturesToRemove);

                    // Check if the creature is dead and remove it from the map
                

                // Create a list to store creatures generateOffspring
                List<Creature> creaturesToGenerateOffspring = new ArrayList<>(map.getCreatures());
                for (Creature creature : map.getCreatures()) {
                    creaturesToGenerateOffspring.add(creature);
                }

                // Generate Offspring
                creatureGenerator.generateOffspring(creaturesToGenerateOffspring);

                // Create a list to store creatures to be removed
            }      
            
        }).start();
    }
}


        // // (temp : Set setting screen for task4)
        // // final JFrame settingFrame = new JFrame("Setting");
        // // settingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
        // // settingFrame.setSize(300, 400);
        // // JPanel panel = new JPanel();
        // // panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // // Set slider for Number of Initial Creatures : Task4
        // CreatureInitializer ci = new CreatureInitializer(map);
        // JSlider creatureSlider = ci.initializeCreaturesGUI();
        // /* Needs to be added later
        // creatureSlider.addChangeListener(new ChangeListener() {
        //     @Override
        //     public void stateChanged(ChangeEvnet e) {
        //     }
        //     int newCreatureCount = creatureSlider.getValue();
        //     initializeSimulation(newCreatureCount);
        //     });        
        //  */
        // panel.add(creatureSlider);



        // // Slider for Maximum Number of Food Items
        // JSlider foodSlider = foodGenerator.initializeFoodItemsGUI();
        // panel.add(foodSlider);

        // JSlider generationSpeedSlider = foodGenerator.initializeGenerationIntervalGUI();
        // panel.add(generationSpeedSlider);

        // // Slider for Generation Timing
        // JSlider genTimeSlider = creatureGenerator.initializeGeneratorGUI();
        // panel.add(genTimeSlider);

        // // (temp : Set setting screen for task4)
        // settingFrame.add(panel);
        // settingFrame.pack(); // Adjust window size based on its contents
        // settingFrame.setVisible(true);



        // Game loop
//         while (true) {
//             // Update the view
//             mapView.repaint();

//             // Generate Food
//             foodGenerator.generateFood(1);
//             // creatureGenerator.generateOffspring(creatures);

//             // Move creatures
//             for (Creature creature : map.getCreatures()) {
//                 creature.move(map.getFoods());
//                 map.checkForFoodProximity(creature, map);
//                 creature.updateEnergyBasedOnFur(environment);

//                 // Check if the creature is dead and remove it from the map
//             }

//             // Create a list to store creatures generateOffspring
//             List<Creature> creaturesToGenerateOffspring = new ArrayList<>();
//             for (Creature creature : map.getCreatures()) {
//                 creaturesToGenerateOffspring.add(creature);
//             }

//             // Generate Offspring
//             creatureGenerator.generateOffspring(creaturesToGenerateOffspring);

//             // Create a list to store creatures to be removed
//             List<Creature> creaturesToRemove = new ArrayList<>();

//             // Iterate over the creatures and mark the dead ones for removal
//             for (Creature creature : map.getCreatures()) {
//                 if (creature.isDead()) {
//                     creaturesToRemove.add(creature); // Mark for removal
//                 }
//             }

//             // Remove the marked creatures from the map
//             for (Creature creature : creaturesToRemove) {
//                 map.removeCreature(creature);
//             }

//             // Optional: Add a delay to control the speed of movement
//             try {
//                 Thread.sleep(100); // Adjust the delay time as needed
//             } catch (InterruptedException e) {
//                 e.printStackTrace();
//             }
//         }

//     }
// }
