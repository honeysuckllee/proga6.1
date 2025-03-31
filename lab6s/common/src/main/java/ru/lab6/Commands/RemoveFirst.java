package ru.lab6.Commands;

import ru.lab6.Model.Deque;
import ru.lab6.exceptions.CommandException;
import ru.lab6.Requests.Request;
import ru.lab6.Response;

/**
 * Класс `RemoveFirst` реализует интерфейс `Command` и представляет команду удаления первого элемента из коллекции.
 * При выполнении команды удаляется первый элемент коллекции.
 */
public class RemoveFirst extends Command {
    /**
     * Коллекция `Deque`, из которой удаляется первый элемент.
     */
    private Deque deque;

    /**
     * Конструктор класса `RemoveFirst`.
     *
     * @param deque Коллекция `Deque`, из которой удаляется первый элемент.
     */
    public RemoveFirst(Deque deque) {
        super(CommandType.REMOVE_FIRST);
        this.deque = deque;
    }

    /**
     * Метод `execute` удаляет первый элемент из коллекции.
     */

    @Override
    public Response execute(Request request) throws CommandException {
        StringBuilder rez = new StringBuilder("\n");

        if (deque.removeFirst()){
            rez.append("Успешно удален первый элемент").append("\n");
        }
        else{
            rez.append("Коллекция пуста").append("\n");
        }
        return new Response(rez.toString());
    }
}