package org.example.Commands;

import org.example.ForFields.Person;
import org.example.Model.CollectionManager;
import org.example.Model.Ticket;

public class CountLessThanTypeCommand implements BaseCommand{
    @Override
    public String execute(CollectionManager collectionManager, Integer id, String path, Ticket ticket, Person person) {
        if (collectionManager.getTickets().isEmpty()) {
            return "\u001B[31m" + "Нет билетов" + "\u001B[0m";
        }
        try {
            int count = 0;
            for (Ticket ticketik: collectionManager.getTickets()){
                if (ticketik.getType().getChisl() > id){
                    count+=1;
                }
            }
            return "Количество необходимых элементов: " + count;
        } catch (NullPointerException e){
            return "Wrong argument";
        }
    }

    @Override
    public String getDescription() {
        return "";
    }
}
