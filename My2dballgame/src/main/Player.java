package main;

import java.awt.*;

public class Player {

    public int x, y; // Made public for easier access in GamePanel
    public final int WIDTH = 80;  // Slightly wider for better catching
    public final int HEIGHT = 14; 
    private final int SPEED = 10;  // Increased speed for WASD responsiveness

    // Flags for both Arrow Keys and WASD
    public boolean leftPressed = false;
    public boolean rightPressed = false;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(int panelWidth) {
        if (leftPressed && x > 0) {
            x -= SPEED;
        }
        if (rightPressed && x < panelWidth - WIDTH) {
            x += SPEED;
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // --- DARK MODE NEON STYLE ---
        // Vibrant Cyan stands out perfectly on image_fedee4.png
        g2.setColor(new Color(0, 255, 255)); 
        g2.fillRoundRect(x, y, WIDTH, HEIGHT, 12, 12); 
        
        // Add a "Glow" effect
        g2.setColor(new Color(0, 255, 255, 50));
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x - 2, y - 2, WIDTH + 4, HEIGHT + 4, 15, 15);
        
        // Subtle highlight
        g2.setColor(new Color(255, 255, 255, 150));
        g2.fillRoundRect(x + 5, y + 3, WIDTH - 10, 3, 5, 5);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
}
