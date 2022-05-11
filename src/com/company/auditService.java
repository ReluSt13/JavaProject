package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class auditService {

    private static File auditServiceFile;
    private static auditService instance;

    private auditService() {
        auditServiceFile = new File("src/resources/auditServiceFile.csv");
        if(!auditServiceFile.exists()) {
            try{
                auditServiceFile.createNewFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static auditService getInstance(){
        if (auditServiceFile == null) {
            instance = new auditService();
        }
        return instance;
    }

//    public static void print(String className, String methodName) {
//        System.out.println(className + " " + methodName + " " + new Date());
//    }

    public static void print(String className, String methodName) {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(auditServiceFile, true);
            bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(formatForCsv(className, methodName));
            bufferedWriter.write("\n");

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

    private static String formatForCsv(String className, String methodName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(className);
        stringBuilder.append(",");
        stringBuilder.append(methodName);
        stringBuilder.append(",");
        stringBuilder.append(new Date());

        return stringBuilder.toString();
    }

}
