import java.util.Scanner;

public class SimpleChatbot {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("🤖 Chatbot: Hello! I’m your virtual assistant. Type 'exit' to end the chat.");

        while (true) {
            System.out.print("You: ");
            String input = scanner.nextLine().toLowerCase();

            if (input.equals("exit")) {
                System.out.println("🤖 Chatbot: Goodbye! Have a nice day.");
                break;
            }

            // Rule-based responses
            if (input.contains("hi") || input.contains("hello")) {
                System.out.println("🤖 Chatbot: Hello! How can I help you today?");
            } else if (input.contains("how are you")) {
                System.out.println("🤖 Chatbot: I'm just a program, but I'm functioning as expected!");
            } else if (input.contains("your name")) {
                System.out.println("🤖 Chatbot: I’m a simple Java chatbot.");
            } else if (input.contains("what can you do")) {
                System.out.println("🤖 Chatbot: I can respond to basic greetings and questions!");
            } else if (input.contains("bye")) {
                System.out.println("🤖 Chatbot: Bye! Take care.");
                break;
            } else {
                System.out.println("🤖 Chatbot: Sorry, I didn't understand that.");
            }
        }

        scanner.close();
    }
}
