package org.example.Commands;

import org.example.ForFields.Person;
import org.example.Model.CollectionManager;
import org.example.Model.Ticket;

import java.util.Objects;

public class FilterByPersonCommand implements BaseCommand{
    @Override
    public String execute(CollectionManager collectionManager, Integer id, String path, Ticket ticket, Person person) {
        if (collectionManager.getTickets().isEmpty()) {
            return "\u001B[31m" + "Нет билетов" + "\u001B[0m";
        }
        StringBuilder result = new StringBuilder();
        boolean c = false;
        for (Ticket tickety: collectionManager.getTickets()){
            if (Objects.equals(person.getEyeColor().getNumber(), tickety.getPerson().getEyeColor().getNumber()) && Objects.equals(tickety.getPerson().getNationality().getNumber(), person.getNationality().getNumber())){
                result.append(tickety).append("\n");
                c = true;
            }
        }
        if (!c){
            return "Совпадений не найдено";
        }
        return "Билеты с похожими людьми" + "\n" + result;
    }

    @Override
    public String getDescription() {
        return "";
    }
}
