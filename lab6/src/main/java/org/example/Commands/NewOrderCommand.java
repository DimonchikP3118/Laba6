package org.example.Commands;

import org.example.ForFields.Person;
import org.example.Model.CollectionManager;
import org.example.Model.Comparators.TicketComparatorByType;
import org.example.Model.Ticket;

import java.util.Vector;

public class NewOrderCommand implements BaseCommand{
    @Override
    public String execute(CollectionManager collectionManager, Integer id, String path, Ticket ticket, Person person) {
        if (collectionManager.getTickets().isEmpty()) {
            return "\u001B[31m" + "Нет билетов" + "\u001B[0m";
        }
        Vector<Ticket> tickets = collectionManager.getTickets();
        TicketComparatorByType comparatorByType = new TicketComparatorByType();
        StringBuilder result = new StringBuilder();
        tickets.stream().sorted(comparatorByType).forEach(tickety -> {
            result.append(tickety).append("\n");
        });
        return result.toString();
    }

    @Override
    public String getDescription() {
        return "";
    }
}
