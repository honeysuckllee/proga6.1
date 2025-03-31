package ru.lab6;

import lombok.Data;
import ru.lab6.Commands.CommandType;
import ru.lab6.Model.Coordinates;
import ru.lab6.Model.Location;
import ru.lab6.Model.Route;
import ru.lab6.Requests.Request;
import ru.lab6.Requests.RequestRoute;
import ru.lab6.Requests.RequestStr;
import ru.lab6.Requests.RequestInt;
import ru.lab6.Service.TransparentScannerWrapper;

import java.time.LocalDate;

import static ru.lab6.Service.Utilites.*;

@Data
public class CommandHandler {
    private String command;
    private String[] commandSplit;
    public TransparentScannerWrapper scanner;


    public CommandHandler() {
        this.command = "";
    }

    /**
     * Launches the command processor and enters the interactive mode.
     * @return 0 if the program is finished properly.
     */
    public String[] readCommand() {
        if (command.equals("exit") || !(scanner.hasNext())) {
            System.out.println("Завершение программы.");
            System.exit(0);
        }
        command = scanner.nextLine();
        commandSplit = command.trim().split(" "); // remove extra-spaces and split the command from the argument
        return commandSplit;
    }

    public Route inputRoute(String args, boolean addIfMax)
    {
        int id = 0;
        if (addIfMax){
            id = getValidInt(scanner, "Введите id:");
        }
        String name;
        if (args.isEmpty()){
            name = getValidName(scanner);
        }
        else{
            name = args;
        }
        if (scanner.enable_out){
            System.out.println("Ввод Coordinates");}
        Coordinates coordinates = getValidCoordinates(scanner);
        LocalDate creationDate = LocalDate.now();
        if (scanner.enable_out){
            System.out.println("Ввод from:");}
        Location from = getValidLocation(scanner);
        if (scanner.enable_out){
            System.out.println("Ввод to:");}
        Location to = getValidLocation(scanner);
        if (scanner.enable_out){
            System.out.println("Ввод Distance");}
        Float distance = getValidFloatDistance(scanner);

        return new Route(id, name, coordinates, creationDate, from, to, distance);
    }

    /**
     * Ввод id
     * @param args
     * @return
     */
    public int InputId(String args){
        int id = 0;
        if (args.isEmpty()){
            id = getValidInt(scanner, "Введите id:");
        }
        else{
            try{
                id = Integer.parseInt(args);
            }
            catch (NumberFormatException e){
                id = 0;
                System.out.println("Введен некорректный id");
            }
        }
        return id;
    }

    /**
     * Ввод имени
     * @param args
     * @return
     */
    public String InputName(String args) {
        String name = null;
        if (args.isEmpty()) {
            name = getValidName(scanner);
        } else {
            name = args;
        }
        return name;
    }


    public Request generateRequest(String[] commandSplit) {
        switch (commandSplit[0]) {
            case "":
                break;
            case "help":
                return new Request(CommandType.HELP);
           case "info":
                return new Request(CommandType.INFO);
            case "show":
                return new Request(CommandType.SHOW);
             case "add":
                return new RequestRoute(CommandType.ADD);
            case "update":
                return new RequestRoute(CommandType.UPDATE);
            case "remove_by_id":
                return new RequestInt(CommandType.REMOVE_BY_ID);
            case "clear":
                return new Request(CommandType.CLEAR);
            case "execute_script":
                return new Request(CommandType.EXECUTE_SCRIPT);
            case "exit":
                break;
            case "add_if_max":
                return new RequestRoute(CommandType.ADD_IF_MAX);
            case "remove_lower":
                return new RequestInt(CommandType.REMOVE_LOWER);
            case "filter_starts_with_name":
                return new RequestStr(CommandType.FILTER_STARTS_WITH_NAME);
            case "print_unique_distance":
                return new Request(CommandType.PRINT_UNIQUE_DISTANCE);
            case "print_field_descending_distance":
                return new Request(CommandType.PRINT_FIELD_DESCENDING_DISTANCE);
            case "remove_first":
                return new Request(CommandType.REMOVE_FIRST);
            default:
                System.out.println("Такой команды нет. Введи 'help' для справки");
                break;
            }
        return new Request(null);
    }
}
