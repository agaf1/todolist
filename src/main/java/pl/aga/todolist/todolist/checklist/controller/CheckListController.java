package pl.aga.todolist.todolist.checklist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.aga.todolist.todolist.checklist.service.CheckList;
import pl.aga.todolist.todolist.checklist.service.CheckListService;
import pl.aga.todolist.todolist.checklist.service.TaskService;

import javax.validation.constraints.NotNull;
import java.io.IOException;
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
            path = "checklist/delete/{idChecklist}",
            produces="application/json"
    )
    public void delete(@PathVariable String idChecklist) throws IOException {
        checkListService.delete(UUID.fromString(idChecklist));
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "checklist/tasks/add/{idCheckList}/{taskName}",
            produces="application/json"
    )
    public void addTask (@PathVariable String idCheckList, @PathVariable String taskName){
        taskService.addTask(UUID.fromString(idCheckList),taskName);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "checklist/tasks/delete/{idCheckList}/{idTask}",
            produces="application/json"
    )
    public void deleteTask (@PathVariable String idCheckList, @PathVariable String idTask){
        taskService.deleteTask(UUID.fromString(idCheckList),UUID.fromString(idTask));
    }
}

