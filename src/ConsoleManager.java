
import java.util.*;
import java.util.regex.Pattern;
import java.time.LocalDate;

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
        Map<String, Integer> requestMap = new LinkedHashMap<>();
        requestMap.put("Введите название фильма: ", 1);
        requestMap.put("Введите координату X: ", 2);
        requestMap.put("Введите координату Y: ", 3);
        requestMap.put("Введите количество Оскаров: ", 4);
        requestMap.put("Введите длину: ", 5);
        requestMap.put("Введите жанр (DRAMA, COMEDY, ADVENTURE, HORROR, FANTASY): ", 6);
        requestMap.put("Введите рейтинг (G, R, NC_17): ", 7);


        Movie newMovie = new Movie();
        Coordinates coordinates = new Coordinates();

        newMovie.setCreationDate(LocalDate.now());

        for (String request : requestMap.keySet()) {
            boolean validCommand = false;

            while (!validCommand) {
                System.out.print(request);
                String line = scanner.nextLine().trim();

                switch (requestMap.get(request)) {
                    case 1:
                        try {
                            newMovie.setName(line);
                            validCommand = true;
                            System.out.println("Введите координаты");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 2:
                        if (readX(line)) {
                            try {
                                if (!line.isEmpty()) {
                                    coordinates.setX(Float.parseFloat(line));
                                }
                                validCommand = true;
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;

                    case 3:
                        if (readY(line)) {
                            try {
                                coordinates.setY(Integer.parseInt(line));
                                newMovie.setCoordinates(coordinates);
                                validCommand = true;
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;

                    case 4:
                        if (readOscar(line)) {
                            try {
                                newMovie.setOscarsCount(Integer.parseInt(line));
                                validCommand = true;
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;

                    case 5:
                        if (readLength(line)) {
                            try {
                                newMovie.setLength(Long.parseLong(line));
                                validCommand = true;
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;

                    case 6:
                        if (readEnum(line, "Genre")) {
                            try {
                                line = line.toUpperCase();
                                newMovie.setGenre(MovieGenre.valueOf(line));
                                validCommand = true;
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;

                    case 7:
                        if (readEnum(line, "MpaaRating")) {
                            try {
                                line = line.toUpperCase();
                                newMovie.setMpaaRating(MpaaRating.valueOf(line));
                                validCommand = true;
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                }
            }
        }
        return newMovie;
    }

    public Boolean readX(String input) {
        Pattern floatPattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (floatPattern.matcher(input).matches()) {
            return true;
        } else if (input.isEmpty()) {
            return true;
        } else {
            System.out.println("Неправильный формат ввода. Координата X может принимать значения типа Float");
            return false;
        }
    }


    public Boolean readY(String input) {
        Pattern intPattern = Pattern.compile("-?\\d+");
        if (intPattern.matcher(input).matches()) {
            return true;
        } else if (input.isEmpty()) {
            System.out.println("Y координата не может быть пустой");
            return false;
        } else {
            System.out.println("Неправильный формат ввода. Y координата принимает значения типа Integer");
            return false;
        }
    }

    public Boolean readOscar(String input) {
        Pattern intPattern = Pattern.compile("-?\\d+");
        if (intPattern.matcher(input).matches()) {
            return true;
        } else if (input.isEmpty()) {
            return true;
        } else {
            System.out.println("Неправильный формат ввода. Количество оскаров должно быть числом типа Integer");
            return false;
        }
    }

    public Boolean readLength(String input) {
        Pattern intPattern = Pattern.compile("-?\\d+");
        if (intPattern.matcher(input).matches()) {
            return true;
        } else if (input.isEmpty()) {
            return true;
        } else {
            System.out.println("Неправильный формат ввода. Длина должна быть числом типа Long");
            return false;
        }
    }

    public Boolean readEnum(String input, String nameEnum) {
        try {
            if (input.isEmpty()) {
                System.out.println("Неправильный формат ввода. Поле не может быть пустым");
                return false;
            } else {
                input = input.toUpperCase();
                if (Objects.equals(nameEnum, "Genre")){
                    MovieGenre genre = MovieGenre.valueOf(input);
                    return true;
                } else if (Objects.equals(nameEnum, "MpaaRating")){
                    MpaaRating rating = MpaaRating.valueOf(input);
                    return true;
                } else {
                    System.out.println("Enum класс не найден");
                    return false;
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Неправильный формат ввода. Введите значение из списка");
            return false;
        }
    }


    public Collection<Command> getCommands() {
        return commandMap.values();
    }
}

