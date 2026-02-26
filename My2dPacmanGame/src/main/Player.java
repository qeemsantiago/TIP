package main;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {

    int x, y;
    int speed = 5;
    int dx = 0, dy = 0;

    boolean up, down, left, right;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, 30, 30);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 30, 30);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) up = true;
        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) down = true;
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) left = true;
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) right = true;
        if (up) dy = -speed;
        if (down) dy = speed;
        if (left) dx = -speed;
        if (right) dx = speed;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) { up = false; dy = 0; }
        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) { down = false; dy = 0; }
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) { left = false; dx = 0; }
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) { right = false; dx = 0; }
    }
}