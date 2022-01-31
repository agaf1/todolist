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

    public CheckList(String name, UUID id, List<Task> tasks) {
        this.name = name;
        this.id = id;
        this.tasks = tasks;
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

    public void deleteTask(Task task) {
        tasks.remove(task);
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name)
                .append(",")
                .append(id);
        if (tasks.size() > 0) {
            sb.append("\n");
            for (Task t : tasks) {
                sb.append(t.getName())
                        .append(",")
                        .append(t.getExecute())
                        .append(",")
                        .append(t.getIdTask())
                        .append("\n");
            }
        }
        return sb.toString().trim();

    }
}
