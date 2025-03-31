package ru.lab6.Commands;

import ru.lab6.Model.Deque;
import ru.lab6.Requests.RequestInt;
import ru.lab6.exceptions.CommandException;
import ru.lab6.Requests.Request;
import ru.lab6.Response;


public class RemoveLower extends Command {
    private Deque deque;
    private int id;

    public RemoveLower(Deque deque) {
        super(CommandType.REMOVE_LOWER);
        this.deque = deque;
    }

    @Override
    public Response execute(Request request)  throws CommandException {

        id = ((RequestInt)request).value;
        if (!deque.getDeque().isEmpty()) {
            int counterDell = deque.removeLower(id);
            return new Response("Удалено " + counterDell + " элементов" + "\n");
        }
        return new Response("Коллекция пуста" + "\n");
    }
}
