package com.company.model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ShoppingList extends Catalogue {
    private double maxPrice;
    private double actualPrice;
    private double uniqueActualPrice;
//    private List<Item> sortedList = new ArrayList<>();
    private Set<ShoppingItem> sortedSet = new TreeSet<>(((o1, o2) -> (int) (o1.getTotalPrice() - o2.getTotalPrice())));
    private void updateActualPrice() {
        this.actualPrice = 0;
        this.uniqueActualPrice = 0;
        this.getList().forEach(shopItem -> this.actualPrice += ((ShoppingItem) shopItem).getTotalPrice());
        this.sortedSet.forEach(shopItem -> this.uniqueActualPrice += shopItem.getTotalPrice());

    }

    public ShoppingList(String listName, double maxPrice) {
        super(listName);
        this.maxPrice = maxPrice;
        this.actualPrice = 0;
        this.uniqueActualPrice = 0;
    }

    public ShoppingList(int id, String listName, Date addDate, double maxPrice, double actualPrice){
        super(id, listName, addDate);
        this.maxPrice = maxPrice;
        this.actualPrice = actualPrice;
    }

    public ShoppingList(int id, String listName, int nrOfItems, Date addDate, double actualPrice, double maxPrice, List<Item> list) {
        super(id, listName, nrOfItems, addDate, list);
        this.maxPrice = maxPrice;
        this.actualPrice = actualPrice;
    }

    public ShoppingList(int id, String listName, double maxPrice) {
        super(id, listName);
        this.maxPrice = maxPrice;
        this.actualPrice = 0;
    }

    public void updateMaxPrice(double newMaxPrice) {
        this.maxPrice = newMaxPrice;
    }

    public void printSortedSet() {
        this.updateAttributes();
        System.out.println("Maximum Price: $" + new DecimalFormat("#.00").format(this.maxPrice));
        System.out.println("Actual Price: $" + new DecimalFormat("#.00").format(this.uniqueActualPrice));
        System.out.println("List ID: " + this.getId() + "\nList name: " + this.getListName() + "\nCreate date: " + this.getAddDate() + "\nNr. of items: " + this.sortedSet.size());
        if(this.sortedSet.size() == 0) System.out.println("The sorted set is empty :(");
        else {
            AtomicInteger index = new AtomicInteger();
            this.sortedSet.forEach(shopItem -> {
                System.out.println("------ Item nr." + (index.incrementAndGet()) + " ------");
                shopItem.print();
            });
        }
        System.out.println("---------------------------------------------------------------");
    }

    @Override
    public void print() {
        this.updateAttributes();
        System.out.println("Maximum Price: $" + new DecimalFormat("#.00", new DecimalFormatSymbols(Locale.US)).format(this.maxPrice));
        System.out.println("Actual Price: $" + new DecimalFormat("#.00", new DecimalFormatSymbols(Locale.US)).format(this.actualPrice));
        super.print();
    }
    @Override
    public void printUnique() {
        this.updateAttributes();
        System.out.println("Maximum Price: $" + new DecimalFormat("#.00").format(this.maxPrice));
        System.out.println("Actual Price: $" + new DecimalFormat("#.00").format(this.uniqueActualPrice));
        super.printUnique();
    }

    @Override
    public void updateAttributes() {
        super.updateAttributes();
        this.updateActualPrice();
    }

    @Override
    public void addToList(Item itemToBeAdded) {
        this.updateAttributes();
        if(((ShoppingItem) itemToBeAdded).getTotalPrice() + this.actualPrice > this.maxPrice) throw new IllegalArgumentException("Nu poti depasi pretul maxim");
        this.sortedSet.add(((ShoppingItem) itemToBeAdded));
        super.addToList(itemToBeAdded);
//        this.sortedList.add(itemToBeAdded);
//        sortedList.sort((item1, item2) -> (int) (((ShoppingItem) item1).getTotalPrice() - ((ShoppingItem) item2).getTotalPrice()));
    }
    @Override
    public void deleteLastItem() {
        ShoppingItem lastItem = (ShoppingItem) this.getList().get(this.getNrOfItems() - 1);
        super.deleteLastItem();
        this.sortedSet.remove(lastItem);
//        this.sortedList.remove(this.getNrOfItems() - 1);
        this.updateAttributes();
    }
    @Override
    public void deleteItemById(int id) {
        super.deleteItemById(id);
        if(id < 0) throw new IllegalArgumentException("ID-ul nu poate fi negativ");
        this.sortedSet.removeIf(item -> item.getId() == id);
        this.updateAttributes();
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public double getUniqueActualPrice() {
        return uniqueActualPrice;
    }
}
