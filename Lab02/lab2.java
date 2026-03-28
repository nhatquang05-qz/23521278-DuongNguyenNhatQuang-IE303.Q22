package Lab02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class lab2 extends JPanel implements ActionListener, KeyListener {
    // Bài 1
    private final int boardWidth = 360;
    private final int boardHeight = 640;

    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    // Bài 2
    int birdX = boardWidth / 8;
    int birdY = boardHeight / 2;
    int birdWidth = 34;
    int birdHeight = 24;

    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    // Bài 3
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe(Image img) {
            this.img = img;
        }
    }

    Bird bird;
    int velocityX = -4; 
    int velocityY = 0;  
    // Bài 2
    int gravity = 1;    

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer gameLoop;
    Timer placePipesTimer;
    
    // Bài 4
    boolean gameOver = false;
    double score = 0;

    public lab2() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        backgroundImg = new ImageIcon(getClass().getResource("./images/flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./images/flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./images/toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./images/bottompipe.png")).getImage();

        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();

        // Bài 3
        placePipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        placePipesTimer.start();

        // Bài 3
        gameLoop = new Timer(1000 / 60, this); 
        gameLoop.start();
    }

    public void placePipes() {
        // Bài 3
        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = boardHeight / 4;

        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Bài 1
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

        // Bài 2
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

        // Bài 3
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        // Bài 4
        g.setColor(Color.white);
        if (gameOver) {
            Font gameOverFont = new Font("Arial", Font.BOLD, 32);
            g.setFont(gameOverFont);
            FontMetrics goMetrics = g.getFontMetrics(gameOverFont);
            String goText = "Game Over: " + (int) score;
            int goX = (boardWidth - goMetrics.stringWidth(goText)) / 2;
            g.drawString(goText, goX, boardHeight / 2 - 50);

            Font restartFont = new Font("Arial", Font.PLAIN, 20);
            g.setFont(restartFont);
            FontMetrics rMetrics = g.getFontMetrics(restartFont);
            String rText = "Press 'Space' to Restart";
            int rX = (boardWidth - rMetrics.stringWidth(rText)) / 2;
            g.drawString(rText, rX, boardHeight / 2);
        } else {
            Font scoreFont = new Font("Arial", Font.BOLD, 32);
            g.setFont(scoreFont);
            FontMetrics scoreMetrics = g.getFontMetrics(scoreFont);
            String scoreText = String.valueOf((int) score);
            int scoreX = (boardWidth - scoreMetrics.stringWidth(scoreText)) / 2;
            g.drawString(scoreText, scoreX, 50);
        }
    }

    public void move() {
        // Bài 2
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);

        // Bài 3
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            // Bài 4
            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                score += 0.5; 
                pipe.passed = true;
            }

            // Bài 4
            if (collision(bird, pipe)) {
                gameOver = true;
            }
        }

        if (bird.y > boardHeight) {
            gameOver = true;
        }
    }

    public boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&
               a.x + a.width > b.x &&
               a.y < b.y + b.height &&
               a.y + a.height > b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();
            repaint();
        } else {
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Bài 2, Bài 4
        if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (gameOver) {
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                score = 0;
                gameOver = false;
                gameLoop.start();
                placePipesTimer.start();
            } else {
                velocityY = -9; 
            }
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        // Bài 1
        JFrame frame = new JFrame("Flappy Bird"); 
        frame.setSize(360, 640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lab2 flappyBird = new lab2();
        frame.add(flappyBird);
        frame.pack();
        flappyBird.requestFocus();
        frame.setVisible(true);
    }
}