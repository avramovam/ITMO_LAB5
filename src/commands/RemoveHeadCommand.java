package commands;

import app.CollectionManager;

public class RemoveHeadCommand implements Command {
    private CollectionManager collectionManager;

    public RemoveHeadCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String argument) {
        try {
            String removedMovie = collectionManager.removeHead().getName();
            System.out.println("Удален первый элемент коллекции под названием '" + removedMovie + "'");
        } catch (Exception e){
            System.out.println("Коллекция пуста, удалять нечего.");
        }
    }

    @Override
    public String getDescription() {
        return "remove_head : вывести первый элемент коллекции и удалить его";
    }
}
