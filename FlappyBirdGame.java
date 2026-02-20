import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class FlappyBirdGame extends JPanel implements ActionListener, KeyListener {

    int birdY = 250;
    int velocity = 0;
    int gravity = 1;
    int pipeX = 600;
    int gap = 150;
    int pipeHeight = 200;
    int score = 0;

    boolean gameStarted = false;

    javax.swing.Timer timer;
    Random rand = new Random();
    JButton startButton;

    public FlappyBirdGame() {
        setLayout(null);

        startButton = new JButton("Start Game");
        startButton.setBounds(230, 260, 140, 40);
        add(startButton);

        startButton.addActionListener(e -> startGame());

        addKeyListener(this);
        setFocusable(true);

        timer = new javax.swing.Timer(20, this);
    }

    void startGame() {
        gameStarted = true;
        birdY = 250;
        velocity = 0;
        pipeX = 600;
        score = 0;
        pipeHeight = rand.nextInt(200) + 100;
        startButton.setVisible(false);
        timer.start();
        requestFocusInWindow();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.cyan);
        g.fillRect(0, 0, 600, 600);

        g.setColor(Color.orange);
        g.fillOval(100, birdY, 30, 30);

        g.setColor(Color.green);
        g.fillRect(pipeX, 0, 60, pipeHeight);
        g.fillRect(pipeX, pipeHeight + gap, 60, 600);

        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 20, 30);

        if (!gameStarted) {
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("Press Start Button", 190, 200);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (!gameStarted) return;

        velocity += gravity;
        birdY += velocity;
        pipeX -= 5;

        if (pipeX < -60) {
            pipeX = 600;
            pipeHeight = rand.nextInt(200) + 100;
            score++;
        }

        Rectangle bird = new Rectangle(100, birdY, 30, 30);
        Rectangle topPipe = new Rectangle(pipeX, 0, 60, pipeHeight);
        Rectangle bottomPipe = new Rectangle(pipeX, pipeHeight + gap, 60, 600);

        if (bird.intersects(topPipe) || bird.intersects(bottomPipe) || birdY > 570 || birdY < 0) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Game Over! Score: " + score);
            startButton.setVisible(true);
            gameStarted = false;
        }

        repaint();
    }

    public void keyPressed(KeyEvent e) {
        if (gameStarted && e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocity = -10;
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird Game developed by Monty");
        FlappyBirdGame game = new FlappyBirdGame();
        frame.add(game);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
