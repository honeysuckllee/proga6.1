package ru.lab6.Commands;

import ru.lab6.Model.Deque;
import ru.lab6.exceptions.CommandException;
import ru.lab6.Requests.Request;
import ru.lab6.Response;

/**
 * Команда для печати элементов коллекции в порядке убывания расстояния до указанного поля.
 */
public class PrintFieldDescendingDistance extends Command {

    /**
     * Коллекция, с которой будет выполняться операция сортировки и вывода.
     */
    private final Deque deque;

    /**
     * Конструктор команды.
     *
     * @param deque коллекция
     */
    public PrintFieldDescendingDistance(Deque deque) {
        super(CommandType.PRINT_FIELD_DESCENDING_DISTANCE);
        this.deque = deque;
    }

    /**
     * Выполняет команду печати элементов коллекции  в порядке убывания.
     */
    @Override
    public Response execute(Request request) throws CommandException {
        return new Response(deque.printFieldDescendingDistance() + "\n");
    }
}