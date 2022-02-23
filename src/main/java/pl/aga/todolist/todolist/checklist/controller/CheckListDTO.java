package pl.aga.todolist.todolist.checklist.controller;

import pl.aga.todolist.todolist.checklist.service.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CheckListDTO {

    private String name;
    private List<TaskDTO> tasksDTO = new ArrayList<>();
    private UUID id;

    public CheckListDTO (String name, UUID id, List<TaskDTO> tasksDTO) {
        this.name = name;
        this.id = id;
        this.tasksDTO = tasksDTO;
    }

    public String getName() {
        return name;
    }

    public List<TaskDTO> getTasksDTO() {
        return tasksDTO;
    }

    public UUID getId() {
        return id;
    }
}
