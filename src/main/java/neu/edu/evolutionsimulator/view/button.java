package neu.edu.evolutionsimulator.view;

import java.awt.*;

public class button {
    button() {
        Frame f = new Frame();

        // Button 1 created
        // OK button
        Button b1 = new Button("OK");
        b1.setBounds(100, 50, 50, 50);
        f.add(b1);

        // Button 2 created
        // Submit button
        Button b2 = new Button("SUBMIT");
        b2.setBounds(100, 101, 50, 50);
        f.add(b2);

        // Button 3 created
        // Cancel button
        Button b3 = new Button("CANCEL");
        b3.setBounds(100, 150, 80, 50);
        f.add(b3);

        f.setSize(500, 500);
        f.setLayout(null);
        f.setVisible(true);
    }

}
