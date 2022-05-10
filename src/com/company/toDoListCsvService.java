package com.company;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class toDoListCsvService implements listService{

    private final File toDoListFile;

    public toDoListCsvService() {
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
    public void add(list listToAdd) {

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
            FileReader fileReader = new FileReader(this.toDoListFile);
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

        try(FileWriter fileWriter = new FileWriter(this.toDoListFile, false)) {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for(list toDoLists : remainingLists) {
                bufferedWriter.write(formatForCsv(toDoLists));
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
                int id = Integer.parseInt(String.valueOf(values[5 + i].charAt(values[5 + i].length() - 1)));
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
                deserializedList.add(new to_do_item(id, content, addDate, updateDate, complete));
            }
            return new to_do_list(
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
        stringBuilder.append(((to_do_list) List).getPercentageComplete());
        stringBuilder.append(",");
        stringBuilder.append(List.getList());

        return stringBuilder.toString();
    }
}
