package neu.edu.evolutionsimulator.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartView extends JPanel {
    private JButton startButton;

    public StartView() {
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // onStart.run(); // Execute the provided action when the button is clicked
            }
        });
        add(startButton);
    }
}