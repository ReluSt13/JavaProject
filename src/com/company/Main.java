package com.company;

import com.company.model.*;
import com.company.service.ShoppingItemCsvService;
import com.company.service.ShoppingListCsvService;
import com.company.service.ToDoItemCsvService;
import com.company.service.ToDoListCsvService;

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
//                to_do_item tdi = new to_do_item("first to do item");
//                //tdi.print();
//                tdi.complete();
//                //tdi.print();
//                tdi.updateContent("new tdi content");
//                //tdi.print();
//
//                to_do_item tdi2 = new to_do_item("second to do item");
//                to_do_item tdi3 = new to_do_item("third to do item");
//                to_do_item tdi4 = new to_do_item("fourth to do item");
//                to_do_item tdi5 = new to_do_item("fifth to do item");
//
//                to_do_list tdl = new to_do_list("TDLIST");
//                tdl.addToList(tdi);
//                tdl.addToList(tdi2);
//                tdl.addToList(tdi3);
//                tdl.addToList(tdi4);
//                tdl.addToList(tdi5);
//                tdl.addToList(new to_do_item("sixth td item"));
//                tdl.addToList(new to_do_item("seventh td item"));
//                tdi3.complete();
//                tdl.updateAttributes();
//                tdl.print();
//
//                ((to_do_item)tdl.getItem(4)).complete();
//                tdl.updateAttributes();
//                tdl.print();
//                tdl.deleteLastItem();
//                tdl.deleteLastItem();
//                tdl.print();
//            shopping_item shopItem = new shopping_item("tomato", 2, 5.3);
//            shopping_list shopList = new shopping_list("monday shopping list", 105.5);
//            shopList.addToList(shopItem);
//            shopList.addToList(new shopping_item("potato", 10, 2.1));
//            shopList.addToList(new shopping_item("apple", 4, 3.1));
//            shopList.addToList(new shopping_item("pear", 4, 1.1));
//            shopList.addToList(shopItem);
//
//            shopList.deleteItemById(2);
//            //shopList.print();
//            //shopList.printSorted();
//            //shopList.printUnique();
//            shopList.deleteLastItem();
//            //shopList.printUnique();
//
//            notebook agenda = notebook.getInstance();
//            agenda.addList(shopList);
//            to_do_list todoList = new to_do_list("tuesday to do list");
//            todoList.addToList(new to_do_item("run 5km"));
//            todoList.addToList(new to_do_item("drink 2.5 liters of water"));
//            todoList.addToList(new to_do_item("read 30 pages of a book"));
//            ((to_do_item)todoList.getItem(5)).complete();
//            agenda.addList(todoList);
//            list emptyList = new list("Empty list");
//            agenda.addList(emptyList);
//            agenda.print();
//            agenda.deleteListById(2);
//            agenda.print();
//            ((shopping_list) agenda.getListById(0)).updateMaxPrice(205.75);
//            agenda.print();
//            agenda.deleteLastList();
//            agenda.print();
//            ((shopping_list) agenda.getListById(0)).printSorted();

//            shopping_item shopItem = new shopping_item("tomato", 3, 1.2);
//            shopItem.updateContent("tomat");
//
//            shoppingItemCsvService shopItemService = new shoppingItemCsvService();
//            shopItemService.add(shopItem);
//            shopItemService.add(new shopping_item("potato", 10, 0.5));
//            shopItemService.add(new shopping_item("garlic", 7, 0.1));
//            shopItemService.add(new shopping_item("onion", 5, 0.4));
//            shopItemService.getItemsBetweenDates(new Date(new Date().getTime() - 60 * 60 * 1000), new Date(new Date().getTime() - 30 * 60 * 1000)).forEach(i -> i.print());
//            shopItemService.updateContent(1, "potat");
//            shopItemService.updateQuantity(2, 9);
//            shopItemService.getAll().forEach(item::print);
//
//            toDoItemCsvService toDoItemService = new toDoItemCsvService();
//            toDoItemService.add(new to_do_item("run 5km"));
//            toDoItemService.updateContent(4, "run 6km");
//            toDoItemService.add(new to_do_item("do 50 push-ups"));
//            toDoItemService.getAll().forEach(item::print);
//            toDoItemService.complete(5);
//            toDoItemService.getAll().forEach(item::print);

//            to_do_list tdL = new to_do_list("to do list");
//            tdL.addToList(new to_do_item("run 10km in under 55mins"));
//            tdL.addToList(new to_do_item("run 5km in under 20mins", true));
//            toDoListCsvService toDoListService = new toDoListCsvService();
//            toDoListService.add(tdL);
//            toDoListService.add(new to_do_list("another to do list"));
//            toDoListService.getById(0).addToList(new to_do_item("do 10 chin-ups"));
//            ((to_do_item) toDoListService.getById(0).getItem(0)).complete();
////            toDoListService.getAll().forEach(list::print);
//            toDoListService.updateName(0, "TO DO LIST");
////            toDoListService.getAll().forEach(list::print);
//
            ShoppingListCsvService shoppingListService = new ShoppingListCsvService();
            ShoppingItem shopItem = new ShoppingItem("tomato", 2, 5.3);
            ShoppingList shopList = new ShoppingList("monday shopping list", 105.5);
            shopList.addToList(shopItem);
            shopList.addToList(new ShoppingItem("potato", 10, 2.1));
            shopList.addToList(new ShoppingItem("apple", 4, 3.1));
            shoppingListService.add(shopList);
//            shoppingListService.getAll().forEach(list::print);
            shoppingListService.add(new ShoppingList("tuesday shopping list", 200.3));
//            shoppingListService.getAll().forEach(list::print);
//            shoppingListService.delete(shoppingListService.getById(0));
            shoppingListService.addItem(1, new ShoppingItem("flower", 33, 0.1));
            shoppingListService.addItem(0, new ShoppingItem("carrot", 12, 0.31));
            shoppingListService.getAll().forEach(Catalogue::print);

            ToDoListCsvService toDoListService = new ToDoListCsvService();
            ToDoList tdL = new ToDoList("to do list");
            tdL.addToList(new ToDoItem("run 10km in under 55mins"));
            tdL.addToList(new ToDoItem("run 5km in under 20mins", true));
            toDoListService.add(tdL);
            toDoListService.addItem(2, new ToDoItem("do 50 sit-up"));
            shoppingListService.updateMaxPrice(1, 321.69);
            toDoListService.completeItemFromList(2, 7);
            shoppingListService.updateQuantityOfItemFromList(0, 0, 6);

            ShoppingItemCsvService shopItemService = new ShoppingItemCsvService();
            ToDoItemCsvService toDoItemService = new ToDoItemCsvService();
            shopItemService.add(new ShoppingItem("mar", 2, 1.2));
            toDoItemService.add(new ToDoItem("100kg deadlift", true));



            ShoppingList sl = new ShoppingList("shoplist test", 2136.7);
            sl.addToList(new ShoppingItem("item1", 3, 2.1));
            sl.addToList(new ShoppingItem("item2", 1, 1.2));
            sl.addToList(new ShoppingItem("ITEM3", 3, 1.23));
            sl.addToList(new ShoppingItem("item4", 1, 0.13));
            sl.addToList(new ShoppingItem("item5", 7, 12.13));
            sl.addToList(new ShoppingItem("item6", 6, 3.13));
            System.out.println("##################################################################################");
            sl.print();
            sl.printSortedSet();
            sl.deleteLastItem();
            System.out.println("##################################################################################");
            sl.print();
            sl.printSortedSet();
            shoppingListService.add(sl);

        } catch (Exception e) {
                System.out.println(e.getMessage());
        }
    }
}
