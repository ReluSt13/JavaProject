package com.company.service;

import com.company.model.Item;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface ItemService {

    void add(Item itemToAdd);

    default Item getById(int id) {
        Optional<Item> ItemOptional = getAll().stream()
                .filter(item -> item.getId() == id)
                .findFirst();
        AuditService.getInstance().print(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());

        return ItemOptional.orElse(null);
    }

    List<Item> getAll();

    default List<Item> getItemsBetweenDates(Date d1, Date d2) {
        AuditService.getInstance().print(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());

        return getAll().stream()
                .filter(Item -> Item.getAddDate().after(d1) && Item.getAddDate().before(d2))
                .collect(Collectors.toList());
    }

    void delete(Item itemToDelete);

    default List<Item> getByCustomFilter(Predicate<Item> filter) {
        AuditService.getInstance().print(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());

        return getAll().stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    default void updateContent(int id, String newContent) {
        Item itemToUpdate = getById(id);
        itemToUpdate.updateContent(newContent);
        delete(getById(id));
        add(itemToUpdate);
        AuditService.getInstance().print(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());

    }
}
