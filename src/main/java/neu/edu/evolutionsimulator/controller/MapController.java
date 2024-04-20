package neu.edu.evolutionsimulator.controller;

import neu.edu.evolutionsimulator.model.Map;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MapController implements KeyListener {
    private Map map;

    public MapController(Map map) {
        this.map = map;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("A key was pressed.");
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
