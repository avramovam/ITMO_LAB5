package commands;

public interface Command {
    public void execute(String argument);
    String getDescription();
}
