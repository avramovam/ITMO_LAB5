
import java.util.*;
import java.util.regex.Pattern;
import java.time.LocalDate;

public class ConsoleManager {
    private CollectionManager collectionManager;
    private Scanner scanner;
    private Map<String, Command> commandMap;
    private final Pattern intPattern;
    private final Pattern floatPattern;


    public ConsoleManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.scanner = new Scanner(System.in);
        this.commandMap = new LinkedHashMap<>();
        this.intPattern = Pattern.compile("-?\\d+");
        this.floatPattern = Pattern.compile("-?\\d+(\\.\\d+)?");


        commandMap.put("add", new AddCommand(collectionManager, this));
        commandMap.put("show", new ShowCommand(collectionManager));
        commandMap.put("remove", new RemoveCommand(collectionManager));
        commandMap.put("clear", new ClearCommand(collectionManager));
        commandMap.put("help", new HelpCommand(this));
        commandMap.put("info", new InfoCommand(collectionManager));
        commandMap.put("update", new UpdateCommand(collectionManager, this));
        commandMap.put("save", new SaveCommand(collectionManager));
        commandMap.put("exit", new ExitCommand());


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
        Map<String, String> requestMap = new LinkedHashMap<>();
        requestMap.put("Введите название фильма: ", "movieName");
        requestMap.put("Введите координату X: ", "X");
        requestMap.put("Введите координату Y: ", "Y");
        requestMap.put("Введите количество Оскаров: ", "oskarCount");
        requestMap.put("Введите длину: ", "length");
        requestMap.put("Введите жанр (DRAMA, COMEDY, ADVENTURE, HORROR, FANTASY): ", "genre");
        requestMap.put("Введите рейтинг (G, R, NC_17): ", "rating");
        requestMap.put("Введите данные о режиссере (нажмите enter для ввода данных, введите '-' для отказа от ввода):", "director");
        requestMap.put("Введите имя режиссера: ", "directorName");
        requestMap.put("Введите passportID режиссера: ", "directorID");;
        requestMap.put("Введите координату X для локации: ", "directorLocationX");
        requestMap.put("Введите координату Y для локации: ", "directorLocationY");
        requestMap.put("Введите название локации: ", "locationName");


        Movie newMovie = new Movie();
        Coordinates coordinates = new Coordinates();
        Person director = new Person();
        Location location = new Location();
        newMovie.setCreationDate(LocalDate.now());

        for (String request : requestMap.keySet()) {
            boolean validCommand = false;

            while (!validCommand) {
                System.out.print(request);
                String line = scanner.nextLine().trim();

                switch (requestMap.get(request)) {
                    case "movieName":
                        try {
                            newMovie.setName(line);
                            validCommand = true;
                            System.out.println("Введите координаты");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case "X":
                        if (readCoordinates(line, "X")) {
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

                    case "Y":
                        if (readCoordinates(line, "Y")) {
                            try {
                                coordinates.setY(Integer.parseInt(line));
                                newMovie.setCoordinates(coordinates);
                                validCommand = true;
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;

                    case "oskarCount":
                        if (readOscarAndLength(line)) {
                            try {
                                if (!line.isEmpty()) {
                                    newMovie.setOscarsCount(Integer.parseInt(line));
                                }
                                validCommand = true;
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;

                    case "length":
                        if (readOscarAndLength(line)) {
                            try {
                                if (!line.isEmpty()) {
                                    newMovie.setLength(Long.parseLong(line));
                                }
                                validCommand = true;
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;

                    case "genre":
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

                    case "rating":
                        if (readEnum(line, "MpaaRating")) {
                            try {
                                if (!line.isEmpty()) {
                                    line = line.toUpperCase();
                                    newMovie.setMpaaRating(MpaaRating.valueOf(line));
                                }
                                validCommand = true;
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;

                    case "director":
                        if (line.isEmpty()) {
                            validCommand = true;
                        } else if (line.equals("-")) {
                            return newMovie;
                        } else {
                            System.out.println("Команда не распознана. Повторите ввод.");
                        }
                        break;
                    case "directorName":
                        try {
                            director.setName(line);
                            validCommand = true;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case "directorID":
                        if (readDirectorID(line)) {
                            try {
                                director.setPassportID(line);
                                validCommand = true;
                                System.out.println("Введите локацию режиссера.");
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;

                    case "directorLocationX":
                        if (readCoordinates(line, "x")) {
                            location.setX(Integer.parseInt(line));
                            validCommand = true;
                        }
                        break;

                    case "directorLocationY":
                        if (readCoordinates(line, "Y")) {
                            location.setY(Long.parseLong(line));
                            validCommand = true;
                        }
                        break;

                    case "locationName":
                        if (!line.isEmpty()) {
                            location.setName(line);
                        }
                        validCommand = true;
                        director.setLocation(location);
                        newMovie.setDirector(director);
                        break;
                }
            }
        }
        return newMovie;
    }

    public Boolean readCoordinates(String input, String arg) {
        Pattern pattern = null;
        if (arg.equals("X")) {
            pattern = floatPattern;
        } else if (arg.equals("Y") || arg.equals("x")) {
            pattern = intPattern;
        }

        arg = arg.toUpperCase();
        assert pattern != null;
        if (pattern.matcher(input).matches()) {
            return true;
        } else if (input.isEmpty()) {
            System.out.println(arg + " координата не может быть пустой");
            return false;
        } else {
            System.out.println("Неправильный формат ввода. Координата " + arg + " не может быть строкой.");
            return false;
        }
    }


    public Boolean readOscarAndLength(String input) {
        if (intPattern.matcher(input).matches()) {
            return true;
        } else if (input.isEmpty()) {
            System.out.println("Неправильный формат ввода. Поле не может быть пустым.");
            return false;
        } else {
            System.out.println("Неправильный формат ввода. Поле не может быть строкой.");
            return false;
        }
    }


    public Boolean readEnum(String input, String nameEnum) {
        try {
            if (input.isEmpty()) {
                if (Objects.equals(nameEnum, "MpaaRating")) {
                    return true;
                }
                else {
                    System.out.println("Неправильный формат ввода. Поле не может быть пустым");
                    return false;
                }

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

    public Boolean readDirectorID(String input) {
        if (!collectionManager.getListPassportID().contains(input)) {
            collectionManager.getListPassportID().add(input);
            return true;
        } else {
            System.out.println("Такой ID уже существует. Повторите ввод.");
            return false;
        }
    }

    public Collection<Command> getCommands() {
        return commandMap.values();
    }
}

