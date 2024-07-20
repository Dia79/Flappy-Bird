import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 360;
    int boardHeight = 640;

    // Images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    // Bird
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

    // Pipes
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

    // Game logic
    Bird bird;
    int velocityX = -4;
    int velocityY = 0;
    int gravity = 1;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer gameLoop;
    Timer placePipesTimer;

    boolean gameOver = false;
    double score = 0;
    double bestScore = 0;

    JButton restartButton;
    JButton exitButton;

    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        // Load images
        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        // Bird
        bird = new Bird(birdImg);
        pipes = new ArrayList<>();

        // Place pipes timer
        placePipesTimer = new Timer(1500, new ActionListener() { // 1.5 seconds
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes(); // Placing a new pipe every 1.5 seconds
            }
        });
        placePipesTimer.start();

        // Game timer
        gameLoop = new Timer(1000 / 60, this); // 1000/60 = 16.6 ms
        gameLoop.start();

        // Restart and Exit buttons
        setLayout(null); // Use absolute positioning
        restartButton = new JButton("Restart");
        restartButton.setBounds(boardWidth / 2 - 75, boardHeight / 2 + 40, 150, 40);
        restartButton.setFont(new Font("Arial", Font.BOLD, 18));
        restartButton.setBackground(Color.LIGHT_GRAY);
        restartButton.setForeground(Color.BLACK);
        restartButton.setFocusPainted(false);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        add(restartButton);
        restartButton.setVisible(false);

        exitButton = new JButton("Exit");
        exitButton.setBounds(boardWidth / 2 - 75, boardHeight / 2 + 100, 150, 40);
        exitButton.setFont(new Font("Arial", Font.BOLD, 18));
        exitButton.setBackground(Color.LIGHT_GRAY);
        exitButton.setForeground(Color.BLACK);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(exitButton);
        exitButton.setVisible(false);
    }

    public void placePipes() {
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
        // Background
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);
    
        // Bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
    
        // Pipes
        for (Pipe pipe : pipes) {
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }
    
        // Draw score in top-left corner
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        g.drawString(String.valueOf((int) score), 10, 35);
    
        if (gameOver) {
            // Set up for translucent background
            g.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black
            int boxWidth = 300;
            int boxHeight = 180; // Adjusted height
            int boxX = (boardWidth - boxWidth) / 2;
            int boxY = (boardHeight - boxHeight) / 2;
            g.fillRect(boxX, boxY, boxWidth, boxHeight);
    
            // Draw game over text
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            FontMetrics metrics = g.getFontMetrics();
            String gameOverText = "Game Over";
            int textWidth = metrics.stringWidth(gameOverText);
            int textHeight = metrics.getHeight();
            g.drawString(gameOverText, boxX + (boxWidth - textWidth) / 2, boxY + textHeight + 20);
    
            // Draw score
            g.setFont(new Font("Arial", Font.PLAIN, 18));
            String scoreText = "Score: " + (int) score;
            textWidth = metrics.stringWidth(scoreText);
            g.drawString(scoreText, boxX + (boxWidth - textWidth) / 2, boxY + boxHeight / 2 - 10);
    
            // Draw best score
            String bestScoreText = "Best: " + (int) bestScore;
            textWidth = metrics.stringWidth(bestScoreText);
            g.drawString(bestScoreText, boxX + (boxWidth - textWidth) / 2, boxY + boxHeight / 2 + 20);
    
            // Draw buttons
            restartButton.setBounds(boxX + 20, boxY + boxHeight - 60, 120, 30); // Adjusted button position
            exitButton.setBounds(boxX + boxWidth - 140, boxY + boxHeight - 60, 120, 30); // Adjusted button position
            restartButton.setVisible(true);
            exitButton.setVisible(true);
        } else {
            // Ensure buttons are hidden during gameplay
            restartButton.setVisible(false);
            exitButton.setVisible(false);
        }
    }
    
    

    public void move() {
        if (!gameOver) {
            // Bird
            velocityY += gravity;
            bird.y += velocityY; // Update bird's position
            bird.y = Math.max(bird.y, 0); // Prevent bird from going off the top of the screen

            // Pipes
            for (Pipe pipe : pipes) {
                pipe.x += velocityX;

                if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                    pipe.passed = true;
                    score += 0.5; // 0.5 because there are 2 pipes! so 0.5*2 = 1, 1 for each set of pipes
                }

                if (Collision(bird, pipe)) {
                    gameOver = true;
                    bestScore = Math.max(bestScore, score);
                }
            }

            if (bird.y > boardHeight) {
                gameOver = true;
                bestScore = Math.max(bestScore, score);
            }
        }
    }

    public boolean Collision(Bird a, Pipe b) {
        return a.x < b.x + b.width && // a's top left corner doesn't reach b's top right corner
                a.x + a.width > b.x && // a's top right corner passes b's top left corner
                a.y < b.y + b.height && // a's top left corner doesn't reach b's bottom left corner
                a.y + a.height > b.y; // a's bottom left corner passes b's top left corner
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -9;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void restartGame() {
        bird.y = birdY;
        velocityY = 0;
        pipes.clear();
        score = 0;
        gameOver = false;
        gameLoop.start();
        placePipesTimer.start();
        restartButton.setVisible(false);
        exitButton.setVisible(false);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird");
        FlappyBird game = new FlappyBird();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
