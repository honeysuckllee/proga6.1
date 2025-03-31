package ru.lab6.Requests;

import lombok.Data;
import ru.lab6.Commands.CommandType;

/**
 * Запрос со данными типа Route
 */
@Data
public class RequestInt extends Request {

    public int value;

    public RequestInt(){
        super(null);
    }

    public RequestInt(CommandType commandtype, int value) {
        super(commandtype);
        this.value = value;
    }

    public RequestInt(CommandType commandtype) {
        super(commandtype);
    }

}
