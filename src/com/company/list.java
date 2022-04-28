package com.company;

import java.util.ArrayList;
import java.util.List;

public class list {
    private int id;
    private static int id_max = 0;
    private int nrOfItems;
    private String listName;
    private List<item> list = new ArrayList<>();

    public list(String listName) {
        this.id = id_max++;
        this.nrOfItems = 0;
        this.listName = listName;
    }
    public void addToList(item itemToBeAdded) {
        this.list.add(itemToBeAdded);
        this.nrOfItems = this.list.size();
    }

    public void printList() {
        System.out.println("---------------------------------------------------------------");
        System.out.println("List ID:" + id + "\nList name: " + listName + "\nNr. of items: " + nrOfItems);
        int index = 0;
        for (item item :
                this.list) {
            System.out.println("------ Item nr." + (++index) + " ------");
            item.printItem();
        }
        System.out.println("---------------------------------------------------------------");
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
