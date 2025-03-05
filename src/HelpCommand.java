public class HelpCommand implements Command {
    private ConsoleManager consoleManager;

    public HelpCommand(ConsoleManager consoleManager) {
        this.consoleManager = consoleManager;
    }
    @Override
    public void execute(String argument) {
        System.out.println("Доступные команды:");
        for (Command command : consoleManager.getCommands()) {
            System.out.println(command.getDescription());
        }

    }

    @Override
    public String getDescription() {
        return "help : вывести справку по доступным командам";
    }
}
