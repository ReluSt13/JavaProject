package com.company;

import java.util.Date;

public class to_do_item extends item {
    private boolean isCompleted;

    public to_do_item(String content, boolean isCompleted) {
        super(content);
        this.isCompleted = isCompleted;
    }

    public to_do_item(String content) {
        super(content);
        this.isCompleted = false;
    }

    public to_do_item(int id, String content, Date addDate, Date updateDate, boolean isCompleted) {
        super(id, content, addDate, updateDate);
        this.isCompleted = isCompleted;
    }

    @Override
    public void print() {
        super.print();
        System.out.println("Task completed: " + this.isCompleted);
    }

    public void complete() {
        this.isCompleted = true;
    }

    public boolean isComplete() {
        return this.isCompleted;
    }
}
