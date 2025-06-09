import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class MindDevelopmentApp extends JFrame {

    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);
    private JCheckBox speedBox, mathBox, memoryBox;
    private JButton startButton;

    public MindDevelopmentApp() {
        setTitle("Mind App");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel.add(createSelectionScreen(), "selection");
        cardLayout.show(mainPanel, "selection");

        add(mainPanel);
        setVisible(true);
    }

    // شاشة اختيار اللعبة
    private JPanel createSelectionScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel title = new JLabel("Choose a Game");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        speedBox = new JCheckBox("Speed Game");
        speedBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        mathBox = new JCheckBox("Math Game");
        mathBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        memoryBox = new JCheckBox("Memory Game");
        memoryBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton = new JButton("Start");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(e -> {
            // تحقق من الخيارات المحددة (اختر أول لعبة فقط لتبسيط التنقل)
            if (speedBox.isSelected()) {
                mainPanel.add(new SpeedGamePanel(), "speed");
                cardLayout.show(mainPanel, "speed");
            } else if (mathBox.isSelected()) {
                mainPanel.add(new MathGamePanel(), "math");
                cardLayout.show(mainPanel, "math");
            } else if (memoryBox.isSelected()) {
                mainPanel.add(new MemoryGamePanel(), "memory");
                cardLayout.show(mainPanel, "memory");
            } else {
                JOptionPane.showMessageDialog(this, "Please select at least one game.");
            }
        });

        panel.add(title);
        panel.add(Box.createVerticalStrut(20));
        panel.add(speedBox);
        panel.add(mathBox);
        panel.add(memoryBox);
        panel.add(Box.createVerticalStrut(20));
        panel.add(startButton);

        return panel;
    }

    // لعبة السرعة (مبسطة)
    class SpeedGamePanel extends JPanel {
        private JLabel wordLabel = new JLabel("", SwingConstants.CENTER);
        private JButton matchButton = new JButton("Match");
        private JButton noMatchButton = new JButton("No Match");
        private String[] words = {"RED", "GREEN", "BLUE"};
        private Color[] colors = {Color.RED, Color.GREEN, Color.BLUE};
        private Random random = new Random();
        private String currentWord;
        private Color currentColor;
        private boolean isMatch;

        public SpeedGamePanel() {
            setLayout(new BorderLayout());
            wordLabel.setFont(new Font("Arial", Font.BOLD, 48));
            wordLabel.setOpaque(true);
            wordLabel.setBackground(Color.LIGHT_GRAY);
            add(wordLabel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(matchButton);
            buttonPanel.add(noMatchButton);

            matchButton.addActionListener(e -> checkAnswer(true));
            noMatchButton.addActionListener(e -> checkAnswer(false));

            add(buttonPanel, BorderLayout.SOUTH);

            // زر العودة
            JButton backButton = new JButton("Back");
            backButton.addActionListener(e -> cardLayout.show(mainPanel, "selection"));
            add(backButton, BorderLayout.NORTH);

            startNewRound();
        }

        private void startNewRound() {
            int wordIndex = random.nextInt(words.length);
            int colorIndex = random.nextInt(colors.length);
            currentWord = words[wordIndex];
            currentColor = colors[colorIndex];

            isMatch = currentWord.equals(getColorName(currentColor));

            wordLabel.setText(currentWord);
            wordLabel.setForeground(currentColor);
        }

        private void checkAnswer(boolean userSaidMatch) {
            if (userSaidMatch == isMatch) {
                JOptionPane.showMessageDialog(this, "Correct!");
            } else {
                JOptionPane.showMessageDialog(this, "Wrong!");
            }
            startNewRound();
        }

        private String getColorName(Color color) {
            if (color.equals(Color.RED)) return "RED";
            if (color.equals(Color.GREEN)) return "GREEN";
            if (color.equals(Color.BLUE)) return "BLUE";
            return "";
        }
    }

    // لعبة الرياضيات (جمع بسيط مع وقت)
    class MathGamePanel extends JPanel {
        private JLabel problemLabel = new JLabel("Press Start", SwingConstants.CENTER);
        private JTextField answerField = new JTextField();
        private JButton startButton = new JButton("Start");
        private JLabel timerLabel = new JLabel("Time: 30", SwingConstants.CENTER);

        private int timeLeft = 30;
        private int currentAnswer;
        private Timer timer;
        private Random random = new Random();

        public MathGamePanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

            problemLabel.setFont(new Font("Arial", Font.BOLD, 36));
            problemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            answerField.setMaximumSize(new Dimension(200, 40));
            answerField.setFont(new Font("Arial", Font.PLAIN, 24));
            answerField.setAlignmentX(Component.CENTER_ALIGNMENT);
            answerField.setEnabled(false);

            timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
            timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            startButton.addActionListener(e -> startGame());

            answerField.addActionListener(e -> checkAnswer());

            // زر العودة
            JButton backButton = new JButton("Back");
            backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            backButton.addActionListener(e -> {
                if(timer != null) timer.stop();
                cardLayout.show(mainPanel, "selection");
            });

            add(problemLabel);
            add(Box.createVerticalStrut(20));
            add(answerField);
            add(Box.createVerticalStrut(10));
            add(timerLabel);
            add(Box.createVerticalStrut(20));
            add(startButton);
            add(Box.createVerticalStrut(10));
            add(backButton);
        }

        private void startGame() {
            timeLeft = 30;
            answerField.setEnabled(true);
            answerField.setText("");
            answerField.requestFocus();
            startButton.setEnabled(false);
            nextProblem();

            timerLabel.setText("Time: " + timeLeft);
            timer = new Timer(1000, e -> {
                timeLeft--;
                timerLabel.setText("Time: " + timeLeft);
                if (timeLeft <= 0) {
                    timer.stop();
                    answerField.setEnabled(false);
                    startButton.setEnabled(true);
                    problemLabel.setText("Time's up!");
                }
            });
            timer.start();
        }

        private void nextProblem() {
            int a = random.nextInt(10);
            int b = random.nextInt(10);
            currentAnswer = a + b;
            problemLabel.setText(a + " + " + b + " = ?");
        }

        private void checkAnswer() {
            try {
                int ans = Integer.parseInt(answerField.getText().trim());
                if (ans == currentAnswer) {
                    JOptionPane.showMessageDialog(this, "Correct!");
                } else {
                    JOptionPane.showMessageDialog(this, "Wrong!");
                }
                answerField.setText("");
                nextProblem();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.");
            }
        }
    }

    // لعبة الذاكرة (تكرار تسلسل كلمات)
    class MemoryGamePanel extends JPanel {
        private JLabel displayLabel = new JLabel("Press Start", SwingConstants.CENTER);
        private JButton startButton = new JButton("Start");
        private JTextField inputField = new JTextField();
        private JLabel feedbackLabel = new JLabel("", SwingConstants.CENTER);

        private String[] words = {"APPLE", "ORANGE", "BANANA", "GRAPE"};
        private java.util.List<String> sequence = new java.util.ArrayList<>();
        private int displayIndex = 0;

        public MemoryGamePanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

            displayLabel.setFont(new Font("Arial", Font.BOLD, 36));
            displayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            inputField.setMaximumSize(new Dimension(400, 40));
            inputField.setFont(new Font("Arial", Font.PLAIN, 24));
            inputField.setAlignmentX(Component.CENTER_ALIGNMENT);
            inputField.setEnabled(false);

            feedbackLabel.setFont(new Font("Arial", Font.BOLD, 18));
            feedbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            startButton.addActionListener(e -> startGame());

            inputField.addActionListener(e -> checkInput());

            // زر العودة
            JButton backButton = new JButton("Back");
            backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            backButton.addActionListener(e -> cardLayout.show(mainPanel, "selection"));

            add(displayLabel);
            add(Box.createVerticalStrut(20));
            add(startButton);
            add(Box.createVerticalStrut(20));
            add(inputField);
            add(Box.createVerticalStrut(20));
            add(feedbackLabel);
            add(Box.createVerticalStrut(10));
            add(backButton);
        }

        private void startGame() {
            sequence.clear();
            nextRound();
        }

        private void nextRound() {
            sequence.add(words[new Random().nextInt(words.length)]);
            displayIndex = 0;
            inputField.setEnabled(false);
            feedbackLabel.setText("");
            showSequence();
        }

        private void showSequence() {
            if (displayIndex < sequence.size()) {
                displayLabel.setText(sequence.get(displayIndex));
                displayIndex++;
                // عرض كل كلمة 800 مللي ثانية ثم تنتقل للكلمة التالية
                Timer timer = new Timer(800, e -> showSequence());
                timer.setRepeats(false);
                timer.start();
            } else {
                displayLabel.setText("Repeat the sequence:");
                inputField.setEnabled(true);
                inputField.requestFocus();
            }
        }

        private void checkInput() {
            String userInput = inputField.getText().trim().toUpperCase();
            String correctSequence = String.join(" ", sequence);
            if (userInput.equals(correctSequence)) {
                feedbackLabel.setText("Correct! Next round.");
                inputField.setText("");
                nextRound();
            } else {
                feedbackLabel.setText("Wrong! Try again.");
                inputField.setText("");
                inputField.requestFocus();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MindDevelopmentApp());
    }
}
