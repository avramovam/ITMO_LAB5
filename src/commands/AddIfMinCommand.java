package commands;

import app.CollectionManager;
import app.ConsoleManager;
import modules.Movie;

public class AddIfMinCommand implements Command {
    private CollectionManager collectionManager;
    private ConsoleManager consoleManager;
    private String[] movieData;

    public AddIfMinCommand(CollectionManager collectionManager, ConsoleManager consoleManager) {
        this.consoleManager = consoleManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String argument) {
        try {
            Movie newMovie;
            if (movieData != null && movieData.length > 0) {
                newMovie = consoleManager.readMovieFromArguments(movieData);
            } else {
                newMovie = consoleManager.readMovieFromConsole();
            }

            if (collectionManager.isMovieMinimal(newMovie)) {
                collectionManager.addMovie(newMovie, false);
                System.out.println("Фильм успешно добавлен, так как название самое короткое.");
            } else {
                System.out.println("Фильм не был добавлен, так как название не является самым коротким.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }

    public void setMovieData(String[] movieData) {
        this.movieData = movieData;
    }
}
