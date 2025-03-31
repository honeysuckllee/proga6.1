package ru.lab6;
import java.io.*;
import java.net.*;
import java.util.*;
import lombok.Data;
import ru.lab6.Commands.Command;
import ru.lab6.Commands.*;
import ru.lab6.Model.Deque;
import ru.lab6.Requests.Request;
import ru.lab6.exceptions.CommandException;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

@Data
public class ServerApp {
    private static int port;
    private static HashMap<CommandType, Command> commandHashMap;
    private static Deque deq;
    public static final Logger logger = Logger.getLogger("Server");

    public ServerApp(int port) throws IOException {
        this.port = port;
        Handler handler = new FileHandler("log.txt");
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
    }

    public static void initCommandsMap(){
        commandHashMap = new HashMap<>();
        deq = new Deque();
        commandHashMap.put(CommandType.HELP, new Help());
        commandHashMap.put(CommandType.SHOW, new Show(deq.getDeque()));
        commandHashMap.put(CommandType.INFO, new Info(deq));
        commandHashMap.put(CommandType.REMOVE_FIRST, new RemoveFirst(deq));
        commandHashMap.put(CommandType.CLEAR,new Clear(deq));
        commandHashMap.put(CommandType.PRINT_UNIQUE_DISTANCE, new PrintUniqueDistance(deq));
        commandHashMap.put(CommandType.PRINT_FIELD_DESCENDING_DISTANCE, new PrintFieldDescendingDistance(deq));
        commandHashMap.put(CommandType.REMOVE_LOWER, new RemoveLower(deq));
        commandHashMap.put(CommandType.REMOVE_BY_ID, new RemoveById(deq));
        commandHashMap.put(CommandType.ADD, new Add(deq));
        commandHashMap.put(CommandType.ADD_IF_MAX, new AddIfMax(deq));
        commandHashMap.put(CommandType.FILTER_STARTS_WITH_NAME, new FilterStartsWithName(deq));
        commandHashMap.put(CommandType.UPDATE, new Update(deq));
    }
    public static void run() throws IOException, ClassNotFoundException {
        initCommandsMap();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Сервер начал работу.");
        logger.info("Сервер начал работу.");

        // Создаем серверный сокет и привязываем его к порту
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Сервер ожидает подключения на порту " + port);

        // Поток для обработки команд "exit"
        Thread exitThread = new Thread(() -> {
            while (true) {
                try {
                    String line = scanner.nextLine();
                    if (line.equals("exit")) {
                        logger.info("Сервер завершил работу.");
                        System.exit(0);
                    }
                } catch (NoSuchElementException e) {
                    System.err.println("Не тыкай Ctrl+D\n");
                }
            }
        });
        exitThread.start();

        // Основной цикл обработки клиентских подключений
        while (true) {
            // Ожидаем подключения клиента
            Socket clientSocket = serverSocket.accept();
            System.out.println("Клиент подключен: " + clientSocket.getInetAddress());

            // Получаем входной и выходной потоки для обмена данными с клиентом
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            try {
                // Основной цикл обработки запросов от клиента
                while (true) {
                    // Чтение данных от клиента
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                    Request request;
                    try {
                        request = (Request) objectInputStream.readObject();
                    } catch (EOFException e) {
                        // Клиент отключился
                        System.out.println("Клиент отключился: " + clientSocket.getInetAddress());
                        break;
                    }

                    CommandType command = request.getCommandType();

                    try {
                        if (commandHashMap.containsKey(command)) {
                            Response result = commandHashMap.get(command).execute(request);

                            // Отправка результата клиенту
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                            objectOutputStream.writeObject(result);
                        } else {
                            System.err.println("Команда " + command + " не найдена.");
                            Response commandResponseContainer = new Response("Команда не найдена");

                            // Отправка результата клиенту
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                            objectOutputStream.writeObject(commandResponseContainer);
                        }
                    } catch (CommandException e) {
                        Response commandResponseContainer = new Response(e.getMessage());

                        // Отправка результата клиенту
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                        objectOutputStream.writeObject(commandResponseContainer);
                    }
                }
            } catch (IOException e) {
                System.err.println("Ошибка при работе с клиентом: " + e.getMessage());
            } finally {
                // Закрываем соединение с клиентом
                clientSocket.close();
                System.out.println("Соединение с клиентом закрыто.");
            }
        }
    }

}

