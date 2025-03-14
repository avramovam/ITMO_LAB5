package modules;

import java.time.LocalDate;
import java.util.Objects;

public class Movie implements Comparable<Movie>{
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int oscarsCount; //Значение поля должно быть больше 0
    private long length; //Значение поля должно быть больше 0
    private MovieGenre genre; //Поле не может быть null
    private MpaaRating mpaaRating; //Поле может быть null
    private Person director; //Поле может быть null

    // Метод для сравнения modules.Movie по имени (для PriorityQueue)
    @Override
    public int compareTo(Movie other) {
        if (this.name.length() != other.name.length()) {
            return Integer.compare(this.name.length(), other.name.length());
        } else {
            return Long.compare(this.length, other.length);
        }
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", oscarsCount=" + oscarsCount +
                ", length=" + length +
                ", genre=" + genre +
                ", mpaaRating=" + mpaaRating +
                ", director=" + director +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return oscarsCount == movie.oscarsCount && length == movie.length && Objects.equals(id, movie.id) && Objects.equals(name, movie.name) && Objects.equals(coordinates, movie.coordinates) && Objects.equals(creationDate, movie.creationDate) && genre == movie.genre && mpaaRating == movie.mpaaRating && Objects.equals(director, movie.director);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, oscarsCount, length, genre, mpaaRating, director);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Название фильма не может быть пустым.");
        }
        this.name = name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public int getOscarsCount() {
        return oscarsCount;
    }

    public void setOscarsCount(int oscarsCount) {
        if (oscarsCount <= 0) {
            throw new IllegalArgumentException("Количество оскаров должно быть больше 0");
        }
        this.oscarsCount = oscarsCount;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Длина должна быть больше 0");
        }
        this.length = length;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public void setGenre(MovieGenre genre) {
        if (genre == null) {
            throw new IllegalArgumentException("Жанр не может быть пустым. Повторите ввод");
        }
        this.genre = genre;
    }

    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(MpaaRating mpaaRating) {
        if (mpaaRating == null) {
            throw new IllegalArgumentException("Рейтинг не может быть пустым. Повторите ввод");
        }
        this.mpaaRating = mpaaRating;
    }

    public Person getDirector() {
        return director;
    }

    public void setDirector(Person director) {
        this.director = director;
    }

}