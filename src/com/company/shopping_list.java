package com.company;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class shopping_list extends list{
    private double maxPrice;
    private double actualPrice;
    private double uniqueActualPrice;
    private List<item> sortedList = new ArrayList<>();
    private void updateActualPrice() {
        this.actualPrice = 0;
        this.uniqueActualPrice = 0;
        this.getList().forEach(shopItem -> this.actualPrice += ((shopping_item) shopItem).getTotalPrice());
        this.getUniqueList().forEach(shopItem -> this.uniqueActualPrice += ((shopping_item) shopItem).getTotalPrice());

    }


    public shopping_list(String listName, double maxPrice) {
        super(listName);
        this.maxPrice = maxPrice;
        this.actualPrice = 0;
        this.uniqueActualPrice = 0;
    }

    public shopping_list(int id, String listName, int nrOfItems, Date addDate, double actualPrice, double maxPrice, List<item> list) {
        super(id, listName, nrOfItems, addDate, list);
        this.maxPrice = maxPrice;
        this.actualPrice = actualPrice;
    }

    public void updateMaxPrice(double newMaxPrice) {
        this.maxPrice = newMaxPrice;
    }

    public void printSorted() {
        this.updateAttributes();
        System.out.println("Maximum Price: $" + new DecimalFormat("#.00").format(this.maxPrice));
        System.out.println("Actual Price: $" + new DecimalFormat("#.00").format(this.actualPrice));
        System.out.println("List ID: " + this.getId() + "\nList name: " + this.getListName() + "\nCreate date: " + this.getAddDate() + "\nNr. of items: " + this.getNrOfItems());
        if(this.getNrOfItems() == 0) System.out.println("The list is empty :(");
        else {
            int index = 0;
            for (item item :
                    this.sortedList) {
                System.out.println("------ Item nr." + (++index) + " ------");
                item.print();
            }
        }
        System.out.println("---------------------------------------------------------------");
    }

    @Override
    public void print() {
        this.updateAttributes();
        System.out.println("Maximum Price: $" + new DecimalFormat("#.00").format(this.maxPrice));
        System.out.println("Actual Price: $" + new DecimalFormat("#.00").format(this.actualPrice));
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
    public void addToList(item itemToBeAdded) {
        this.updateAttributes();
        if(((shopping_item) itemToBeAdded).getTotalPrice() + this.actualPrice > this.maxPrice) throw new IllegalArgumentException("Nu poti depasi pretul maxim");
        super.addToList(itemToBeAdded);
        this.sortedList.add(itemToBeAdded);
        sortedList.sort((item1, item2) -> (int) (((shopping_item) item1).getTotalPrice() - ((shopping_item) item2).getTotalPrice()));
    }
    @Override
    public void deleteLastItem() {
        super.deleteLastItem();
        this.sortedList.remove(this.getNrOfItems() - 1);
        this.updateAttributes();
    }
    @Override
    public void deleteItemById(int id) {
        super.deleteItemById(id);
        if(id < 0) throw new IllegalArgumentException("ID-ul nu poate fi negativ");
        this.sortedList.removeIf(item -> item.getId() == id);
        this.updateAttributes();
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public double getActualPrice() {
        return actualPrice;
    }
}
