package com.company.service;

import com.company.model.Item;
import com.company.model.Catalogue;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface ListService {

    void add(Catalogue listToAdd);

    default Catalogue getById(int id) {
        Optional<Catalogue> listOptional = getAll().stream()
                .filter(List -> List.getId() == id)
                .findFirst();
        AuditService.getInstance().print(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());
        return listOptional.orElse(null);
    }

    List<Catalogue> getAll();

    default List<Catalogue> getListsBetweenDates(Date d1, Date d2) {
        AuditService.getInstance().print(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());
        return getAll().stream()
                .filter(List -> List.getAddDate().after(d1) && List.getAddDate().before(d2))
                .collect(Collectors.toList());
    }

    void delete(Catalogue ListToDelete);

    default List<Catalogue> getByCustomFilter(Predicate<Catalogue> filter) {
        AuditService.getInstance().print(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());
        return getAll().stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    default void addItem(int id, Item itemToAdd) {
        Catalogue updatedList = getById(id);
        if (updatedList.getItem(itemToAdd.getId()) == null) {
            updatedList.addToList(itemToAdd);
            delete(getById(id));
            add(updatedList);
        }
    }

    default void updateName(int id, String newName) {
        Catalogue listToUpdate = getById(id);
        listToUpdate.updateListName(newName);
        delete(getById(id));
        add(listToUpdate);
        AuditService.getInstance().print(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());
    }

}
