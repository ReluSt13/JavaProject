package com.company.model;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

public class ToDoList extends Catalogue {
    private double percentageComplete;

    //maybe add nrOfCompletedItems...
    private void updatePercentage() {
        int nrOfItemsCompleted = 0;
        for (Item item : this.getList()) {
            if (((ToDoItem) item).isComplete()) nrOfItemsCompleted++;
        }
        if(getNrOfItems() == 0) {
            this.percentageComplete = 0.0;
        } else {
            this.percentageComplete = (double) nrOfItemsCompleted / this.getNrOfItems() * 100;
        }
    }

    public ToDoList(String listName, double percentageComplete) {
        super(listName);
        this.percentageComplete = percentageComplete;
    }

    public ToDoList(String listName) {
        super(listName);
        this.percentageComplete = 0;
    }

    public ToDoList(int id, String listName, int nrOfItems, Date addDate, double percentageComplete, List<Item> list) {
        super(id, listName, nrOfItems, addDate, list);
        this.percentageComplete = percentageComplete;
    }

    @Override
    public void print() {
        this.updateAttributes();
        System.out.println("Percentage complete: " + (this.percentageComplete == 0.0 ? "0" : new DecimalFormat("#.00").format(this.percentageComplete)) + "%");
        super.print();
    }

    @Override
    public void updateAttributes() {
        super.updateAttributes();
        this.updatePercentage();
    }

    public double getPercentageComplete() {
        return percentageComplete;
    }

    @Override
    public String toString() {
        return "to_do_list{" +
                "percentageComplete=" + percentageComplete +
                '}';
    }
}
