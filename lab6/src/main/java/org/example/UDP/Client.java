package org.example.UDP;

import org.example.DATA.Request;
import org.example.DATA.Response;
import org.example.ForFields.*;
import org.example.Model.Ticket;
import org.example.Reciewer;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Создаем датаграмный сокет
            DatagramSocket socket = new DatagramSocket(); // возьмет любой свободный порт
            Scanner scanner = new Scanner(System.in);
            Reciewer reciewer = new Reciewer();
            while (scanner.hasNextLine()){
                Request request;
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                String input = scanner.nextLine();

                if (Objects.equals(input, "add")){
                    Ticket ticket = reciewer.createTicket(null);
                    request = new Request(ticket,input);
                } else if (Objects.equals(input, "exit")) {
                    System.exit(0);
                    request = new Request(input);
                } else if (Objects.equals(input, "remove_by_id")) {
                    request = new Request(input.split(" ")[0],Integer.parseInt(input.split(" ")[0]));
                } else if (Objects.equals(input, "filter_by_person")) {
                    Person person = reciewer.makePerson();
                    request = new Request(input,person);
                } else if (Objects.equals(input.split(" ")[0], "count_less_than_type")) {
                    TicketType type = TicketType.getTypeByEng(input.split(" ")[1]);
                    request = new Request(type,input.split(" ")[0]);
                } else {
                    request = new Request(input);
                }
                objectOutputStream.writeObject(request);

                // Отправляем сообщение серверу
                InetAddress serverAddress = InetAddress.getByName("localhost");
                DatagramPacket sendPacket = new DatagramPacket(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.size(), serverAddress, 8080);
                socket.send(sendPacket);
                System.out.println("Отправлено сообщение: " + request);

                // Получаем ответ от сервера
                byte[] buffer = new byte[1024];
                DatagramPacket inputPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(inputPacket);

                ObjectInputStream oi = new ObjectInputStream(new ByteArrayInputStream(inputPacket.getData()));
                Response response = (Response) oi.readObject();

                System.out.println("Получено сообщение от сервера: " + response);
            }

            // Закрываем сокет
            socket.close();
        } catch (UnknownHostException e) {
            System.err.println("Неизвестный хост: " + e.getMessage());
        } catch (IOException | ClassNotFoundException  e) {
            System.err.println("Ошибка отправки/приема данных: " + e.getMessage());
        }
    }
}
