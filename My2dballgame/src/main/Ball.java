package main;

import java.awt.*;
import java.util.Random;

public class Ball {

    private int x;
    private int y;
    private final int SIZE = 20;
    private final int SPEED = 7;
    private Random random = new Random();

    public Ball() {
        reset();
    }

    public void move() {
        y += SPEED;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, SIZE, SIZE);
    }

    public void reset() {
        x = random.nextInt(580);
        y = 0;
    }

    public boolean checkCollision(Player player) {
        Rectangle ballRect = new Rectangle(x, y, SIZE, SIZE);
        return ballRect.intersects(player.getBounds());
    }

    public int getY() {
        return y;
    }
}
