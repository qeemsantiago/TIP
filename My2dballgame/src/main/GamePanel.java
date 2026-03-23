package main;

import javax.swing.*;

import objects.Ball;
import objects.Bomb;
import objects.Player;

import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    Player player;
    Ball ball;
    Bomb bomb;
    Timer timer;
    
    int score = 0;
    int timeLeft = 30; 
    int frameCounter = 0; 
    
    boolean gameStarted = false; 
    boolean gameOver = false;
    boolean gameWon = false;
 // Two separate managers so they don't fight for the same speaker
    SoundManager music = new SoundManager();
    SoundManager sfx = new SoundManager();

    public GamePanel() {
        this.setPreferredSize(new Dimension(500, 400));
        this.setBackground(new Color(28, 30, 34));
        
        player = new Player(215, 350); 
        ball = new Ball();
        bomb = new Bomb();
        
        timer = new Timer(15, this); 
        timer.start();

        this.setFocusable(true);
        this.addKeyListener(this);

        // --- FIX 1: START MUSIC WHEN APP OPENS ---
        playMusic(0); 
    }

    private void restartGame() {
        score = 0;
        timeLeft = 30;
        frameCounter = 0;
        gameOver = false;
        gameWon = false;
        gameStarted = true; 
        ball.reset();
        bomb.reset();
        player.leftPressed = false;
        player.rightPressed = false;
        
     // --- ADD THIS LINE TO RESTART MUSIC ---
        playMusic(0);
        timer.start();
        repaint();
    }

    public void playMusic(int i) {
        music.setFile(i); 
        music.play();    
        music.loop();    
    }

    public void stopMusic() {
        music.stop(); 
    }

    public void playSE(int i) {
        sfx.setFile(i); 
        sfx.play();    
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // --- 1. START MENU UI ---
        if (!gameStarted) {
            drawCenteredString(g2, "BALL CATCHER", new Font("Segoe UI", Font.BOLD, 58), new Color(52, 152, 219), 160);
            drawCenteredString(g2, "Collect 20 points in 30s", new Font("Segoe UI", Font.PLAIN, 18), Color.LIGHT_GRAY, 210);
            drawCenteredString(g2, "Avoid the bombs!", new Font("Segoe UI", Font.BOLD, 18), new Color(231, 76, 60), 240);
            
            float alpha = (float) (Math.sin(System.currentTimeMillis() * 0.005) * 0.5 + 0.5);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            drawCenteredString(g2, "PRESS 'ENTER' TO START", new Font("Segoe UI", Font.BOLD, 22), Color.WHITE, 340);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            return;
        }

        // --- 2. GAME OVER / WIN SCREENS ---
        if (gameWon) {
            drawCenteredString(g2, "MISSION SUCCESS!", new Font("Segoe UI", Font.BOLD, 48), new Color(46, 204, 113), 200);
            drawCenteredString(g2, "Score: " + score, new Font("Segoe UI", Font.PLAIN, 24), Color.WHITE, 260);
            return;
        }
        
        if (gameOver) {
            // Large Red Title
            drawCenteredString(g2, "GAME OVER", new Font("Segoe UI", Font.BOLD, 52), new Color(231, 76, 60), 200);
            
            // The reason why they lost (White text)
            String reason = (timeLeft <= 0) ? "Time Ran Out!" : "You hit a bomb!";
            drawCenteredString(g2, reason, new Font("Segoe UI", Font.PLAIN, 20), Color.WHITE, 250);
            
            // The instruction to restart (Light Gray/Italic)
            drawCenteredString(g2, "PRESS 'R' TO RETRY", new Font("Segoe UI", Font.ITALIC, 18), Color.LIGHT_GRAY, 310);
            
            return;
        }

        // --- 3. HUD ---
        g2.setFont(new Font("Segoe UI", Font.BOLD, 24));
        g2.setColor(Color.WHITE);
        g2.drawString("Score: " + score, 20, 40);
        
        if (timeLeft <= 5) g2.setColor(new Color(231, 76, 60));
        g2.drawString("Time: " + timeLeft + "s", getWidth() - 140, 40);

        // Progress Bar (Neon Green)
        g2.setColor(new Color(255, 255, 255, 30));
        g2.fillRoundRect(20, 55, 150, 8, 4, 4);
        g2.setColor(new Color(46, 204, 113));
        int barWidth = (int) ((Math.min(score, 20) / 20.0) * 150);
        g2.fillRoundRect(20, 55, barWidth, 8, 4, 4);

        // Render Game Objects
        player.draw(g2);
        ball.draw(g2);
        bomb.draw(g2);
    }

    private void drawCenteredString(Graphics g, String text, Font font, Color color, int y) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (getWidth() - metrics.stringWidth(text)) / 2;
        g.setFont(font);
        g.setColor(color);
        g.drawString(text, x, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameStarted && !gameOver && !gameWon) {
            player.update(getWidth()); 
            ball.move();
            bomb.move();

            frameCounter++;
            if (frameCounter >= 60) {
                timeLeft--;
                frameCounter = 0;
            }

            if (timeLeft <= 0) {
                stopMusic(); // Stop music when time ends
                if (score >= 20) {
                    gameWon = true;
                } else {
                    gameOver = true;
                    playSE(3); // Play Game Over sound
                }
                timer.stop();
            }

         // CATCHING THE BALL
            if (ball.checkCollision(player)) {
                score++;
                ball.reset();
                playSE(1); // Music keeps playing because this uses 'sfx' object
            }

            // HITTING THE BOMB
            if (bomb.checkCollision(player)) {
                gameOver = true;
                timer.stop();
                stopMusic(); // We stop music here because the game is over
                playSE(2);   // Play explosion
            }

            if (ball.getY() > getHeight()) ball.reset();
            if (bomb.getY() > getHeight()) bomb.reset();
        }
        repaint();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (!gameStarted && code == KeyEvent.VK_ENTER) {
            gameStarted = true;
        }
        if (gameStarted && !gameOver && !gameWon) {
            // WASD + Arrows
            if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) player.leftPressed = true;
            if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) player.rightPressed = true;
        }
        if ((gameOver || gameWon) && code == KeyEvent.VK_R) {
            restartGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) player.leftPressed = false;
        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) player.rightPressed = false;
    }

    @Override public void keyTyped(KeyEvent e) {}
}
