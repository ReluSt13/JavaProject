package com.company.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class AuditService {

    private static File auditServiceFile;
    private static AuditService instance;

    private AuditService() {
        auditServiceFile = new File("src/resources/auditServiceFile.csv");
        if(!auditServiceFile.exists()) {
            try{
                auditServiceFile.createNewFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static AuditService getInstance(){
        if (auditServiceFile == null) {
            instance = new AuditService();
        }
        return instance;
    }

//    public static void print(String className, String methodName) {
//        System.out.println(className + " " + methodName + " " + new Date());
//    }

    public void print(String className, String methodName) {
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

    private String formatForCsv(String className, String methodName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(className);
        stringBuilder.append(",");
        stringBuilder.append(methodName);
        stringBuilder.append(",");
        stringBuilder.append(new Date());

        return stringBuilder.toString();
    }

}
