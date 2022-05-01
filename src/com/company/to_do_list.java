package com.company;

import java.text.DecimalFormat;

public class to_do_list extends list{
    private double percentageComplete;
    //maybe add nrOfCompletedItems...
    private void updatePercentage() {
        int nrOfItemsCompleted = 0;
        for (item item : this.list) {
            if (((to_do_item) item).isComplete()) nrOfItemsCompleted++;
        }
        this.percentageComplete = (double) nrOfItemsCompleted / this.getNrOfItems() * 100;
    }

    public to_do_list(String listName, double percentageComplete) {
        super(listName);
        this.percentageComplete = percentageComplete;
    }

    public to_do_list(String listName) {
        super(listName);
        this.percentageComplete = 0;
    }

    @Override
    public void print() {
        this.updateAttributes();
        System.out.println("Percentage complete: " + new DecimalFormat("#.00").format(this.percentageComplete) + "%");
        super.print();
    }

    @Override
    public void updateAttributes() {
        super.updateAttributes();
        this.updatePercentage();
    }

}
