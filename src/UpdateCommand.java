public class UpdateCommand implements Command {
    private CollectionManager collectionManager;
    private ConsoleManager consoleManager;

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
            if (!collectionManager.getMovieCollection().removeIf(movie -> movie.getId().equals(id))) {
                System.out.println("Фильм с таким ID не найден.");
            } else {;
                Movie newMovie = consoleManager.readMovieFromConsole();
                collectionManager.updateMovieById(id, newMovie);
                System.out.println("Фильм с ID " + id + " обновлен");
            }
        } catch (NumberFormatException e) {
            System.out.println("Некорректный формат ввода ID. Введите целое число");
        }
    }

    @Override
    public String getDescription() {
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }
}


