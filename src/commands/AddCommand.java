package commands;

import app.CollectionManager;
import app.ConsoleManager;
import modules.Movie;

public class AddCommand implements Command {
    private CollectionManager collectionManager;
    private ConsoleManager consoleManager;
    private String[] movieData;

    public AddCommand(CollectionManager collectionManager, ConsoleManager consoleManager) {
        this.collectionManager = collectionManager;
        this.consoleManager = consoleManager;
    }
    @Override
    public void execute(String argument) {;
        try {
            Movie newMovie;
            if (movieData != null && movieData.length > 0) {
                newMovie = consoleManager.readMovieFromArguments(movieData);
            } else {
                newMovie = consoleManager.readMovieFromConsole();
            }
            collectionManager.addMovie(newMovie, false);
            System.out.println("Фильм успешно добавлен.");
        } catch (IllegalArgumentException e) {
            System.out.println("Содержится ошибка в веденных данных.");
        }
    }

    @Override
    public String getDescription() {
        return "add {element} : добавить новый элемент в коллекцию";
    }

    public void setMovieData(String[] movieData) {
        this.movieData = movieData;
    }
}
