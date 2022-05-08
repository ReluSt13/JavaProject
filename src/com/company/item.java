package com.company;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class item {
    private int id;
    private static int id_max = 0;
    private String content;
    private Date addDate;
    private Date updateDate;

    public item(int id, String content, Date addDate, Date updateDate) {
        this.id = id;
        this.content = content;
        this.addDate = addDate;
        this.updateDate = updateDate;
    }

    public item(String content, Date addDate, Date updateDate) {
        this.id = id_max++;
        this.content = content;
        this.addDate = addDate;
        this.updateDate = updateDate;
    }

    public item(String content){
        this(content, new Date(), null);
    }

    public void updateContent(String newContent) {
        this.content = newContent;
        this.updateDate = new Date();
    }

    public void print() {
        System.out.println("ID: " + id + "\nContent: " + content + "\nAdd Date: " + addDate + "\nUpdate Date: " + updateDate);
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Date getAddDate() {
        return addDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "item{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", addDate=" + addDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
