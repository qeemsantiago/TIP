package main;

import java.awt.*;
import java.util.Random;

public class NumberTarget {

    int x, y;
    int value;
    int speed = 2;
    boolean collected = false;

    int directionX, directionY;
    Random rand = new Random();

    public NumberTarget(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
        directionX = rand.nextBoolean() ? 1 : -1;
        directionY = rand.nextBoolean() ? 1 : -1;
    }

    public void move() {
        x += directionX * speed;
        y += directionY * speed;
        if (x < 0 || x > 570) directionX *= -1;
        if (y < 0 || y > 370) directionY *= -1;
    }

    public void draw(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillOval(x, y, 30, 30);
        // value is hidden to make it mysterious
        // g.setColor(Color.BLACK);
        // g.drawString(String.valueOf(value), x + 10, y + 20);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 30, 30);
    }
}