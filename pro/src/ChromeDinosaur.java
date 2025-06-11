
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class ChromeDinosaur implements ActionListener, KeyListener {
    private final int boardWidth = 750;
    private final int boardHeight = 250;

    // Images
    private Image dinosaurImg;
    private Image dinosaurDeadImg;
    private Image dinosaurJumpImg;
    private Image cactus1Img;
    private Image cactus2Img;
    private Image cactus3Img;

    private JFrame frame;
    private GamePanel gamePanel;

    class Block {
        int x;
        int y;
        int width;
        int height;
        Image img;

        Block(int x, int y, int width, int height, Image img) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.img = img;
        }
    }

    // Dinosaur
    private final int dinosaurWidth = 88;
    private final int dinosaurHeight = 94;
    private final int dinosaurX = 50;
    private final int dinosaurY = boardHeight - dinosaurHeight;

    Block dinosaur;

    // Cactus
    private final int cactus1Width = 34;
    private final int cactus2Width = 69;
    private final int cactus3Width = 102;

    private final int cactusHeight = 70;
    private final int cactusX = 700;
    private final int cactusY = boardHeight - cactusHeight;
    private ArrayList<Block> cactusArray;

    // Physics
    private int velocityX = -12; // cactus moving left speed
    private int velocityY = 0;   // dinosaur jump speed
    private final int gravity = 1;

    private boolean gameOver = false;
    private int score = 0;

    private Timer gameLoop;
    private Timer placeCactusTimer;

    // Inner class for game panel to handle painting and input
    private class GamePanel extends JPanel {
        public GamePanel() {
            setPreferredSize(new Dimension(boardWidth, boardHeight));
            setBackground(Color.lightGray);
            setFocusable(true);
            addKeyListener(ChromeDinosaur.this);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            draw(g);
        }
    }

    public ChromeDinosaur() {
        loadImages();

        // Dinosaur
        dinosaur = new Block(dinosaurX, dinosaurY, dinosaurWidth, dinosaurHeight, dinosaurImg);
        cactusArray = new ArrayList<>();

        gameLoop = new Timer(1000 / 60, this);
        placeCactusTimer = new Timer(1500, e -> placeCactus());
    }

    private void loadImages() {
        dinosaurImg = new ImageIcon(getClass().getResource("/imj/dino-run.gif")).getImage();
        dinosaurDeadImg = new ImageIcon(getClass().getResource("/imj/dino-dead.png")).getImage();
        dinosaurJumpImg = new ImageIcon(getClass().getResource("/imj/dino-jump.png")).getImage();
        cactus1Img = new ImageIcon(getClass().getResource("/imj/big-cactus1.png")).getImage();
        cactus2Img = new ImageIcon(getClass().getResource("/imj/big-cactus2.png")).getImage();
        cactus3Img = new ImageIcon(getClass().getResource("/imj/big-cactus3.png")).getImage();
    }

    public void startGame() {
        frame = new JFrame("Chrome Dinosaur");
        gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        gamePanel.requestFocus();

        resetGame();
        gameLoop.start();
        placeCactusTimer.start();
    }

    private void resetGame() {
        dinosaur.y = dinosaurY;
        dinosaur.img = dinosaurImg;
        velocityY = 0;
        cactusArray.clear();
        score = 0;
        gameOver = false;
    }

    private void placeCactus() {
        if (gameOver) return;

        double chance = Math.random();
        if (chance > 0.90) {
            cactusArray.add(new Block(cactusX, cactusY, cactus3Width, cactusHeight, cactus3Img));
        } else if (chance > 0.70) {
            cactusArray.add(new Block(cactusX, cactusY, cactus2Width, cactusHeight, cactus2Img));
        } else if (chance > 0.50) {
            cactusArray.add(new Block(cactusX, cactusY, cactus1Width, cactusHeight, cactus1Img));
        }

        if (cactusArray.size() > 10) {
            cactusArray.remove(0);
        }
    }

    private void draw(Graphics g) {
        // Dinosaur
        g.drawImage(dinosaur.img, dinosaur.x, dinosaur.y, dinosaur.width, dinosaur.height, null);

        // Cactus
        for (Block cactus : cactusArray) {
            g.drawImage(cactus.img, cactus.x, cactus.y, cactus.width, cactus.height, null);
        }

        // Score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Courier", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("Game Over: " + score, 10, 35);
        } else {
            g.drawString(String.valueOf(score), 10, 35);
        }
    }

    private void move() {
        velocityY += gravity;
        dinosaur.y += velocityY;

        if (dinosaur.y > dinosaurY) {
            dinosaur.y = dinosaurY;
            velocityY = 0;
            dinosaur.img = dinosaurImg;
        }

        for (Block cactus : cactusArray) {
            cactus.x += velocityX;
            if (collision(dinosaur, cactus)) {
                gameOver = true;
                dinosaur.img = dinosaurDeadImg;
            }
        }

        score++;
    }

    private boolean collision(Block a, Block b) {
        return a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y;
    }

    // Timer event
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();
        } else {
            gameLoop.stop();
            placeCactusTimer.stop();
        }
        gamePanel.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (dinosaur.y == dinosaurY) {
                velocityY = -17;
                dinosaur.img = dinosaurJumpImg;
            }

            if (gameOver) {
                resetGame();
                gameLoop.start();
                placeCactusTimer.start();
            }
        }
    }
    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}

