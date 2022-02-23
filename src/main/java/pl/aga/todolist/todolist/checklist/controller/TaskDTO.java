package pl.aga.todolist.todolist.checklist.controller;

import java.util.UUID;

public class TaskDTO {

    private String name;
    private boolean execute;
    private UUID idTask;

    public TaskDTO (String name, boolean execute, UUID idTask){
        this.name = name;
        this.execute = execute;
        this.idTask = idTask;
    }

    public String getName() {
        return name;
    }

    public boolean isExecute() {
        return execute;
    }

    public UUID getIdTask() {
        return idTask;
    }
}
