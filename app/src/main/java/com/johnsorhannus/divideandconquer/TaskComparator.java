package com.johnsorhannus.divideandconquer;

import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {
    public int compare(Task first, Task second) {
        return first.getDueDate().compareTo(second.getDueDate());
    }
}
