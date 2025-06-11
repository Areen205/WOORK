import java.util.ArrayList;
import java.util.Scanner;
public class GameManager {
    Scanner scanner = new Scanner(System.in);
    public void startGames(ArrayList<String> choices) {
        for (String choice : choices) {
            if (choice.equals("1")) {
                SpeedGame speedGame = new SpeedGame(scanner);
                speedGame.play();
            }
            if (choice.equals("4")) {
              ChromeDinosaur  chromedinosaur = new ChromeDinosaur();
                chromedinosaur.startGame();

            }
        }
    }

    private void printInstructions(ArrayList<String> choices) {
        System.out.println("\n=== Instructions ===");
        for (String choice : choices) {
            switch (choice) {
                case "Speed":
                    System.out.println("- Speed Game: Respond quickly if the word matches the color.");
                    break;
                case "Math":
                    System.out.println("- Math Game: Solve simple math problems as quickly as you can.");
                    break;
                case "Memory":
                    System.out.println("- Memory Game: Remember a sequence of words and repeat them.");
                    break;
                case "Fun" :
                    System.out.println("- Fun Game: Chrome Dinosaur press space to make the dinosaur jump.");
            }
        }
        System.out.println("=====================\n");
    }



}
//