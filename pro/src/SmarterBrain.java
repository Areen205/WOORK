import java.util.ArrayList;
import java.util.Scanner;
public class SmarterBrain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> choices = new ArrayList<>();

        System.out.println("Welcome to Smarter Brain");
        System.out.println("Please select the skills you want to improve ");
        System.out.println("1. Speed");
        System.out.println("2. Math");
        System.out.println("3. Memory");
        System.out.println("You can select multiple options separated by commas (e.g., 1,2)");

        System.out.println("Enter your choice: ");
        String input = sc.nextLine();
        String[] inputchoices = input.split(",");

        for (String choice : inputchoices) {
            choice = choice.trim();
            if (choice.equals("1")) {
                choices.add("Speed");
            }
            else if (choice.equals("2")) {
                choices.add("Math");
            }
            else if (choice.equals("3")) {
                choices.add("Memory");
            }
        }
        if (choices.isEmpty()) {
            System.out.println("No choices selected");
        }

    }
}
