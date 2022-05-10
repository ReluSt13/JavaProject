package com.company;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface itemService {

    void add(item itemToAdd);

    default item getById(int id) {
        Optional<item> ItemOptional = getAll().stream()
                .filter(item -> item.getId() == id)
                .findFirst();

        return ItemOptional.orElse(null);
    }

    List<item> getAll();

    default List<item> getItemsBetweenDates(Date d1, Date d2) {
        return getAll().stream()
                .filter(Item -> Item.getAddDate().after(d1) && Item.getAddDate().before(d2))
                .collect(Collectors.toList());
    }

    void delete(item itemToDelete);

    default List<item> getByCustomFilter(Predicate<item> filter) {
        return getAll().stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    default void updateContent(int id, String newContent) {
        item itemToUpdate = getById(id);
        itemToUpdate.updateContent(newContent);
        delete(getById(id));
        add(itemToUpdate);
    }
}
