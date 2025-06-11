import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SmarterBrain {
    private JFrame frame;
    private JRadioButton speedRadioButton;
    private JRadioButton mathRadioButton;
    private JRadioButton memoryRadioButton;
    private JRadioButton funRadioButton;
    private ButtonGroup group;
    private JButton submitButton;

    public SmarterBrain() {
        // Create the frame
        frame = new JFrame("Smarter Brain - Choose One Skill");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLayout(new BorderLayout(10, 10));

        // Label at the top
        JLabel label = new JLabel("Please select the skill you want to improve:");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 16f));
        frame.add(label, BorderLayout.NORTH);

        // Radio buttons panel (choices under the label)
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
        radioPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        speedRadioButton = new JRadioButton("Speed");
        mathRadioButton = new JRadioButton("Math");
        memoryRadioButton = new JRadioButton("Memory");
        funRadioButton = new JRadioButton("Fun");

        // Group radio buttons to allow only one selection
        group = new ButtonGroup();
        group.add(speedRadioButton);
        group.add(mathRadioButton);
        group.add(memoryRadioButton);
        group.add(funRadioButton);

        // Add radio buttons to panel with spacing
        radioPanel.add(speedRadioButton);
        radioPanel.add(Box.createVerticalStrut(10));
        radioPanel.add(mathRadioButton);
        radioPanel.add(Box.createVerticalStrut(10));
        radioPanel.add(memoryRadioButton);
        radioPanel.add(Box.createVerticalStrut(10));
        radioPanel.add(funRadioButton);

        frame.add(radioPanel, BorderLayout.CENTER);

        // Submit button panel (centered below choices)
        JPanel buttonPanel = new JPanel();
        submitButton = new JButton("Submit");
        buttonPanel.add(submitButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Add action listener to submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });

        // Finalize frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void handleSubmit() {
        String selectedSkill = null;
        if (speedRadioButton.isSelected()) {
            selectedSkill = "Speed";
        } else if (mathRadioButton.isSelected()) {
            selectedSkill = "Math";
        } else if (memoryRadioButton.isSelected()) {
            selectedSkill = "Memory";
        } else if (funRadioButton.isSelected()) {
            selectedSkill = "Fun";
        }

        if (selectedSkill == null) {
            JOptionPane.showMessageDialog(frame, "Please select a skill!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            // Close the selection window
            frame.dispose();

            // Start the game using GameManager
            GameManager gameManager = new GameManager();
            ArrayList<String> choices = new ArrayList<>();
            choices.add(selectedSkill);
            gameManager.startGames(choices);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SmarterBrain::new);
    }
}


