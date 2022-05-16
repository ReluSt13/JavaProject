package com.company.service;

import com.company.model.Item;
import com.company.model.ToDoItem;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ToDoItemCsvService implements ItemService {

    private final File toDoItemFile;

    public ToDoItemCsvService() {
        this.toDoItemFile = new File("src/resources/toDoItemFile.csv");
        if(!this.toDoItemFile.exists()) {
            try{
                this.toDoItemFile.createNewFile();
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
            fileWriter = new FileWriter(this.toDoItemFile, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            boolean toDoItemAlreadyExists = getAll().stream()
                    .anyMatch(storedItem -> storedItem.getId() == itemToAdd.getId()
                    );
            if (!toDoItemAlreadyExists) {
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
            FileReader fileReader = new FileReader(this.toDoItemFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            AuditService.getInstance().print(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());

            return bufferedReader.lines()
                    .map(line -> getToDoItemFromCsvLine(line))
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

        try(FileWriter fileWriter = new FileWriter(this.toDoItemFile, false)) {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for(Item toDoItems : remainingItems) {
                bufferedWriter.write(formatForCsv(toDoItems));
                bufferedWriter.write("\n");
            }
            AuditService.getInstance().print(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());

            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void complete(int id) {
        Item itemToUpdate = getById(id);
        ((ToDoItem) itemToUpdate).complete();
        delete(getById(id));
        add(itemToUpdate);
        AuditService.getInstance().print(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());

    }

    private Item getToDoItemFromCsvLine(String line) {
        try {
            String[] values = line.split(",");
            return new ToDoItem(
                    Integer.parseInt(values[0]),
                    values[1],
                    new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault()).parse(values[2]),
                    !values[3].equals("null") ? new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault()).parse(values[3]) : null,
                    Boolean.parseBoolean(values[4]));
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
        stringBuilder.append(((ToDoItem) item).isComplete());

        return stringBuilder.toString();
    }


}
