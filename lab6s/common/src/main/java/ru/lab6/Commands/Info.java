package ru.lab6.Commands;

import ru.lab6.Model.Deque;
import ru.lab6.exceptions.CommandException;
import ru.lab6.Requests.Request;
import ru.lab6.Response;

import java.io.IOException;

/**
 * Класс `Info` реализует интерфейс `Command` и представляет команду вывода информации о коллекции.
 * При выполнении команды выводятся тип коллекции, время создания и количество элементов.
 */
public class Info extends Command {
    /**
     * Коллекция `Deque`, для которой выводится информация.
     */
    private Deque deq;

    /**
     * Конструктор класса `Info`.
     *
     * @param deque Коллекция `Deque`, для которой выводится информация.
     */
    public Info(Deque deque) {
        super(CommandType.INFO);
        deq = deque;
    }

    /**
     * Метод `execute` выводит информацию о коллекции, включая тип коллекции,
     * время создания и количество элементов.
     * Если возникает ошибка при получении времени создания, выбрасывается исключение `RuntimeException`.
     */


    @Override
    public Response execute(Request request) throws CommandException {
        StringBuilder rez = new StringBuilder("Информация о коллекции:\n");
        rez.append("Тип коллекции:" + deq.getCollectionType()).append("\n");
        try {
            rez.append("Время создания коллекции:" + deq.getCollectionFileCreationDate()).append("\n");
        } catch (IOException e) {
            return new Response("Ошибка\n");
        }
        rez.append("Количество элементов в коллекции:" + deq.getDeque().size()).append("\n");
        return new Response(rez.toString());
    }
}