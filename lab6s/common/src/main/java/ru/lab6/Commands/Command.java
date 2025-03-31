package ru.lab6.Commands;

import ru.lab6.Requests.Request;
import ru.lab6.Response;
import ru.lab6.exceptions.CommandException;

import java.io.Serializable;

public abstract class Command implements Serializable {
    protected final CommandType commandType;
    public abstract Response execute(Request request) throws CommandException;

    public CommandType getCommandType() {
        return commandType;
    }

    protected Command(CommandType commandType) {
        this.commandType = commandType;
    }

    public Request getRequest() {
        return new Request(null);
    }

}