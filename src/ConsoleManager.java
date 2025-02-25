import java.util.Scanner;

public class ConsoleManager {
    private CollectionManager collectionManager;
    private Scanner scanner;

    public ConsoleManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.scanner = new Scanner(System.in);
    }

    public void startInteractiveMode() {
        while (true) {
            System.out.println("Enter command: ");
            String command = scanner.nextLine().trim();

            try {
                processCommand(command);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Произошла ошибка: " + e.getMessage());
            }

        }
    }

    public void processCommand(String command) {
        String[] parts = command.split("\\s+", 2); // Разделяем команду на имя и аргумент (если есть)
        String commandName = parts[0];
        String argument = parts.length > 1 ? parts[1] : null;
        switch (commandName) {
            case "add":
                return;
        }


    }
}

