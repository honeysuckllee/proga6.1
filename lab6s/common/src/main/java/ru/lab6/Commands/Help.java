package ru.lab6.Commands;

import ru.lab6.exceptions.CommandException;
import ru.lab6.Requests.Request;
import ru.lab6.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс `Help` реализует интерфейс `Command` и представляет команду вывода справки по доступным командам.
 * При выполнении команды выводится список всех доступных команд и их описание.
 * Использует словарь для хранения команд и их описаний, что делает код более гибким и удобным для расширения.
 */
public class Help extends Command {

    /**
     * Словарь для хранения команд и их описаний.
     * Ключ — название команды, значение — её описание.
     */
    private final Map<String, String> commands;

    /**
     * Конструктор класса `Help`.
     * Инициализирует словарь команд и их описаний.
     */
    public Help() {
        super(CommandType.HELP);
        commands = new HashMap<>();
        initializeCommands();
    }

    /**
     * Метод для инициализации словаря команд.
     * Добавляет команды и их описания в словарь.
     */
    private void initializeCommands() {
        commands.put("help", "вывести справку по доступным командам");
        commands.put("info", "вывести информацию о коллекции");
        commands.put("show", "вывести все элементы коллекции");
        commands.put("add (element_name) ", "добавить новый элемент в коллекцию");
        commands.put("update id (element)", "обновить значение элемента коллекции по id");
        commands.put("remove_by_id id", "удалить элемент из коллекции по id");
        commands.put("clear", "очистить коллекцию");
        commands.put("execute_script", "исполнить скрипт из файла");
        commands.put("exit", "завершить программу");
        commands.put("remove_first", "удалить первый элемент из коллекции");
        commands.put("add_if_max (element)", "добавить новый элемент, если он больше максимального");
        commands.put("remove_lower (element)", "удалить все элементы, меньшие заданного");
        commands.put("filter_starts_with_name name", "вывести элементы, имя которых начинается с подстроки");
        commands.put("print_unique_distance", "вывести уникальные значения поля distance");
        commands.put("print_field_descending_distance", "вывести значения поля distance в порядке убывания");
    }

    /**
     * Метод `execute` выводит список всех доступных команд и их описание.
     * Использует словарь для получения команд и их описаний.
     */

    @Override
    public Response execute(Request request)   throws CommandException {
        StringBuilder rez = new StringBuilder("Доступные команды:\n");
        for (Map.Entry<String, String> entry : commands.entrySet()) {
            rez.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
        }
        return new Response(rez.toString());
    }
}