package commands;

import app.CollectionManager;

public class SaveCommand implements Command {
    CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String argument) {
        collectionManager.saveDataToFile("-");
    }

    @Override
    public String getDescription() {
        return "save : сохранить коллекцию в файл";
    }
}
