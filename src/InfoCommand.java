public class InfoCommand implements Command {
    private CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String argument) {
        System.out.println("Информация о коллекции:");
        System.out.println("  Тип коллекции: " + collectionManager.getCollectionType());
        System.out.println("  Количество элементов: " + collectionManager.getSize());
        System.out.println("  Дата инициализации: " + collectionManager.getInitializationDate());
    }

    @Override
    public String getDescription() {
        return "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
}
