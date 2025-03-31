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

/**
 * Класс `AddIfMax` реализует команду добавления нового элемента `Route` в коллекцию `Deque`,
 * только если его `id` больше, чем текущий максимальный `id` в коллекции.
 */
public class AddIfMax extends Command {
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
     * Идентификатор маршрута.
     */
    private int id;

    /**
     * Конструктор класса `AddIfMax`.
     *
     * @param deque   Коллекция `Deque`, в которую добавляется маршрут.
     */
    public AddIfMax(Deque deque) {
        super(CommandType.ADD_IF_MAX);
        this.deque = deque;
    }

    /**
     * Выполняет команду добавления нового элемента `Route` в коллекцию, если его `id` больше максимального.
     * Запрашивает у пользователя данные для создания объекта `Route` и добавляет его в коллекцию `Deque`,
     * только если `id` больше, чем `maxId` в коллекции.
     */

    @Override
    public Response execute(Request request)  throws CommandException {
        RequestRoute requestRoute = (RequestRoute) request;

        // id
        int id = requestRoute.route.getId();

        if (id > deque.getMaxId()) {

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

            deque.addRoute(id, name, coordinates, creationDate, from, to, distance);

            return new Response("Маршрут добавлен\n");
        }
        else{
            return new Response("Введенное значение меньше максимального id коллекции\n ");
        }
    }
}
