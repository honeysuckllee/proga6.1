package ru.lab6.Commands;

import ru.lab6.Model.Deque;
import ru.lab6.Requests.RequestStr;
import ru.lab6.exceptions.CommandException;
import ru.lab6.Requests.Request;
import ru.lab6.Response;

import java.util.Arrays;
import java.util.List;

/**
 * Класс `FilterStartsWithName` реализует интерфейс `Command` и представляет команду фильтрации элементов коллекции,
 * которые начинаются с указанного имени.
 */
public class FilterStartsWithName extends Command {
    /**
     * Коллекция `Deque`, с которой работает команда.
     */
    private Deque deque;

    /**
     * Имя, с которого должны начинаться элементы коллекции.
     */
    private String name;

    /**
     * Конструктор класса `FilterStartsWithName`.
     *
     * @param deque Коллекция `Deque`, с которой работает команда.
     */
    public FilterStartsWithName(Deque deque) {
        super(CommandType.FILTER_STARTS_WITH_NAME);
        this.deque = deque;
    }

    /**
     * Метод `execute` выполняет команду фильтрации элементов коллекции, которые начинаются с указанного имени.
     * Если имя не было передано, запрашивает его у пользователя.
     */

    @Override
    public Response execute(Request request) throws CommandException {
        this.name = ((RequestStr)request).name;
        return new Response(deque.filterStartsWithName(name));
    }
}