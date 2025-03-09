package app;

public class Main {
    public static void main(String[] args) {
        CollectionManager collectionManager = new CollectionManager();
        collectionManager.loadDataFromFile();

        ConsoleManager consoleManager = new ConsoleManager(collectionManager);
        consoleManager.startInteractiveMode();

        //collectionManager.saveDataToFile();

    }
}