package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    Timer timer;
    Player player;
    Ghost ghost;
    ArrayList<NumberTarget> numbers;
    ArrayList<Wall> walls;

    int currentIndex = 0;
    int[] pin = new int[4];
    int lives = 3;

    public GamePanel() {

        setPreferredSize(new Dimension(600, 400));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        player = new Player(50, 50);
        ghost = new Ghost(500, 300);
        numbers = new ArrayList<>();
        walls = new ArrayList<>();

        Random rand = new Random();
        for (int i = 0; i < 4; i++) {
            pin[i] = rand.nextInt(10);
            numbers.add(new NumberTarget(rand.nextInt(500)+50, rand.nextInt(300)+50, pin[i]));
        }

        // Add some maze walls
        walls.add(new Wall(100, 100, 400, 10));
        walls.add(new Wall(100, 200, 10, 200));
        walls.add(new Wall(300, 50, 10, 200));
        walls.add(new Wall(200, 300, 300, 10));

        timer = new Timer(20, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        player.move();

        // Collision with walls
        for (Wall w : walls) {
            if (player.getBounds().intersects(w.getBounds())) {
                // Simple bounce back
                if (player.dx > 0) player.x -= player.dx;
                if (player.dx < 0) player.x -= player.dx;
                if (player.dy > 0) player.y -= player.dy;
                if (player.dy < 0) player.y -= player.dy;
            }
        }

        for (NumberTarget n : numbers) {
            n.move();
            if (!n.collected && player.getBounds().intersects(n.getBounds())) {
                if (currentIndex < 4 && n.value == pin[currentIndex]) {
                    n.collected = true;
                    currentIndex++;
                    for (NumberTarget num : numbers) if (!num.collected) num.speed = 4;
                } else {
                    for (NumberTarget num : numbers) if (!num.collected) num.speed = 2;
                }
            }
        }

        ghost.move(player);
        if (player.getBounds().intersects(ghost.getBounds())) {
            lives--;
            player.x = 50; player.y = 50;
            if (lives <= 0) {
                timer.stop();
            }
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Wall w : walls) w.draw(g);

        player.draw(g);
        ghost.draw(g);

        for (NumberTarget n : numbers) if (!n.collected) n.draw(g);

        g.setColor(Color.WHITE);
        g.drawString("Lives: " + lives, 20, 20);

        if (currentIndex == 4) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.setColor(Color.GREEN);
            g.drawString("PIN UNLOCKED!", 150, 200);
        } else if (lives <= 0) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.setColor(Color.RED);
            g.drawString("GAME OVER", 180, 200);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press R to Restart", 200, 240);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (lives <= 0 && e.getKeyCode() == KeyEvent.VK_R) {
            restart();
        }
        player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    private void restart() {
        currentIndex = 0;
        lives = 3;
        Random rand = new Random();
        numbers.clear();
        for (int i = 0; i < 4; i++) {
            pin[i] = rand.nextInt(10);
            numbers.add(new NumberTarget(rand.nextInt(500)+50, rand.nextInt(300)+50, pin[i]));
        }
        player.x = 50; player.y = 50;
        ghost.x = 500; ghost.y = 300;
        timer.start();
    }
}