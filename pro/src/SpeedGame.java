import java.util.Scanner;
import java.util.Random;
public class SpeedGame {

    private final String[] colors = {"red", "blue", "green", "yellow", "purple"};
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";

    private Random random;
    private Scanner scanner;

    public SpeedGame(Scanner scanner) {
        this.random = new Random();
        this.scanner = scanner;
    }

    private String getAnsiColorCode(String colorName) {
        switch(colorName.toLowerCase()) {
            case "red":
                return RED;
            case "blue":
                return BLUE;
            case "green":
                return GREEN;
            case "yellow":
                return YELLOW;
            case "purple":
                return PURPLE;
            default:
                return RESET;
        }
    }

    public void play() {
         System.out.println(" Starting Speed Game\nType 'exit' to return to main menu)\\n "  );
        while (true) {
            // Choose a random color word and a random color
            String colorWord = colors[random.nextInt(colors.length)];
            String actualColor = colors[random.nextInt(colors.length)];

            String colorCode = getAnsiColorCode(actualColor);
            System.out.print("Type the COLOR of the word shown: ");
            // Print the colorWord text colored with actualColor
            System.out.println(colorCode + colorWord + RESET);

            System.out.print("Your answer: ");

            long startTime = System.currentTimeMillis();
            String input = scanner.nextLine().trim().toLowerCase();
            long endTime = System.currentTimeMillis();

            if (input.equals("exit")) {
                System.out.println("Exiting Speed Game.\n");
                break;
            }
            double responseTime = (endTime - startTime) / 1000.0;
            if (input.equals(actualColor)) {
                System.out.printf("Correct! Response time: %.2f seconds%n%n", responseTime);
            } else {
                System.out.printf("Incorrect. The correct color was '%s'. Response time: %.2f seconds%n%n", actualColor, responseTime);
            }
        }

        }

    }

