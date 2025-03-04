public class ShowCommand implements Command {
    private CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String argument) {
        collectionManager.showCollection();
    }

    @Override
    public String getDescription() {
        return "show : вывести все элементы коллекции";
    }
}
