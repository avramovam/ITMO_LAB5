import java.util.*;

public class ConsoleManager {
    private CollectionManager collectionManager;
    private Scanner scanner;
    private Map<String, Command> commandMap;

    public ConsoleManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.scanner = new Scanner(System.in);
        this.commandMap = new HashMap<>();

        commandMap.put("add", new AddCommand(collectionManager, this));
        commandMap.put("show", new ShowCommand(collectionManager));
        commandMap.put("remove", new RemoveCommand(collectionManager));
        commandMap.put("clear", new ClearCommand(collectionManager));
        commandMap.put("help", new HelpCommand(this));
        commandMap.put("info", new InfoCommand(collectionManager));
        commandMap.put("update", new UpdateCommand(collectionManager, this));

    }

    public void startInteractiveMode() {
        while (true) {
            System.out.print("Введите команду: ");
            String line = scanner.nextLine().trim();
            String[] parts = line.split("\\s+", 2);
            String commandName = parts[0];
            String argument = parts.length > 1 ? parts[1] : null;

            Command command = commandMap.get(commandName);
            if (command != null) {
                try {
                    command.execute(argument);
                } catch (Exception e) {
                    System.out.println("Произошла ошибка при выполнении команды: " + e.getMessage());
                }
            } else {
                System.out.println("Неизвестная команда. Введите 'help' для просмотра доступных команд.");
            }
        }
    }

    public Movie readMovieFromConsole() {
        try {
            System.out.print("Введите название фильма: ");
            String name = scanner.nextLine().trim();
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Название фильма не может быть пустым.");
            }

            System.out.print("Введите координату X: ");
            Float x = Float.parseFloat(scanner.nextLine().trim());

            System.out.print("Введите координату Y: ");
            Integer y = Integer.parseInt(scanner.nextLine().trim());

            Coordinates coordinates = new Coordinates();
            coordinates.setX(x);
            coordinates.setY(y);

            Movie newMovie = new Movie();
            newMovie.setName(name);
            newMovie.setCoordinates(coordinates);

            return newMovie;

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный формат ввода. Повторите ввод.");
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public Collection<Command> getCommands() {
        return commandMap.values();
    }

    /*
    private void addMovie() {
        try {
            System.out.print("Введите название фильма: ");
            String name = scanner.nextLine().trim();
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Название фильма не может быть пустым.");
            }

            System.out.print("Введите координату X: ");
            Float x = Float.parseFloat(scanner.nextLine().trim());

            System.out.print("Введите координату Y: ");
            Integer y = Integer.parseInt(scanner.nextLine().trim());

            Coordinates coordinates = new Coordinates();
            coordinates.setX(x);
            coordinates.setY(y);

            // ... Запрашиваем остальные поля и создаем объект Movie
            Movie newMovie = new Movie();
            newMovie.setName(name);
            newMovie.setCoordinates(coordinates);
            // Установка остальных полей для newMovie

            collectionManager.addMovie(newMovie);
            System.out.println("Фильм успешно добавлен.");

        } catch (NumberFormatException e) {
            System.out.println("Некорректный формат числа. Повторите ввод.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // Выводим сообщение об ошибке валидации
        }
    }

    private void removeMovieById(String idStr) {
        try {
            Long id = Long.parseLong(idStr);
            collectionManager.removeMovieById(id);
            System.out.println("Фильм с ID " + id + " удален.");
        } catch (NumberFormatException e) {
            System.out.println("Некорректный формат ID. Введите целое число.");
        }
    }

    private void updateMovieById(String idStr) {
        try {
            Long id = Long.parseLong(idStr);
            // Запросите у пользователя новые данные для фильма
            System.out.println("Введите новые данные для фильма с ID " + id);

            // Запросите новые значения для полей фильма, как в методе addMovie()
            // Создайте новый объект Movie с этими значениями

            Movie updatedMovie = new Movie(); // Заполните поля updatedMovie

            collectionManager.updateMovieById(id, updatedMovie);
            System.out.println("Фильм с ID " + id + " успешно обновлен.");

        } catch (NumberFormatException e) {
            System.out.println("Некорректный формат ID. Введите целое число.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }



    private void clearCollection() {
        collectionManager.clearCollection();
        System.out.println("Коллекция очищена.");
    }

    private void showHelp() {
        System.out.println("Доступные команды:");
        System.out.println("  help : вывести справку по доступным командам");
        System.out.println("  info : вывести информацию о коллекции");
        System.out.println("  show : вывести все элементы коллекции");
        System.out.println("  add : добавить новый элемент в коллекцию");
        System.out.println("  remove_by_id {id} : удалить элемент с указанным ID");
        System.out.println("  update_id {id} : обновить элемент с указанным ID");
        System.out.println("  clear : очистить коллекцию");
        System.out.println("  exit : завершить программу");
        // ...
    }

    private void showInfo() {
        System.out.println("Тип коллекции: PriorityQueue");
        System.out.println("Количество элементов: " + collectionManager.getMovieCollection().size());
    }

    private void showCollection() {
        PriorityQueue<Movie> movies = collectionManager.getMovieCollection();
        if (movies.isEmpty()) {
            System.out.println("Коллекция пуста.");
            return;
        }
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
     */
}

