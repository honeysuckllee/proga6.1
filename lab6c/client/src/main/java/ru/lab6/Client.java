package ru.lab6;

import ru.lab6.Commands.CommandType;
import ru.lab6.Requests.Request;
import ru.lab6.Requests.RequestRoute;
import ru.lab6.Requests.RequestStr;
import ru.lab6.Requests.RequestInt;
import ru.lab6.Service.TransparentScannerWrapper;

import java.io.*;
import java.net.Socket;
import java.util.*;
/**
 * Класс клиента.
 * Используется для подключения к серверу.
 */
public class Client {
    /**
     * Конструктор
     *
     * @param port - порт подключения
     */
    public Client(int port) {
        this.port = port;
        useScript = new Stack<>();
        useScanners = new Stack<>();
        commandHandler = new CommandHandler();
    }

    /**
     * port - порт подключения
     */
    private static int port;
    /**
     * useScript - список выполняемых скриптов
     */
    private static Stack<String> useScript;
    /**
     * useScanners - список используемых сканеров
     */
    private static Stack<TransparentScannerWrapper> useScanners;

    /**
     *  commandHandler - менеджер комманд
     */
    static CommandHandler commandHandler;

    /**
     * метод для взаимодействия с сервером
     */
    public static void run() {
        Request request = null;
        Response response = null;

        boolean success = false;
        while (!success) {
            try {
                TransparentScannerWrapper scn;
                TransparentScannerWrapper scanner = new TransparentScannerWrapper(new Scanner(System.in), true);
                // текущий сканер
                commandHandler.scanner = scanner;
                useScanners.push(scanner);

                System.out.println("Клиентское приложение для управления коллекцией. ");
                System.out.println("Введите 'help' чтобы увидеть доступные команды или 'exit' для завершения работы.");

                // Создаем TCP-сокет и подключаемся к серверу
                Socket socket = new Socket("localhost", port);
                System.out.println("Подключение к серверу установлено.");

                // Получаем входной и выходной потоки для обмена данными с сервером
                OutputStream outputStream = socket.getOutputStream();
                InputStream inputStream = socket.getInputStream();


                while (true) {
                    String[] commandSplit  = commandHandler.readCommand();
                    request = commandHandler.generateRequest(commandSplit);
                    if (request.isEmpty()) continue;

                    try {
                        if (commandSplit[0].equals("exit")) {
                            System.out.println("Завершение клиента.");
                            success = true;
                            // Закрываем соединение с сервером
                            socket.close();
                            break;
                        } else if (commandSplit[0].equals("execute_script")) {

                            if (commandSplit.length == 1) {
                                System.out.println("Скрипт не задан");
                                continue;
                            }
                            if (useScanners.empty()){
                                useScanners.push(commandHandler.scanner);
                            }
                            String scriptsPath = System.getenv("ROUTE_SCRIPTS");
                            // создание сканера для скрипта
                            TransparentScannerWrapper scriptScanner = new TransparentScannerWrapper(new Scanner(new FileReader(scriptsPath + commandSplit[1])), false);
                            commandHandler.scanner = scriptScanner;
                            useScanners.push(scriptScanner);
                            useScript.push(scriptsPath + commandSplit[1]);
                            // делаем созданный сканер текущим
                            scn = scriptScanner;

                            while (useScanners.size() > 1) {
                                while (scn.hasNextLine()) {
                                    String[] scriptInput = scn.nextLine().split(" ");
                                    if (!commandHandler.generateRequest(scriptInput).isEmpty()) // commandHashMap.containsKey(scriptInput[0])
                                    {
                                        if (scriptInput[0].equals("execute_script")) {
                                            if (useScript.contains(scriptsPath + scriptInput[1])) {
                                                // убираем зацикливание
                                                continue;
                                            }
                                            // создаем новый сканер
                                            TransparentScannerWrapper embedScanner = new TransparentScannerWrapper(new Scanner(new FileReader(scriptsPath + scriptInput[1])), false);
                                            commandHandler.scanner = embedScanner;
                                            useScript.push(scriptsPath + scriptInput[1]);
                                            useScanners.push(embedScanner);
                                            scn = embedScanner;

                                        } else {
                                            Request requestScript = commandHandler.generateRequest(scriptInput);

                                            String requestClassName = requestScript.getClass().getName();
                                            if (requestClassName.equals("ru.lab6.Requests.RequestInt")){
                                                if (scriptInput.length > 1) {
                                                    ((RequestInt) requestScript).value = commandHandler.InputId(scriptInput[1]);
                                                } else {
                                                    ((RequestInt) requestScript).value = commandHandler.InputId("");
                                                }
                                            } else if (requestClassName.equals("ru.lab6.Requests.RequestStr")){
                                                if (scriptInput.length > 1) {
                                                    ((RequestStr) requestScript).name = commandHandler.InputName(scriptInput[1]);
                                                } else {
                                                    ((RequestStr) requestScript).name = commandHandler.InputName("");
                                                }
                                            } else if (requestClassName.equals("ru.lab6.Requests.RequestRoute")){
                                                if (scriptInput.length > 1) {
                                                    ((RequestRoute) requestScript).route = commandHandler.inputRoute(scriptInput[1],(requestScript.getCommandType() == CommandType.ADD_IF_MAX));
                                                } else {
                                                    ((RequestRoute) requestScript).route = commandHandler.inputRoute("",(requestScript.getCommandType() == CommandType.ADD_IF_MAX));
                                                }
                                            }


                                            // Отправка данных на сервер
                                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                                            objectOutputStream.writeObject(requestScript);


                                            // Получение ответа от сервера
                                            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                                            Response inputContainer = (Response) objectInputStream.readObject();
                                            System.out.println(inputContainer.getResponseInfo());
                                        }
                                    } else if (scriptInput[0].equals("exit")) {
                                        break;
                                    } else {
                                        System.err.println("Команда " + scriptInput[0] + " не найдена.");
                                    }
                                }
                                if (!useScript.empty()) {
                                    useScript.pop();// убираем выполненный скрипт из списка
                                }
                                if (!useScanners.empty()) {
                                    commandHandler.scanner = useScanners.pop(); // делаем предыдущий сканер текущим
                                }
                            }

                            if (!useScript.empty()) {
                                useScript.pop();
                            }
                            if (!useScanners.empty()) {
                                commandHandler.scanner = useScanners.pop();
                            }
                            System.out.println("Выполнение скрипта завершено.");

                        } else if (!request.isEmpty()) {

                            String requestClassName = request.getClass().getName();
                            if (requestClassName.equals("ru.lab6.Requests.RequestInt")){
                                if (commandSplit.length > 1) {
                                    ((RequestInt) request).value = commandHandler.InputId(commandSplit[1]);
                                } else {
                                    ((RequestInt) request).value = commandHandler.InputId("");
                                }
                            } else if (requestClassName.equals("ru.lab6.Requests.RequestStr")){
                                if (commandSplit.length > 1) {
                                    ((RequestStr) request).name = commandHandler.InputName(commandSplit[1]);
                                } else {
                                    ((RequestStr) request).name = commandHandler.InputName("");
                                }
                            } else if (requestClassName.equals("ru.lab6.Requests.RequestRoute")){
                                boolean idInput = (request.getCommandType() == CommandType.ADD_IF_MAX) | (request.getCommandType() == CommandType.UPDATE);
                                if (commandSplit.length > 1) {
                                    ((RequestRoute) request).route = commandHandler.inputRoute(commandSplit[1],idInput);
                                } else {
                                    ((RequestRoute) request).route = commandHandler.inputRoute("",idInput);
                                }
                            } else if (requestClassName.equals("ru.lab6.Requests.Request")){
                                // команда без параметров - дополнительных действий не требуется
                            }

                            // Отправка данных на сервер
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                            objectOutputStream.writeObject(request);


                            // Получение ответа от сервера
                            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                            Response inputContainer = (Response) objectInputStream.readObject();
                            System.out.println(inputContainer.getResponseInfo());

                        } else {
                            System.err.println("Команда '" + commandSplit[0] + "' не найдена");
                        }
                    } catch (FileNotFoundException e1) {
                        System.err.println("Файл не найден");
                    }
                }

            } catch (NoSuchElementException e) {
                System.err.println("Не тыкай Ctrl+D\n");
                break;
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}