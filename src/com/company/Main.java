package com.company;

public class Main {

    public static void main(String[] args) {
        item firstItem = new item("Acesta este primul content");
        System.out.println(firstItem.toString());
        item secondItem = new item("Acesta este al doilea content");
        System.out.println(secondItem.toString());
        firstItem.updateContent("Acesta este noul content");
        System.out.println(firstItem.toString());
        list firstList = new list("First List");
        firstList.addToList(firstItem);
        firstList.printList();
        firstList.addToList(secondItem);
        firstList.printList();
    }
}
