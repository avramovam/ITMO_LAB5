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


}
