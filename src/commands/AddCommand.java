package commands;

import app.CollectionManager;
import app.ConsoleManager;
import modules.Movie;

public class AddCommand implements Command {
    private CollectionManager collectionManager;
    private ConsoleManager consoleManager;

    public AddCommand(CollectionManager collectionManager, ConsoleManager consoleManager) {
        this.collectionManager = collectionManager;
        this.consoleManager = consoleManager;
    }
    @Override
    public void execute(String argument) {;
        try {
            Movie newMovie = consoleManager.readMovieFromConsole();
            collectionManager.addMovie(newMovie, false);
            System.out.println("Фильм успешно добавлен.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "add {element} : добавить новый элемент в коллекцию";
    }
}
