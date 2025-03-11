package commands;

import app.CollectionManager;
import app.ConsoleManager;
import modules.Movie;

public class UpdateCommand implements Command {
    private CollectionManager collectionManager;
    private ConsoleManager consoleManager;
    private String[] movieData;

    public UpdateCommand(CollectionManager collectionManager , ConsoleManager consoleManager) {
        this.collectionManager = collectionManager;
        this.consoleManager = consoleManager;
    }

    @Override
    public void execute(String argument) {
        try {
            if (argument == null || argument.isEmpty()) {
                System.out.println("Необходимо указать ID фильма для обновления.");
                return;
            }
            Long id = Long.parseLong(argument);
            if (collectionManager.getMovieCollection().removeIf(movie -> movie.getId().equals(id))) {
                Movie newMovie;
                if (movieData != null && movieData.length > 0) {
                    newMovie = consoleManager.readMovieFromArguments(movieData);
                } else {
                    newMovie = consoleManager.readMovieFromConsole();
                }
                collectionManager.updateMovieById(id, newMovie);
                System.out.println("Фильм с ID " + id + " обновлен");
            } else {;
                System.out.println("Фильм с таким ID не найден.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Некорректный формат ввода ID. Введите целое число");
        }
    }

    @Override
    public String getDescription() {
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }

    public void setMovieData(String[] movieData) {
        this.movieData = movieData;
    }
}


