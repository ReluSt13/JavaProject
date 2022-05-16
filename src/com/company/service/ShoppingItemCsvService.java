package com.company.service;

import com.company.model.Item;
import com.company.model.ShoppingItem;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ShoppingItemCsvService implements ItemService {

    private final File shoppingItemFile;

    public ShoppingItemCsvService() {
        this.shoppingItemFile = new File("src/resources/shoppingItemFile.csv");
        if(!this.shoppingItemFile.exists()) {
            try{
                this.shoppingItemFile.createNewFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void add(Item itemToAdd) {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(this.shoppingItemFile, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            boolean shoppingItemAlreadyExists = getAll().stream()
                    .anyMatch(storedItem -> storedItem.getId() == itemToAdd.getId()
                    );
            if (!shoppingItemAlreadyExists) {
                bufferedWriter.write(formatForCsv(itemToAdd));
                bufferedWriter.write("\n");
            }
            bufferedWriter.close();
            AuditService.getInstance().print(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

        }
    }

    @Override
    public List<Item> getAll() {
        try {
            FileReader fileReader = new FileReader(this.shoppingItemFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            AuditService.getInstance().print(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());

            return bufferedReader.lines()
                    .map(line -> getShoppingItemFromCsvLine(line))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return Collections.emptyList();
    }

    @Override
    public void delete(Item itemToDelete) {
        List<Item> remainingItems = getAll().stream()
                .filter(storedItem -> storedItem.getId() != itemToDelete.getId())
                .collect(Collectors.toList());

        try(FileWriter fileWriter = new FileWriter(this.shoppingItemFile, false)) {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for(Item shopItems : remainingItems) {
                bufferedWriter.write(formatForCsv(shopItems));
                bufferedWriter.write("\n");
            }
            AuditService.getInstance().print(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());

            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public void updatePrice(int id, double newPrice) {
        Item itemToUpdate = getById(id);
        ((ShoppingItem) itemToUpdate).updatePrice(newPrice);
        delete(getById(id));
        add(itemToUpdate);
        AuditService.getInstance().print(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());

    }

    public void updateQuantity(int id, int newQuantity) {
        Item itemToUpdate = getById(id);
        ((ShoppingItem) itemToUpdate).updateQuantity(newQuantity);
        delete(getById(id));
        add(itemToUpdate);
        AuditService.getInstance().print(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());

    }

    private Item getShoppingItemFromCsvLine(String line) {
        try {
            String[] values = line.split(",");
            return new ShoppingItem(
                    Integer.parseInt(values[0]),
                    values[1],
                    new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault()).parse(values[2]),
                    !values[3].equals("null") ? new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault()).parse(values[3]) : null,
                    Integer.parseInt(values[4]),
                    Double.parseDouble(values[5]));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private String formatForCsv(Item item) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(item.getId());
        stringBuilder.append(",");
        stringBuilder.append(item.getContent());
        stringBuilder.append(",");
        stringBuilder.append(item.getAddDate());
        stringBuilder.append(",");
        stringBuilder.append(item.getUpdateDate());
        stringBuilder.append(",");
        stringBuilder.append(((ShoppingItem) item).getQuantity());
        stringBuilder.append(",");
        stringBuilder.append(((ShoppingItem) item).getPrice());

        return stringBuilder.toString();
    }

}
