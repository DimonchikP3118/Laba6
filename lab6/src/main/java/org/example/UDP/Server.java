package org.example.UDP;

import org.example.Commands.CommandManager;
import org.example.DATA.Request;
import org.example.DATA.Response;
import org.example.Model.CollectionManager;

import java.io.*;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Objects;

public class Server {
    public static void main(String[] args) {
        String respons = "";
        try {
            CollectionManager collectionManager = new CollectionManager();
            CommandManager commandManager = new CommandManager();
            // Создаем сетевой канал
            DatagramChannel channel = DatagramChannel.open();
            channel.configureBlocking(false);

            // Привязываем канал к порту
            channel.bind(new InetSocketAddress(8080));

            System.out.println("Сервер запущен на порту 8080");
            while (true) {
                // Читаем датаграмму от клиента
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                InetSocketAddress clientAddress = (InetSocketAddress) channel.receive(buffer);

                if (buffer.position() > 0) { // проверка получения данных
                    buffer.flip(); // разворот битов при необходимости

                    ObjectInputStream oi = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
                    Request request = (Request) oi.readObject();

                    // Получаем адрес и порт клиента
                    InetAddress clientIP = clientAddress.getAddress();
                    int clientPort = clientAddress.getPort();

                    System.out.println("Получено сообщение от клиента " + clientIP + ":" + clientPort + ": " + request);
                    if (Objects.equals(request.getMessage(), "add")){
                        respons = commandManager.response(request.getMessage(),collectionManager,request.getTicket(),null,null);
                    } else if (Objects.equals(request.getMessage(),"filter_by_person")) {
                        respons = commandManager.response(request.getMessage(),collectionManager,null,null,request.getPerson());
                    } else if (Objects.equals(request.getMessage(),"remove_by_id")) {
                        respons = commandManager.response(request.getMessage(),collectionManager,null,request.getId(),null);
                    } else if (Objects.equals(request.getMessage(), "exit")) {
                        collectionManager.saveTickets();
                    } else if (Objects.equals(request.getMessage(), "count_less_than_type")) {
                        respons = commandManager.response(request.getMessage(),collectionManager,null,request.getType().getChisl(),null);
                    } else {
                        respons = commandManager.response(request.getMessage(),collectionManager,null,null,null);
                    }

                    // Отправляем ответ клиенту
                    Response response = new Response(respons);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    objectOutputStream.writeObject(response);

                    ByteBuffer responseBuffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
                    channel.send(responseBuffer, clientAddress);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка запуска сервера: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
