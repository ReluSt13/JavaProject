package com.company;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

public class to_do_list extends list{
    private double percentageComplete;

    //maybe add nrOfCompletedItems...
    private void updatePercentage() {
        int nrOfItemsCompleted = 0;
        for (item item : this.getList()) {
            if (((to_do_item) item).isComplete()) nrOfItemsCompleted++;
        }
        if(getNrOfItems() == 0) {
            this.percentageComplete = 0.0;
        } else {
            this.percentageComplete = (double) nrOfItemsCompleted / this.getNrOfItems() * 100;
        }
    }

    public to_do_list(String listName, double percentageComplete) {
        super(listName);
        this.percentageComplete = percentageComplete;
    }

    public to_do_list(String listName) {
        super(listName);
        this.percentageComplete = 0;
    }

    public to_do_list(int id, String listName, int nrOfItems, Date addDate, double percentageComplete,  List<item> list) {
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
