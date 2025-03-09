package commands;

import app.CollectionManager;
import app.ConsoleManager;
import modules.Movie;

public class RemoveGreaterCommand implements Command {
    private CollectionManager collectionManager;
    private ConsoleManager consoleManager;

    public RemoveGreaterCommand(ConsoleManager consoleManager, CollectionManager collectionManager) {
        this.consoleManager = consoleManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String argument) {
        try {
            Movie movieToCompare = consoleManager.readMovieFromConsole();
            int initialSize = collectionManager.getSize();

            collectionManager.removeGreater(movieToCompare);
            int newSize = collectionManager.getSize();
            System.out.println("Удалено " + (initialSize - newSize) + " элементов.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный";
    }
}
