package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class notebook {

    private List<list> notebook = new ArrayList<>();
    private int nrOfLists;
    private static notebook instance;
    private notebook() {}

    public static notebook getInstance() {
        if (instance == null) instance = new notebook();
        return instance;
    }

    public void addList(list listToBeAdded) {
        this.notebook.add(listToBeAdded);
        this.nrOfLists = this.notebook.size();
    }

    public void print() {
        System.out.println("This notebook has: " + this.nrOfLists + " lists.");
        AtomicInteger i = new AtomicInteger();
        this.notebook.forEach(list -> {
            System.out.println("---List nr. " + i.incrementAndGet() + ".---");
            list.print();
        });
    }

    public list getListById(int id) {
        return this.notebook.stream()
                .filter(list -> list.getId() == id)
                .findAny()
                .orElse(null);
    }

    public void deleteListById(int id) {
        if(id < 0) throw new IllegalArgumentException("ID-ul nu poate fi negativ");
        this.notebook.removeIf(list -> list.getId() == id);
        this.nrOfLists = this.notebook.size();
    }

}
