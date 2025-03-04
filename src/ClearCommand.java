public class ClearCommand implements Command {
    private CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String argument) {
        collectionManager.clearCollection();
        System.out.println("Коллекция очищена");
    }

    @Override
    public String getDescription() {
        return "clear : очистить коллекцию";
    }
}
