package main;

import java.awt.*;
import java.util.Random;

public class Ghost {

    int x, y;
    int speed = 2;
    Random rand = new Random();
    int dx, dy;

    public Ghost(int x, int y) {
        this.x = x;
        this.y = y;
        dx = rand.nextBoolean() ? speed : -speed;
        dy = rand.nextBoolean() ? speed : -speed;
    }

    public void move(Player player) {
        // Chase player
        if (player.x > x) x += speed;
        if (player.x < x) x -= speed;
        if (player.y > y) y += speed;
        if (player.y < y) y -= speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, 30, 30);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 30, 30);
    }
}