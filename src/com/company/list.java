package com.company;

import java.util.*;

public class list {
    private int id;
    private static int id_max = 0;
    private int nrOfItems;
    private int nrOfUniqueItems;
    private String listName;
    protected List<item> list = new ArrayList<>();
    private Set<item> uniqueList = new HashSet<>();

    public list(String listName) {
        this.id = id_max++;
        this.nrOfItems = 0;
        this.listName = listName;
    }
    public void addToList(item itemToBeAdded) {
        this.list.add(itemToBeAdded);
        this.uniqueList.add(itemToBeAdded);
        this.updateAttributes();
    }

    public void print() {
        System.out.println("List ID: " + id + "\nList name: " + listName + "\nNr. of items: " + nrOfItems);
        if(this.getNrOfItems() == 0) System.out.println("The list is empty :(");
        else {
            int index = 0;
            for (item item :
                    this.list) {
                System.out.println("------ Item nr." + (++index) + " ------");
                item.print();
            }
        }
        System.out.println("---------------------------------------------------------------");
    }

    public void printUnique() {
        System.out.println("---------------------------------------------------------------");
        System.out.println("List ID:" + id + "\nList name: " + listName + "\nNr. of unique items: " + nrOfUniqueItems);
        int index = 0;
        for (item item :
                this.uniqueList) {
            System.out.println("------ Item nr." + (++index) + " ------");
            item.print();
        }
        System.out.println("---------------------------------------------------------------");
    }

    public void deleteLastItem() {
        if (this.getNrOfItems() > 0) {
            this.list.remove(this.getNrOfItems() - 1);
            this.updateAttributes();
        }
    }

    public void deleteItemById(int id) {
        if(id < 0) throw new IllegalArgumentException("ID-ul nu poate fi negativ");
        this.list.removeIf(item -> item.getId() == id);
        this.updateAttributes();
    }

    public void updateAttributes() {
        this.nrOfItems = this.list.size();
        this.nrOfUniqueItems = this.uniqueList.size();
    }

    public item getItem(int id) {
        return this.list.stream()
                .filter(item -> item.getId() == id)
                .findAny()
                .orElse(null);
    }

    public int getId() {
        return id;
    }

    public int getNrOfItems() {
        return nrOfItems;
    }

    public String getListName() {
        return listName;
    }
}
