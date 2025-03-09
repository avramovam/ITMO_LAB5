package commands;

import app.CollectionManager;
import app.ConsoleManager;

import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ExecuteScriptCommand implements Command{
    private CollectionManager collectionManager;
    private ConsoleManager consoleManager;
    private static Set<String> scriptStack = new HashSet<>();

    public ExecuteScriptCommand(ConsoleManager consoleManager, CollectionManager collectionManager) {
        this.consoleManager = consoleManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String argument) {
        if (argument.isEmpty()) {
            System.out.println("Необходимо указать имя файла скрипта.");
            return;
        }

        File scriptFile = new File(argument);
        String scriptPath = scriptFile.getAbsolutePath();

        if (scriptStack.contains(scriptPath)) {
            System.out.println("Обнаружена рекурсия! Скрипт " + scriptPath + " уже выполняется.");
            return;
        }

        scriptStack.add(scriptPath);

        try (Scanner fileScanner = new Scanner(new BufferedReader(new FileReader(scriptFile)))) {
            Scanner oldScanner = consoleManager.getScanner();
            consoleManager.setScanner(fileScanner);

            System.out.println("Выполнение скрипта из файла: " + scriptPath);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                String commandName = parts[0];
                String[] data = null;

                if (commandName.equals("add") || commandName.equals("update") || commandName.equals("add_if_min") || commandName.equals("remove_greater")) {
                    if (!fileScanner.hasNextLine()) {
                        System.out.println("Недостаточно данных для команды " + commandName + " в файле " + scriptPath);
                        break;
                    }
                    String dataLine = fileScanner.nextLine().trim();
                    data = dataLine.split(",");
                }

                System.out.println("Выполняемая команда из скрипта: " + line);
                consoleManager.processCommand(line, data);
            }

            System.out.println("Скрипт из файла " + scriptPath + " выполнен.");

            consoleManager.setScanner(oldScanner);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + scriptPath);
        } catch (Exception e) {
            System.out.println("Произошла ошибка при выполнении скрипта: " + e.getMessage());
        } finally {
            scriptStack.remove(scriptPath);
        }
    }

    @Override
    public String getDescription() {
        return "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }
}
