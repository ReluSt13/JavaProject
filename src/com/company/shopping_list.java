package com.company;

import java.text.DecimalFormat;

public class shopping_list extends list{
    private double maxPrice;
    private double actualPrice;
    private void updateActualPrice() {
        this.actualPrice = 0;
        this.list.forEach(shopItem -> this.actualPrice += ((shopping_item)shopItem).getTotalPrice());
    }


    public shopping_list(String listName, double maxPrice) {
        super(listName);
        this.maxPrice = maxPrice;
        this.actualPrice = 0;
    }

    public void updateMaxPrice(double newMaxPrice) {
        this.maxPrice = newMaxPrice;
    }

    @Override
    public void print() {
        this.updateAttributes();
        System.out.println("Maximum Price: $" + new DecimalFormat("#.00").format(this.maxPrice));
        System.out.println("Actual Price: $" + new DecimalFormat("#.00").format(this.actualPrice));
        super.print();
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
    }
}
