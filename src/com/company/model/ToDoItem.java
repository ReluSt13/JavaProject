package com.company.model;

import java.util.Date;

public class ToDoItem extends Item {
    private boolean isCompleted;

    public ToDoItem(String content, boolean isCompleted) {
        super(content);
        this.isCompleted = isCompleted;
    }

    public ToDoItem(String content) {
        super(content);
        this.isCompleted = false;
    }

    public ToDoItem(int id, String content, Date addDate, Date updateDate, boolean isCompleted) {
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

    @Override
    public String toString() {
        return "to_do_item{" +
                "id=" + this.getId() +
                ", content='" + this.getContent() + '\'' +
                ", addDate=" + this.getAddDate() +
                ", updateDate=" + this.getUpdateDate() +
                ", isCompleted=" + isCompleted +
                '}';
    }
}
