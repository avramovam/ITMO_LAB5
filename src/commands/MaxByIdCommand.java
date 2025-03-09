package commands;

import app.CollectionManager;
import modules.Movie;

public class MaxByIdCommand implements Command {
    private CollectionManager collectionManager;

    public MaxByIdCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String argument) {
        Movie maxMovie = collectionManager.findMaxByID();

        if (maxMovie != null) {
            System.out.println("Фильм с наибольшим ID " + maxMovie.getId() + ": "+ maxMovie);
        } else {
            System.out.println("Коллекция пуста.");
        }
    }

    @Override
    public String getDescription() {
        return "max_by_id : вывести любой объект из коллекции, значение поля id которого является максимальным";
    }
}
