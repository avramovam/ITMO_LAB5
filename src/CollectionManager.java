import com.sun.jdi.Value;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.PriorityQueue;

public class CollectionManager {
    private PriorityQueue<Movie> movieCollection;
    private Long nextId = 1L;
    private Date initializationDate;
    private ArrayList<String> directorsID;

    public CollectionManager() {
        movieCollection = new PriorityQueue<>();
        this.initializationDate = new Date();
        directorsID = new ArrayList<>();
    }

    public PriorityQueue<Movie> getMovieCollection() {
        return movieCollection;
    }

    public ArrayList<String> getListPassportID() {
        return directorsID;
    }

    public String getCollectionType() {
        return "java.util.PriorityQueue<Movie>";
    }

    public int getSize() {
        return movieCollection.size();
    }

    public Date getInitializationDate() {
        return initializationDate;
    }

    public void addMovie(Movie movie) {
        movie.setId(nextId++);
        movieCollection.add(movie);
    }

    public void updateMovieById(Long id, Movie newMovie) {
        newMovie.setId(id);
        movieCollection.add(newMovie);
    }

    public void removeMovieById(Long id) {
        if (!movieCollection.removeIf(movie -> movie.getId().equals(id))) {
            System.out.println("Фильм с ID " + id + " не найден.");
        } else {
            System.out.println("Фильм с ID " + id + " удален.");
        }
    }

    public void clearCollection() {
        movieCollection.clear();
        nextId = 1L;
    }

    public void showCollection() {
        if (movieCollection.isEmpty()) {
            System.out.println("Коллекция пуста");
        } else {
            for (Movie movie : movieCollection) {
                System.out.println(movie);
            }
        }
    }


    private String dataFilePath = "src/movies.csv"; // Путь к файлу с данными

    public void saveDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFilePath))) {
            for (Movie movie : movieCollection) {
                writer.write(convertToCsv(movie));
                writer.newLine();
            }
            System.out.println("Коллекция успешно сохранена в файл " + dataFilePath);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении коллекции в файл: " + e.getMessage());
        }
    }

    public void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Movie movie = convertFromCsv(line);
                    addMovie(movie);
                } catch (IllegalArgumentException e) {
                    System.err.println("Ошибка при чтении данных из файла: " + e.getMessage());
                }
            }
            System.out.println("Коллекция успешно загружена из файла " + dataFilePath);
        } catch (FileNotFoundException e) {
            System.out.println("Файл " + dataFilePath + " не найден. Будет создана новая коллекция.");
        } catch (IOException e) {
            System.err.println("Ошибка при чтении данных из файла: " + e.getMessage());
        }
    }

    private String convertToCsv(Movie movie) {
        String line = movie.getId() + "," +
                movie.getName() + "," +
                movie.getCoordinates().getX() + "," +
                movie.getCoordinates().getY() + "," +
                movie.getCreationDate() + "," +
                movie.getOscarsCount() + "," +
                movie.getLength() + "," +
                movie.getGenre() + "," +
                movie.getMpaaRating() + ",";
        if (movie.getDirector() == null) {
            return line + null;
        } else {
            return line +
                    movie.getDirector().getName() + "," +
                    movie.getDirector().getPassportID() + "," +
                    movie.getDirector().getLocation().getX() + "," +
                    movie.getDirector().getLocation().getY() + "," +
                    movie.getDirector().getLocation().getName();
        }
    }

    private Movie convertFromCsv(String line) {
        String[] values = line.split(",");
        if (values.length != 14 && values.length != 10) {
            throw new IllegalArgumentException("Неверный формат данных в файле.");
        }
        try {
            Movie movie = new Movie();
            Person director = new Person();
            Location directorLocation = new Location();
            Coordinates coordinates = new Coordinates();

            movie.setId(Long.parseLong(values[0]));
            movie.setName(values[1]);
            coordinates.setX(Float.parseFloat(values[2]));
            coordinates.setY(Integer.parseInt(values[3]));
            movie.setCoordinates(coordinates);
            movie.setCreationDate(LocalDate.parse(values[4]));
            movie.setOscarsCount(Integer.parseInt(values[5]));
            movie.setLength(Long.parseLong(values[6]));
            movie.setGenre(MovieGenre.valueOf(values[7]));
            try{movie.setMpaaRating(MpaaRating.valueOf(values[8]));} catch (Exception _) {};
            try{director.setName(values[9]);
            director.setPassportID(values[10]);
            directorLocation.setX(Integer.parseInt(values[11]));
            directorLocation.setY(Long.parseLong(values[12]));
            director.setName(values[13]);
            director.setLocation(directorLocation);
            movie.setDirector(director);} catch (Exception _) {};
            return movie;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный формат числа в файле.");
        }
    }



}
