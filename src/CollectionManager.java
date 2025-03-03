import java.io.*;
import java.util.PriorityQueue;

public class CollectionManager {
    private PriorityQueue<Movie> movieCollection;
    private Long nextId = 1L;

    public CollectionManager() {
        movieCollection = new PriorityQueue<>();
    }

    public PriorityQueue<Movie> getMovieCollection() {
        return movieCollection;
    }

    public void addMovie(Movie movie) {
        movie.setId(nextId++);
        movieCollection.add(movie);
    }

    public void updateMovieById(Long id, Movie newMovie) {
        // поиск фильма по id, удаление и добавление нового по тому же id
        movieCollection.removeIf(movie -> movie.getId().equals(id));
        newMovie.setId(id);
        movieCollection.add(newMovie);
    }

    public void removeMovieById(Long id) {
        movieCollection.removeIf(movie -> movie.getId().equals(id));
    }

    // очищение коллекции и сброс счетчика
    public void clearCollection() {
        movieCollection.clear();
        nextId = 1L;
    }

    public void showCollection() {
        if (movieCollection.isEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        } else {
            for (Movie movie : movieCollection) {
                System.out.println(movie);
            }
        }
    }

    /*
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
        return movie.getId() + "," +
                movie.getName() + "," +
                movie.getCoordinates().getX() + "," +
                movie.getCoordinates().getY() + "," +
                movie.getCreationDate() + "," +
                movie.getOscarsCount() + "," +
                movie.getLength() + "," +
                movie.getGenre() + "," +
                movie.getMpaaRating() + "," +
                movie.getDirector().getName() + "," +
                movie.getDirector().getPassportID() + "," +
                movie.getDirector().getLocation().getX() + "," +
                movie.getDirector().getLocation().getY() + "," +
                movie.getDirector().getLocation().getName();
    }

    private Movie convertFromCsv(String line) {
        String[] values = line.split(",");
        if (values.length != 14) {
            throw new IllegalArgumentException("Неверный формат данных в файле.");
        }

        try {
            Movie movie = new Movie();
            movie.setId(Long.parseLong(values[0]));
            movie.setName(values[1]);
            Coordinates coordinates = new Coordinates();
            coordinates.setX(Float.parseFloat(values[2]));
            coordinates.setY(Integer.parseInt(values[3]));
            movie.setCoordinates(coordinates);
            return movie;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный формат числа в файле.");
        }
    }

*/


}
