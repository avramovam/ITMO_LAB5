public class Main {
    public static void main(String[] args) {
        CollectionManager collectionManager = new CollectionManager();
        //collectionManager.loadDataFromFile(); // Загрузка данных при старте

        ConsoleManager consoleManager = new ConsoleManager(collectionManager);
        consoleManager.startInteractiveMode();

        //collectionManager.saveDataToFile(); // Сохранение данных при завершении

    }
}