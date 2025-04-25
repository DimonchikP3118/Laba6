package org.example.Commands;

import org.example.ForFields.Person;
import org.example.Model.CollectionManager;
import org.example.Model.Ticket;

public class AddCommand implements BaseCommand{
    @Override
    public String execute(CollectionManager collectionManager, Integer id, String path, Ticket ticket, Person person) {
        collectionManager.getTickets().add(ticket);
        return "Билет добавлен";
    }

    @Override
    public String getDescription() {
        return "\u001B[35m" + "add" + "\u001B[0m" + " - Добавление нового билета";
    }
}
