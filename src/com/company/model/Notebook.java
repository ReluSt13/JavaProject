package com.company.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Notebook {

    private List<Catalogue> notebook = new ArrayList<>();
    private int nrOfLists;
    private static Notebook instance;
    private Notebook() {}

    public static Notebook getInstance() {
        if (instance == null) instance = new Notebook();
        return instance;
    }

    public void addList(Catalogue listToBeAdded) {
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

    public Catalogue getListById(int id) {
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

    public void deleteLastList() {
        if (this.nrOfLists > 0) {
            this.notebook.remove(this.nrOfLists - 1);
            this.nrOfLists = this.notebook.size();
        }
    }

    public int getNrOfLists() {
        return nrOfLists;
    }
}
