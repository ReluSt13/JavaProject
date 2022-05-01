package com.company;

public class shopping_item extends item{
    private int quantity;
    private double price;

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
    }

    public void updatePrice(double newPrice) {
        this.price = newPrice;
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
        System.out.println("Total price: $" + this.getTotalPrice());
    }
}
