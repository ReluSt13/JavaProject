package com.company;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class shoppingListCsvService implements listService{

    private final File shoppingListFile;

    public shoppingListCsvService() {
        this.shoppingListFile = new File("src/resources/shoppingListFile.csv");
        if(!this.shoppingListFile.exists()) {
            try{
                this.shoppingListFile.createNewFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void add(list listToAdd) {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(this.shoppingListFile, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            boolean shoppingListAlreadyExists = getAll().stream()
                    .anyMatch(storedList -> storedList.getId() == listToAdd.getId()
                    );
            if (!shoppingListAlreadyExists) {
                bufferedWriter.write(formatForCsv(listToAdd));
                bufferedWriter.write("\n");
            }
            bufferedWriter.close();

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
    public List<list> getAll() {
        try {
            FileReader fileReader = new FileReader(this.shoppingListFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            return bufferedReader.lines()
                    .map(line -> getToDoListFromCsvLine(line))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return Collections.emptyList();
    }

    @Override
    public void delete(list ListToDelete) {
        List<list> remainingLists = getAll().stream()
                .filter(storedList -> storedList.getId() != ListToDelete.getId())
                .collect(Collectors.toList());

        try(FileWriter fileWriter = new FileWriter(this.shoppingListFile, false)) {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for(list shoppingLists : remainingLists) {
                bufferedWriter.write(formatForCsv(shoppingLists));
                bufferedWriter.write("\n");
            }

            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private list getToDoListFromCsvLine(String line) {
        try {
            String[] values = line.split(",");
            List<item> deserializedList = new ArrayList<>();
            int i = 0;
            for (int j = 0; j < Integer.parseInt(values[2]); j++){
                int id = Integer.parseInt(values[6 + i].substring(18));
                i++;
                String content = values[6 + i].substring(10).replace("'", "");
                i++;
                Date addDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault()).parse(values[6 + i].substring(9));
                i++;
                Date updateDate = !values[6 + i].substring(12).equals("null") ? new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault()).parse(values[6 + i].substring(12)) : null;
                i++;
                int quantity = Integer.parseInt(values[6 + i].substring(10));
                i++;
                double price = Double.parseDouble(values[6 + i].substring(7).replace("}", "").replace("]", ""));
                i++;
//                System.out.println(id);
//                System.out.println(content);
//                System.out.println(addDate);
//                System.out.println(updateDate);
//                System.out.println(quantity);
//                System.out.println(price);
                deserializedList.add(new shopping_item(id, content, addDate, updateDate, quantity, price));
            }
            return new shopping_list(
                    Integer.parseInt(values[0]),
                    values[1],
                    Integer.parseInt(values[2]),
                    new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault()).parse(values[3]),
                    Double.parseDouble(values[4]),
                    Double.parseDouble(values[5]),
                    deserializedList
            );

        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private String formatForCsv(list List) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(List.getId());
        stringBuilder.append(",");
        stringBuilder.append(List.getListName());
        stringBuilder.append(",");
        stringBuilder.append(List.getNrOfItems());
        stringBuilder.append(",");
        stringBuilder.append(List.getAddDate());
        stringBuilder.append(",");
        stringBuilder.append(((shopping_list) List).getActualPrice());
        stringBuilder.append(",");
        stringBuilder.append(((shopping_list) List).getMaxPrice());
        stringBuilder.append(",");
        stringBuilder.append(List.getList());

        return stringBuilder.toString();
    }

}
