package ru.lab6.Commands;

import ru.lab6.Model.Coordinates;
import ru.lab6.Model.Deque;
import ru.lab6.Model.Location;
import ru.lab6.Requests.RequestRoute;
import ru.lab6.exceptions.CommandException;
import ru.lab6.Requests.Request;
import ru.lab6.Response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;


public class Update extends Command {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location from; //Поле может быть null
    private Location to; //Поле может быть null
    private Float distance;
    private Deque deque;
    private int id;

    public Update(Deque deque) {
        super(CommandType.UPDATE);
        this.deque = deque;
    }

    @Override
    public Response execute(Request request)  throws CommandException {
        RequestRoute requestRoute = (RequestRoute) request;

        // id
        int id = requestRoute.route.getId();

        //  name
        this.name = requestRoute.route.getName();

        //  coordinates
        this.coordinates = requestRoute.route.getCoordinates();

        //  creationDate
        this.creationDate = requestRoute.route.getCreationDate();

        //  from
        this.from = requestRoute.route.getFrom();

        //  to
        this.to = requestRoute.route.getTo();

        //  distance
        this.distance = requestRoute.route.getDistance();

        return new Response(deque.updateRoute(id, name, coordinates, creationDate, from, to, distance));

    }
}