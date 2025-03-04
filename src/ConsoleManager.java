
import javax.sound.sampled.Control;
import java.lang.reflect.Type;
import java.text.ListFormat;
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
        InputRequest[] requests = {
                new InputRequest("Введите название фильма: ", String.class, s -> s, "name"),
                new InputRequest("Введите координату X: ", Float.class, s -> {
                    try {
                        return Float.parseFloat(s);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }, "x"),
                new InputRequest("Введите координату Y: ", Integer.class, s -> Integer.parseInt(s), "y")
        };
        Movie newMovie = new Movie();
        Coordinates coordinates = new Coordinates();

        for (InputRequest request : requests) {
            Object value = null;
            boolean validCommand = false;

            while (!validCommand) {
                System.out.print(request.getPrompt());
                String line = scanner.nextLine().trim();

                value = request.parseInput(line);

                switch (request.getFieldName()) {
                    case "name":
                        try {
                            newMovie.setName((String) value);
                            validCommand = true;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                        case "x":
                            try {
                                if (value != null) {
                                    coordinates.setX((Float) value);
                                    validCommand = true;
                                } else if (value == null && line.isEmpty()) {
                                    validCommand = true;
                                } else {
                                    System.out.println("Неправильный формат ввода. Координата X может принимать значения типа Float");
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                }


                }
            }






        /*try {
            System.out.print("Введите название фильма: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
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
        }*/
        return newMovie;
    }

    public Collection<Command> getCommands() {
        return commandMap.values();
    }
}

