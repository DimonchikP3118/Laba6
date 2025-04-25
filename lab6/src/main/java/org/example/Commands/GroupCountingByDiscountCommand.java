package org.example.Commands;

import org.example.ForFields.Person;
import org.example.Model.CollectionManager;
import org.example.Model.Ticket;

import java.util.HashMap;
import java.util.Map;

public class GroupCountingByDiscountCommand implements BaseCommand{
    @Override
    public String execute(CollectionManager collectionManager, Integer id, String path, Ticket ticket, Person person) {
        if (collectionManager.getTickets().isEmpty()) {
            return "\u001B[31m" + "Нет билетов" + "\u001B[0m";
        }
        Map<Integer, Integer> discountGroups = new HashMap<>();
        StringBuilder result = new StringBuilder();
        for (Ticket tickety : collectionManager.getTickets()) {
            int discount = tickety.getDiscount();
            discountGroups.put(discount, discountGroups.getOrDefault(discount, 0) + 1);
        }
        for (Map.Entry<Integer, Integer> entry : discountGroups.entrySet()) {
            result.append("Скидка: ").append(entry.getKey()).append(", количество: ").append(entry.getValue()).append("\n");
        }
        return result.toString();
    }

    @Override
    public String getDescription() {
        return "";
    }
}
