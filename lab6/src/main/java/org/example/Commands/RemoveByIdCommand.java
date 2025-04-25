package org.example.Commands;

import org.example.ForFields.Person;
import org.example.Model.CollectionManager;
import org.example.Model.Ticket;

import java.util.Objects;

public class RemoveByIdCommand implements BaseCommand{
    @Override
    public String execute(CollectionManager collectionManager, Integer id, String path, Ticket ticket, Person person) {
        if (collectionManager.getTickets().isEmpty()) {
            return "\u001B[31m" + "Нет билетов" + "\u001B[0m";
        }
        for(Ticket tickety: collectionManager.getTickets()){
            if(Objects.equals(id, ticket.getId())){
                collectionManager.getTickets().remove(tickety);
                return "\u001B[32m" + "Билет по id = " + id + " удален" + "\u001B[0m";
            }
        }
        return "\u001B[31m" + "Нет билетов c таким id" + "\u001B[0m";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
