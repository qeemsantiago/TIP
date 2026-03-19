package main;

import java.awt.*;
import java.util.Random;

public class Bomb {
    private int x, y;
    private int speed;
    private final int SIZE = 70; // Matches the sprite scale used in GamePanel
    private int baseSpeed = 5;   // Higher base speed for more challenge
    private Random rand = new Random();

    public Bomb() {
        reset();
    }

    public void move() {
        y += speed; 
    }

    /**
     * Call this from GamePanel to speed up bombs as the score increases
     */
    public void increaseDifficulty() {
        baseSpeed += 1;
    }

    public void reset() {
        // Corrected bounds: 500 width - 70 size = 430 max X
        x = rand.nextInt(430); 
        // Start further up to stagger arrival with the ball
        y = -200;               
        speed = baseSpeed + rand.nextInt(3); 
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // --- DARK MODE NEON RED ---
        // Glow effect to make it visible on the charcoal background
        g2.setColor(new Color(231, 76, 60, 100));
        g2.fillOval(x - 5, y - 5, SIZE + 10, SIZE + 10);
        
        // Main Bomb Body
        g2.setColor(new Color(231, 76, 60));
        g2.fillOval(x, y, SIZE, SIZE);
        
        // "TNT" Text Label
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.drawString("TNT", x + 20, y + 42);
    }

    /**
     * CLEANER COLLISION: Uses the player's internal getBounds() 
     * rather than creating a new rectangle for the player here.
     */
    public boolean checkCollision(Player p) {
        Rectangle bombRect = new Rectangle(x, y, SIZE, SIZE);
        return bombRect.intersects(p.getBounds());
    }

    public int getY() { return y; }
    public int getX() { return x; }
}