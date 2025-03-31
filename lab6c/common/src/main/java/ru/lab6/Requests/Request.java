package ru.lab6.Requests;


import ru.lab6.Commands.CommandType;

import java.io.Serializable;

/**
 * Базовый класс для запроса
 */
public class Request implements Serializable {
    private static final long serialVersionUID = -7213323027290265345L;
    private CommandType commandType;

    public Request(CommandType commandType) {
        this.commandType = commandType;
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

    public boolean isEmpty() {
        return commandType == null;
    }

    public Request() {
    }
}
