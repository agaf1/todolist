package pl.aga.todolist.todolist.checklist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import pl.aga.todolist.todolist.checklist.service.CheckList;
import pl.aga.todolist.todolist.checklist.service.CheckListService;
import pl.aga.todolist.todolist.checklist.service.TaskService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
public class CheckListController {

    @Autowired
    private CheckListService checkListService;
    @Autowired
    private TaskService taskService;

    @RequestMapping(
            method = RequestMethod.GET,
            path = "checklist/all",
            produces="application/json"
    )
    @ResponseBody
    public List<CheckList> getAll(){
        return checkListService.getAll();
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "checklist/create/{name}",
            produces="application/json"
    )
    public void create(@PathVariable @NotNull String name){
        checkListService.create(name);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "checklist/tasks/add/{idCheckList}/{taskName}",
            produces="application/json"
    )
    public void addTask (@PathVariable String idCheckList, @PathVariable String taskName){
        taskService.addTask(UUID.fromString(idCheckList),taskName);
    }
}

