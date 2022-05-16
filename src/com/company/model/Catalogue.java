package com.company.model;

import java.util.*;

public class Catalogue {
    private int id;
    private static int id_max = 0;
    private int nrOfItems;
    private int nrOfUniqueItems;
    private Date addDate;
    private String listName;
    private List<Item> list = new ArrayList<>();
    private Set<Item> uniqueList = new HashSet<>();

    public Catalogue(String listName) {
        this.id = id_max++;
        this.nrOfItems = 0;
        this.listName = listName;
        this.addDate = new Date();
    }

    public Catalogue(int id, String listName, int nrOfItems, Date addDate, List<Item> list) {
        this.id = id;
        this.listName = listName;
        this.nrOfItems = nrOfItems;
        this.addDate = addDate;
        this.list = list;
    }

    public void addToList(Item itemToBeAdded) {
        this.list.add(itemToBeAdded);
        this.uniqueList.add(itemToBeAdded);
        this.updateAttributes();
    }

    public void print() {
        System.out.println("List ID: " + this.id + "\nList name: " + this.listName + "\nCreate date: " + this.addDate + "\nNr. of items: " + this.nrOfItems);
        if(this.getNrOfItems() == 0) System.out.println("The list is empty :(");
        else {
            int index = 0;
            for (Item item :
                    this.list) {
                System.out.println("------ Item nr." + (++index) + " ------");
                item.print();
            }
        }
        System.out.println("---------------------------------------------------------------");
    }

    public void printUnique() {
        System.out.println("List ID:" + id + "\nList name: " + listName + "\nCreate date: " + this.addDate + "\nNr. of unique items: " + this.nrOfUniqueItems);
        int index = 0;
        for (Item item :
                this.uniqueList) {
            System.out.println("------ Item nr." + (++index) + " ------");
            item.print();
        }
        System.out.println("---------------------------------------------------------------");
    }

    public void deleteLastItem() {
        if (this.getNrOfItems() > 0) {
            this.list.remove(this.getNrOfItems() - 1);
            java.util.List<Item> unique = new ArrayList<>(uniqueList);
            this.uniqueList.remove(unique.get(unique.size() - 1));
            this.updateAttributes();
        }
    }

    public void deleteItemById(int id) {
        if(id < 0) throw new IllegalArgumentException("ID-ul nu poate fi negativ");
        this.list.removeIf(item -> item.getId() == id);
        this.uniqueList.removeIf(item -> item.getId() == id);
        this.updateAttributes();
    }

    public void updateAttributes() {
        this.nrOfItems = this.list.size();
        this.nrOfUniqueItems = this.uniqueList.size();
    }

    public Item getItem(int id) {
        return this.list.stream()
                .filter(item -> item.getId() == id)
                .findAny()
                .orElse(null);
    }

    public void updateListName(String newName) {
        this.listName = newName;
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

    public Date getAddDate() {
        return addDate;
    }

    public List<Item> getList() {
        return list;
    }

    public Set<Item> getUniqueList() {
        return uniqueList;
    }

    public int getNrOfUniqueItems() {
        return nrOfUniqueItems;
    }
}
