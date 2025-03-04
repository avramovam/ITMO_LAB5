public class RemoveCommand implements Command {
    private CollectionManager collectionManager;

    public RemoveCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String argument) {
        try {
            if (argument == null || argument.isEmpty()) {
                System.out.println("Необходимо указать ID фильма для удаления.");
                return;
            }
            Long id = Long.parseLong(argument);
            collectionManager.removeMovieById(id);
        } catch (NumberFormatException e) {
            System.out.println("Некорректный формат ввода ID. Введите целое число");
        }
    }

    @Override
    public String getDescription() {
        return "remove_by_id {id} : удалить элемент с указанным ID";
    }
}
