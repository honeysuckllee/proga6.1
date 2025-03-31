package ru.lab6.Commands;

import ru.lab6.Model.Coordinates;
import ru.lab6.Model.Deque;
import ru.lab6.Model.Location;
import ru.lab6.Requests.RequestRoute;
import ru.lab6.exceptions.CommandException;
import ru.lab6.Requests.Request;
import ru.lab6.Response;

import java.time.LocalDate;

/**
 * Класс `Add` реализует команду добавления нового элемента `Route` в коллекцию `Deque`.
 */
public class Add extends Command {
    /**
     * Имя маршрута. Поле не может быть null, строка не может быть пустой.
     */
    private String name;
    /**
     * Координаты маршрута. Поле не может быть null.
     */
    private Coordinates coordinates;
    /**
     * Дата создания маршрута. Поле не может быть null, значение этого поля должно генерироваться автоматически.
     */
    private LocalDate creationDate;
    /**
     * Начальная локация маршрута. Поле может быть null.
     */
    private Location from;
    /**
     * Конечная локация маршрута. Поле может быть null.
     */
    private Location to;
    /**
     * Дистанция маршрута.
     */
    private Float distance;
    /**
     * Коллекция `Deque`, в которую добавляется маршрут.
     */
    private Deque deque;

    /**
     * Конструктор класса `Add`.
     *
     * @param deque   Коллекция `Deque`, в которую добавляется маршрут.
     */
    public Add(Deque deque){
        super(CommandType.ADD);
        this.deque = deque;
    }

    /**
     * Выполняет команду добавления нового элемента `Route` в коллекцию.
     * Запрашивает у пользователя данные для создания объекта `Route` и добавляет его в коллекцию `Deque`.
     */

    @Override
    public Response execute(Request request) throws CommandException {
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

        deque.addRoute(name, coordinates, creationDate, from, to, distance);

        return new Response("Маршрут добавлен" + "\n");

    }
}
