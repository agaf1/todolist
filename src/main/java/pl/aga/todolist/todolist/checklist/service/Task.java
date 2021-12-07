package pl.aga.todolist.todolist.checklist.service;

import java.util.UUID;

public class Task {
    private String name;
    private boolean execute;
    private UUID idTask;

    public Task(String name, boolean execute, UUID idTask){
        this.name = name;
        this.execute = execute;
        this.idTask = idTask;
    }

    public String getName(){
        return name;
    }
    public boolean getExecute(){
        return execute;
    }
    public UUID getIdTask(){
        return idTask;
    }


    public void changeState(){
        if(execute == false){
            execute = true;
        }
        else {execute = false;}
    }
}
