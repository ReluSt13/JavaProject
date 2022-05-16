package com.company.service;

import com.company.model.Catalogue;
import com.company.model.Item;
import com.company.model.ToDoItem;
import com.company.model.ToDoList;

import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ToDoListCsvService implements ListService {

    private final File toDoListFile;

    public ToDoListCsvService() {
        this.toDoListFile = new File("src/resources/toDoListFile.csv");
        if(!this.toDoListFile.exists()) {
            try{
                this.toDoListFile.createNewFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void add(Catalogue listToAdd) {

        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(this.toDoListFile, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            boolean toDoListAlreadyExists = getAll().stream()
                    .anyMatch(storedList -> storedList.getId() == listToAdd.getId()
                    );
            if (!toDoListAlreadyExists) {
                bufferedWriter.write(formatForCsv(listToAdd));
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
    public List<Catalogue> getAll() {
        try {
            FileReader fileReader = new FileReader(this.toDoListFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            AuditService.getInstance().print(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());
            return bufferedReader.lines()
                    .map(line -> getToDoListFromCsvLine(line))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return Collections.emptyList();
    }

    @Override
    public void delete(Catalogue ListToDelete) {

        List<Catalogue> remainingLists = getAll().stream()
                .filter(storedList -> storedList.getId() != ListToDelete.getId())
                .collect(Collectors.toList());

        try(FileWriter fileWriter = new FileWriter(this.toDoListFile, false)) {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for(Catalogue toDoLists : remainingLists) {
                bufferedWriter.write(formatForCsv(toDoLists));
                bufferedWriter.write("\n");
            }
            AuditService.getInstance().print(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void completeItemFromList(int idList, int idItem) {
        ToDoList updatedToDoList = ((ToDoList) getById(idList));
        ((ToDoItem) updatedToDoList.getItem(idItem)).complete();
        updatedToDoList.updateAttributes();
        delete(getById(idList));
        add(updatedToDoList);
        AuditService.getInstance().print(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());

    }

    private Catalogue getToDoListFromCsvLine(String line) {
        try {
            String[] values = line.split(",");
            List<Item> deserializedList = new ArrayList<>();
            int i = 0;
            for (int j = 0; j < Integer.parseInt(values[2]); j++){
                int id = Integer.parseInt(values[5 + i].substring(15));
                i++;
                String content = values[5 + i].substring(10).replace("'", "");
                i++;
                Date addDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault()).parse(values[5 + i].substring(9));
                i++;
                Date updateDate = !values[5 + i].substring(12).equals("null") ? new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault()).parse(values[5 + i].substring(12)) : null;
                i++;
                boolean complete = Boolean.parseBoolean(values[5 + i].substring(13, values[5 + i].indexOf("}")));
                i++;
//                System.out.println(id);
//                System.out.println(content);
//                System.out.println(addDate);
//                System.out.println(updateDate);
//                System.out.println(complete);
                deserializedList.add(new ToDoItem(id, content, addDate, updateDate, complete));
            }
            return new ToDoList(
                    Integer.parseInt(values[0]),
                    values[1],
                    Integer.parseInt(values[2]),
                    new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault()).parse(values[3]),
                    Double.parseDouble(values[4]),
                    deserializedList
            );

        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private String formatForCsv(Catalogue List) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(List.getId());
        stringBuilder.append(",");
        stringBuilder.append(List.getListName());
        stringBuilder.append(",");
        stringBuilder.append(List.getNrOfItems());
        stringBuilder.append(",");
        stringBuilder.append(List.getAddDate());
        stringBuilder.append(",");
        stringBuilder.append(new DecimalFormat("#.00", new DecimalFormatSymbols(Locale.US)).format(((ToDoList) List).getPercentageComplete()));
        stringBuilder.append(",");
        stringBuilder.append(List.getList());

        return stringBuilder.toString();
    }
}
