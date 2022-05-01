package com.company;

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
