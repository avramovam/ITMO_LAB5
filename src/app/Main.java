package app;

public class Main {
    public static void main(String[] args) {
        CollectionManager collectionManager = new CollectionManager();
        ConsoleManager consoleManager = new ConsoleManager(collectionManager);
        try {
            consoleManager.startInteractiveMode();
        } finally {
            consoleManager.emergencyShutdown();
        }


    }
}