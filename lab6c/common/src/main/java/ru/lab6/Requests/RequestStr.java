package ru.lab6.Requests;

import lombok.Data;
import ru.lab6.Commands.CommandType;

/**
 * Запрос со данными типа Route
 */
@Data
public class RequestStr extends Request {

    public String name;

    public RequestStr(){
        super(null);
    }

    public RequestStr(CommandType commandtype, String name) {
        super(commandtype);
        this.name = name;
    }

    public RequestStr(CommandType commandtype) {
        super(commandtype);
    }

}
