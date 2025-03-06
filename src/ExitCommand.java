public class ExitCommand implements Command {

    @Override
    public void execute(String argument) {
        System.out.println("Завершение работы...");
        System.exit(0);
    }

    @Override
    public String getDescription() {
        return "exit : завершить программу (без сохранения в файл)";
    }
}
