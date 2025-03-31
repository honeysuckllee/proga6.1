package ru.lab6.Commands;

import ru.lab6.Model.Deque;
import ru.lab6.exceptions.CommandException;
import ru.lab6.Requests.Request;
import ru.lab6.Response;

/**
 * Класс `Clear` реализует команду очистки коллекции `Deque`.
 */
public class Clear extends Command {
    /**
     * Коллекция `Deque`, которую необходимо очистить.
     */
    private Deque deq;

    /**
     * Конструктор класса `Clear`.
     *
     * @param deque Коллекция `Deque`, которую необходимо очистить.
     */
    public Clear(Deque deque) {
        super(CommandType.CLEAR);
        deq = deque;
    }

    /**
     * Выполняет команду очистки коллекции `Deque`.
     * Удаляет все элементы из коллекции `Deque`.
     */
    @Override
    public Response execute(Request request) throws CommandException {
        deq.clear();
        return new Response("Коллекция очищена\n");
    }
}
