package com.company;

public class Main {

    public static void main(String[] args) {
//            try {
//                    item firstItem = new item("Acesta este primul content");
//                    System.out.println(firstItem.toString());
//                    item secondItem = new item("Acesta este al doilea content");
//                    System.out.println(secondItem.toString());
//                    firstItem.updateContent("Acesta este noul content");
//                    System.out.println(firstItem.toString());
//                    list firstList = new list("First List");
//                    firstList.addToList(firstItem);
//                    firstList.addToList(secondItem);
//                    firstList.print();
//
//                    firstList.getItem(1).updateContent("wow");
//                    firstList.print();
//
//            } catch (Exception e) {
//                    System.out.println(e.getMessage());
//            }
            
        try {
                to_do_item tdi = new to_do_item("first to do item");
                //tdi.print();
                tdi.complete();
                //tdi.print();
                tdi.updateContent("new tdi content");
                //tdi.print();

                to_do_item tdi2 = new to_do_item("second to do item");
                to_do_item tdi3 = new to_do_item("third to do item");
                to_do_item tdi4 = new to_do_item("fourth to do item");
                to_do_item tdi5 = new to_do_item("fifth to do item");

                to_do_list tdl = new to_do_list("TDLIST");
                tdl.addToList(tdi);
                tdl.addToList(tdi2);
                tdl.addToList(tdi3);
                tdl.addToList(tdi4);
                tdl.addToList(tdi5);
                tdl.addToList(new to_do_item("sixth td item"));
                tdl.addToList(new to_do_item("seventh td item"));
                tdi3.complete();
                tdl.updateAttributes();
                tdl.print();

                ((to_do_item)tdl.getItem(4)).complete();
                tdl.updateAttributes();
                tdl.print();
                tdl.deleteLastItem();
                tdl.deleteLastItem();
                tdl.print();

        } catch (Exception e) {
                System.out.println(e.getMessage());
        }
    }
}
