package commands;

import app.CollectionManager;
import modules.MovieGenre;

import java.util.Scanner;

public class CountGreaterThanGenreCommand implements Command {
    private CollectionManager collectionManager;

    public CountGreaterThanGenreCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String argument) {
        try {
            MovieGenre movieGenre = MovieGenre.valueOf(argument.toUpperCase());
            long count = collectionManager.countGreaterThanGenre(movieGenre);
            System.out.println("Количество фильмов с жанром больше, чем " + movieGenre + ": " + count);
        } catch (IllegalArgumentException e) {
            System.out.println("Некорректный жанр. Пожалуйста, введите один из: DRAMA, COMEDY, ADVENTURE, HORROR, FANTASY.");
        } catch (NullPointerException e) {
            System.out.println("Пожалуйста, введите название жанра из: DRAMA, COMEDY, ADVENTURE, HORROR, FANTASY.");
        }
    }


    @Override
    public String getDescription() {
        return "count_greater_than_genre genre : вывести количество элементов, значение поля genre которых больше заданного";
    }
}
