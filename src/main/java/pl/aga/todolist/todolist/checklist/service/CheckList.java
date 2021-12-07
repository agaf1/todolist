package pl.aga.todolist.todolist.checklist.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CheckList {
    private String name;
    private List<Task> tasks = new ArrayList<>();
    private UUID id;

    public CheckList(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void deleteTask(Task task){
        tasks.remove(task);
    }

    public List<Task> getAllTasks() {
        return tasks;
    }
}
