package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    Player player;
    Ball ball;
    Timer timer;
    int score = 0;

    public GamePanel() {
        player = new Player(250, 350);
        ball = new Ball();

        timer = new Timer(15, this);
        timer.start();

        this.setFocusable(true);
        this.addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);

        player.draw(g);
        ball.draw(g);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 20, 30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        ball.move();

        if (ball.checkCollision(player)) {
            score++;
            ball.reset();
        }

        if (ball.getY() > 400) {
            ball.reset();
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        player.move(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}