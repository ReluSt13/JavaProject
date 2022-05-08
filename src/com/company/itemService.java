package com.company;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public interface itemService {

    void add(item itemToAdd);

    item getById(int id);

    List<item> getAll();

    List<item> getItemsBetweenDates(Date d1, Date d2);

    void delete(item itemToDelete);

    List<item> getByCustomFilter(Predicate<item> filter);

    void updateContent(int id, String newContent);
}
