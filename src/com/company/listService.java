package com.company;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface listService {

    void add(list listToAdd);

    default list getById(int id) {
        Optional<list> listOptional = getAll().stream()
                .filter(List -> List.getId() == id)
                .findFirst();

        return listOptional.orElse(null);
    }

    List<list> getAll();

    default List<list> getListsBetweenDates(Date d1, Date d2) {
        return getAll().stream()
                .filter(List -> List.getAddDate().after(d1) && List.getAddDate().before(d2))
                .collect(Collectors.toList());
    }

    void delete(list ListToDelete);

    default List<list> getByCustomFilter(Predicate<list> filter) {
        return getAll().stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    default void updateName(int id, String newName) {
        list listToUpdate = getById(id);
        listToUpdate.updateListName(newName);
        delete(getById(id));
        add(listToUpdate);
    }

}
