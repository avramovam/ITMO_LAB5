package app;

import commands.*;
import modules.*;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.time.LocalDate;

public class ConsoleManager {
    private CollectionManager collectionManager;
    private Scanner scanner;
    private final Map<String, Command> commandMap;
    private final Pattern intPattern;
    private final Pattern floatPattern;
    private boolean isEmergencyShutdown;


    public ConsoleManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.scanner = new Scanner(System.in);
        this.commandMap = new LinkedHashMap<>();
        this.intPattern = Pattern.compile("-?\\d+");
        this.floatPattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        this.isEmergencyShutdown = new File("src/app/emergencies.csv").length() != 0;



        commandMap.put("add", new AddCommand(collectionManager, this));
        commandMap.put("show", new ShowCommand(collectionManager));
        commandMap.put("remove_by_id", new RemoveCommand(collectionManager));
        commandMap.put("clear", new ClearCommand(collectionManager));
        commandMap.put("help", new HelpCommand(this));
        commandMap.put("info", new InfoCommand(collectionManager));
        commandMap.put("update", new UpdateCommand(collectionManager, this));
        commandMap.put("save", new SaveCommand(collectionManager));
        commandMap.put("exit", new ExitCommand(collectionManager, scanner));
        commandMap.put("remove_head", new RemoveHeadCommand(collectionManager));
        commandMap.put("min_by_coordinates", new MinByCoordinatesCommand(collectionManager));
        commandMap.put("max_by_id", new MaxByIdCommand(collectionManager));
        commandMap.put("add_if_min", new AddIfMinCommand(collectionManager, this));
        commandMap.put("remove_greater", new RemoveGreaterCommand(this, collectionManager));
        commandMap.put("count_greater_than_genre", new CountGreaterThanGenreCommand(collectionManager));
        commandMap.put("execute_script", new ExecuteScriptCommand(this, collectionManager));

    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void emergencyShutdown() {
        System.out.println("Аварийное завершение работы.");
        collectionManager.saveDataToFile("+");
    }
    public void startInteractiveMode() {
        if (isEmergencyShutdown) {
            System.out.print("Хотите продолжить работу с несохраненными данными? (+/-)" );
            String line = scanner.nextLine().trim();
            if (line.equals("+")) {
                collectionManager.loadDataFromFile("+");
                System.out.println("Восстановленная коллекция: ");
                collectionManager.showCollection();
                try (FileWriter _ = new FileWriter("src/app/emergencies.csv", false)){}
                catch (IOException e) {
                    System.out.println("Ошибка при очистке файла " + e.getMessage());
                }
            } else {
                collectionManager.loadDataFromFile("-");
            }
        } else {
            collectionManager.loadDataFromFile("-");
        }
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

    public void processCommand(String line, String[] data) {
        String[] parts = line.split("\\s+", 2);
        String commandName = parts[0];
        String argument = parts.length > 1 ? parts[1] : null;

        Command command = commandMap.get(commandName);
        if (command != null) {
            try {
                if (command instanceof AddCommand addCommand) {
                    if (data != null && data.length > 0) {
                        addCommand.setMovieData(data);
                    }
                }
                if (command instanceof UpdateCommand updateCommand) {
                    if (data != null && data.length > 0) {
                        updateCommand.setMovieData(data);
                    }
                }
                if (command instanceof AddIfMinCommand addIfMinCommand) {
                    if (data != null && data.length > 0) {
                        addIfMinCommand.setMovieData(data);
                    }
                }
                if (command instanceof RemoveGreaterCommand removeGreaterCommand) {
                    if (data != null && data.length > 0) {
                        removeGreaterCommand.setMovieData(data);
                    }
                }
                if (command instanceof UpdateCommand updateCommand) {
                    if (data != null && data.length > 0) {
                        updateCommand.setMovieData(data);
                    }
                }
                command.execute(argument);
            } catch (Exception e) {
                System.out.println("Произошла ошибка при выполнении команды: " + e.getMessage());
            }
        } else {
            System.out.println("Неизвестная команда: " + commandName);
        }
    }

    public Movie readMovieFromArguments(String[] values) {
        /*try {
            Movie movie = new Movie();
            Person director = new Person();
            Location directorLocation = new Location();
            Coordinates coordinates = new Coordinates();

            movie.setName(values[0].trim());
            coordinates.setX(Float.parseFloat(values[1].trim()));
            coordinates.setY(Integer.parseInt(values[2].trim()));
            movie.setCoordinates(coordinates);
            movie.setCreationDate(LocalDate.now());
            movie.setOscarsCount(Integer.parseInt(values[3].trim()));
            movie.setLength(Long.parseLong(values[4].trim()));
            movie.setGenre(MovieGenre.valueOf(values[5].trim().toUpperCase()));
            try{movie.setMpaaRating(MpaaRating.valueOf(values[6].trim().toUpperCase()));} catch (Exception _) {};
            try{
                director.setName(values[9]);
                director.setPassportID(values[10]);
                directorLocation.setX(Integer.parseInt(values[11]));
                directorLocation.setY(Long.parseLong(values[12]));
                director.setName(values[13]);
                director.setLocation(directorLocation);
                movie.setDirector(director);} catch (Exception _) {};

            return movie;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный формат числа в файле.");
        } catch (IllegalArgumentException e) {
            throw e;
        }*/

        try {
            Movie newMovie = new Movie();
            Person director = new Person();
            Location directorLocation = new Location();
            Coordinates coordinates = new Coordinates();
            newMovie.setCreationDate(LocalDate.now());
            Boolean validCommand = true;

            newMovie.setName(values[0].trim());
            if (readCoordinates(values[1].trim(), "X")) {
                coordinates.setX(Float.parseFloat(values[1].trim()));
            } else {
                validCommand = false;
            }
            if (readCoordinates(values[2].trim(), "Y")) {
                coordinates.setY(Integer.parseInt(values[2].trim()));
                newMovie.setCoordinates(coordinates);
            } else {
                validCommand = false;
            }
            if (readOscarAndLength(values[3].trim())) {
                newMovie.setOscarsCount(Integer.parseInt(values[3].trim()));
            } else {
                validCommand = false;
            }
            if (readOscarAndLength(values[4].trim())) {
                newMovie.setLength(Long.parseLong(values[4].trim()));
            } else {
                validCommand = false;
            }
            if (readEnum(values[5].trim(), "Genre")) {
                newMovie.setGenre(MovieGenre.valueOf(values[5].trim().toUpperCase()));
            } else {
                validCommand = false;
            }
            if (values.length >= 7 && readEnum(values[6].trim(), "modules.MpaaRating")) {
                if (!values[6].trim().isEmpty()) {
                    newMovie.setMpaaRating(MpaaRating.valueOf(values[6].trim().toUpperCase()));
                }
            }

            if (values.length >= 12){
                if (values[7].trim().isEmpty()) {
                    director.setName(values[8].trim());
                    if (readDirectorID(values[9].trim())) {
                        director.setPassportID(values[9].trim());
                        if (readCoordinates(values[10].trim(), "x")) {
                            directorLocation.setX(Integer.parseInt(values[10].trim()));
                            if (readCoordinates(values[11].trim(), "Y")) {
                                directorLocation.setY(Long.parseLong(values[11].trim()));
                                if (values.length == 13 && !(values[12].trim().isEmpty())) {
                                    directorLocation.setName(values[12].trim());
                                }
                                director.setLocation(directorLocation);
                                newMovie.setDirector(director);
                                return newMovie;
                            }
                        }
                    }
                } else {
                    System.out.println("Команда для ввода данных о режиссера не распознана.");
                }
            } else if (values.length >= 8 && !values[7].trim().isEmpty()) {
                System.out.println("Команда для ввода данных режиссера не распознана, а также недостаточно аргументов");
            } else if (values.length >= 8 && values[7].trim().isEmpty()) {
                System.out.println("Недостаточно аргументов для ввода данных о режиссере.");
            }
            if (validCommand) {
                return newMovie;
            } else {
                throw new IllegalArgumentException("Ошибка в данных файла.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
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
                                coordinates.setX(Float.parseFloat(line));
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
                                newMovie.setOscarsCount(Integer.parseInt(line));
                                validCommand = true;
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;

                    case "length":
                        if (readOscarAndLength(line)) {
                            try {
                                newMovie.setLength(Long.parseLong(line));
                                validCommand = true;
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;

                    case "genre":
                        if (readEnum(line, "Genre")) {
                            try {
                                newMovie.setGenre(MovieGenre.valueOf(line.toUpperCase()));
                                validCommand = true;
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;

                    case "rating":
                        if (readEnum(line, "modules.MpaaRating")) {
                            try {
                                if (!line.isEmpty()) {
                                    newMovie.setMpaaRating(MpaaRating.valueOf(line.toUpperCase()));
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
                if (Objects.equals(nameEnum, "modules.MpaaRating")) {
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
                } else if (Objects.equals(nameEnum, "modules.MpaaRating")){
                    MpaaRating rating = MpaaRating.valueOf(input);
                    return true;
                } else {
                    System.out.println("Enum класс не найден");
                    return false;
                }
            }
        } catch (IllegalArgumentException e) {
            if (Objects.equals(nameEnum, "Genre")) {
                System.out.println("Неправильный формат ввода жанра. Введенное значение должно быть из списка");
            } else {
                System.out.println("Неправильный формат ввода рейтинга. Введенное значение должно быть из списка");
            }
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

