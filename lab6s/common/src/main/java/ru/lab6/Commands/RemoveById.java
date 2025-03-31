package ru.lab6.Commands;

import ru.lab6.Model.Deque;
import ru.lab6.Requests.RequestInt;
import ru.lab6.exceptions.CommandException;
import ru.lab6.Requests.Request;
import ru.lab6.Response;


/**
 * Класс `RemoveById` реализует интерфейс `Command` и представляет команду удаления элемента из коллекции по его идентификатору.
 * Если идентификатор не был передан, он запрашивается у пользователя.
 */
public class RemoveById extends Command {
    /**
     * Коллекция `Deque`, из которой удаляется элемент.
     */
    private Deque deque;

    /**
     * Идентификатор элемента, который необходимо удалить.
     */
    private int id;

    /**
     * Конструктор класса `RemoveById`.
     *

     * @param deque Коллекция `Deque`, из которой удаляется элемент.

     */
    public RemoveById( Deque deque) {
        super(CommandType.REMOVE_BY_ID);
        this.deque = deque;
    }

    /**
     * Метод `execute` удаляет элемент из коллекции по его идентификатору.
     * Если идентификатор не был передан, он запрашивается у пользователя.
     */

    @Override
    public Response execute(Request request) throws CommandException {
        id =  ((RequestInt)request).value;
        boolean dell = deque.removeById(id);
        if (dell){
            return new Response("Элемент удален\n");
        }
        return new Response("Элемент не может быть удален\n");
    }
}