package main;

import java.awt.*;

public class Player {

    private int x;
    private int y;
    private final int WIDTH = 100;
    private final int HEIGHT = 15;
    private final int SPEED = 20;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int keyCode) {

        if (keyCode == 37 && x > 0) { // LEFT
            x -= SPEED;
        }

        if (keyCode == 39 && x < 500) { // RIGHT
            x += SPEED;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
}