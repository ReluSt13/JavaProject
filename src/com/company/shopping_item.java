package com.company;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;

public class shopping_item extends item{
    private int quantity;
    private double price;

    public shopping_item(int id, String content, Date addDate, Date updateDate, int quantity, double price) {
        super(id, content, addDate, updateDate);
        this.quantity = quantity;
        this.price = price;
    }

    public shopping_item(String content, int quantity, double price) {
        super(content);
        this.quantity = quantity;
        this.price = price;
    }

    public shopping_item(String content) {
        super(content);
        this.quantity = 0;
        this.price = 0;
    }

    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
        this.setUpdateDate(new Date());
    }

    public void updatePrice(double newPrice) {
        this.price = newPrice;
        this.setUpdateDate(new Date());
    }

    public double getTotalPrice() {
        return (double) this.quantity * this.price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public void print() {
        super.print();
        System.out.println("Price: $" + this.price);
        System.out.println("Quantity: " + this.quantity);
        System.out.println("Total price: $" + new DecimalFormat("#.00").format(this.getTotalPrice()));
    }

    @Override
    public String toString() {
        return "shopping_item{" +
                "id=" + this.getId() +
                ", content='" + this.getContent() + '\'' +
                ", addDate=" + this.getAddDate() +
                ", updateDate=" + this.getUpdateDate() +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
