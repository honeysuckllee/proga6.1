package ru.lab6.Commands;

import ru.lab6.Model.Deque;
import ru.lab6.Model.Route;
import ru.lab6.exceptions.CommandException;
import ru.lab6.Requests.Request;
import ru.lab6.Response;

import java.util.ArrayDeque;

public class Show extends Command {
    private ArrayDeque<Route> deque;

    public Show(ArrayDeque<Route> deque) {
        super(CommandType.SHOW);
        this.deque = deque;
    }

    @Override
    public Response execute(Request request) throws CommandException {
        StringBuilder rez = new StringBuilder("Коллекция:\n");
        if (!deque.isEmpty()) {
            for (Route route : deque) {
                rez.append(route.toString()).append("\n");
            }
        }
        else {
            rez.append("пуста").append("\n");
        }
        return new Response(rez.toString());
    }
}
