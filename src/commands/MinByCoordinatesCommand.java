package commands;

import app.CollectionManager;
import modules.Movie;

public class MinByCoordinatesCommand implements Command {
    private CollectionManager collectionManager;

    public MinByCoordinatesCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String argument) {
        Movie minMovie = collectionManager.findMinCoordinates();

        if (minMovie != null) {
            System.out.println("Фильм с наименьшими координатами (" + minMovie.getCoordinates() + ") найден: " + minMovie.toString());
        } else {
            System.out.println("Коллекция пуста.");
        }
    }

    @Override
    public String getDescription() {
        return "min_by_coordinates : вывести любой объект из коллекции, значение поля coordinates которого является минимальным";
    }
}
