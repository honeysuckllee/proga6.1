package ru.lab6.Requests;

import lombok.Data;
import ru.lab6.Commands.CommandType;
import ru.lab6.Model.Route;

/**
 * Запрос со данными типа Route
 */
@Data
public class RequestRoute extends Request {

    public Route route;

    public RequestRoute(){
        super(null);
    }

    public RequestRoute(CommandType commandtype, Route route) {
        super(commandtype);
        this.route = route;
    }

    public RequestRoute(CommandType commandtype) {
        super(commandtype);
    }

}
