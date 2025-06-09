import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class App extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    private JCheckBox speedBox, mathBox, memoryBox;
    private JButton startButton;

    public App() {
        setTitle("Mind Development App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setMinimumSize(new Dimension(700, 500));
        setLocationRelativeTo(null);

        mainPanel.setBackground(Color.WHITE);
        add(mainPanel);

        mainPanel.add(createSelectionPanel(), "selection");
        cardLayout.show(mainPanel, "selection");
    }

    private JPanel createSelectionPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(80, 100, 80, 100));

        JLabel title = new JLabel("Develop Your Mind");
        title.setFont(new Font("Segoe UI", Font.BOLD, 48));
        title.setForeground(Color.BLACK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Choose one or more options to start");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitle.setForeground(new Color(107, 114, 128));
        subtitle.setBorder(new EmptyBorder(20, 0, 40, 0));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel optionsPanel = new JPanel(new GridLayout(1, 3, 60, 0));
        optionsPanel.setBackground(Color.WHITE);

        speedBox = new JCheckBox("Speed");
        speedBox.setFont(new Font("Segoe UI", Font.BOLD, 26));
        speedBox.setBackground(Color.WHITE);
        speedBox.setForeground(Color.BLACK);

        mathBox = new JCheckBox("Math");
        mathBox.setFont(new Font("Segoe UI", Font.BOLD, 26));
        mathBox.setBackground(Color.WHITE);
        mathBox.setForeground(Color.BLACK);

        memoryBox = new JCheckBox("Memory");
        memoryBox.setFont(new Font("Segoe UI", Font.BOLD, 26));
        memoryBox.setBackground(Color.WHITE);
        memoryBox.setForeground(Color.BLACK);

        optionsPanel.add(speedBox);
        optionsPanel.add(mathBox);
        optionsPanel.add(memoryBox);

        startButton = new JButton("Start");
        startButton.setFont(new Font("Segoe UI", Font.BOLD, 24));
        startButton.setBackground(new Color(0, 120, 215));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Action listener لوضبط اللعبة بناء على الاختيارات هنا (تقدر تضيفها حسب كودك)
        // startButton.addActionListener(e -> { ... });

        panel.add(title);
        panel.add(subtitle);
        panel.add(optionsPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 50)));
        panel.add(startButton);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App ui = new App();
            ui.setVisible(true);
        });
    }
}

