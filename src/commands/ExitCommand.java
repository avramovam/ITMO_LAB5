package commands;

import app.CollectionManager;

import java.util.Scanner;

public class ExitCommand implements Command {
    private CollectionManager collectionManager;
    private Scanner scanner;

    public ExitCommand(CollectionManager collectionManager, Scanner scanner) {
        this.collectionManager = collectionManager;
        this.scanner = scanner;
    }

    @Override
    public void execute(String argument) {
        if (collectionManager.isCollectionModified()) {
            System.out.print("Хотите сохранить данные перед выходом?(+/-) ");
            String command = scanner.nextLine().trim();
            if (command.equals("+")) {
                collectionManager.saveDataToFile("-");
            }
        }

        System.out.println("Завершение работы...");
        System.exit(0);
    }

    @Override
    public String getDescription() {
        return "exit : завершить программу (без сохранения в файл)";
    }
}
