package pl.aga.todolist.todolist.checklist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.aga.todolist.todolist.checklist.service.CheckList;
import pl.aga.todolist.todolist.checklist.service.CheckListService;
import pl.aga.todolist.todolist.checklist.service.TaskService;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@RestController
public class CheckListController {

    @Autowired
    private CheckListService checkListService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private MapperDTO mapperDTO;

    @RequestMapping(
            method = RequestMethod.GET,
            path = "checklist/all",
            produces="application/json"
    )
    @ResponseBody
    public List<CheckListDTO> getAll(){

        List<CheckListDTO> checkListDTOList = checkListService
                .getAll()
                .stream()
                .map(mapperDTO).toList();
        return checkListDTOList;
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
            method = RequestMethod.POST,
            path = "checklist/create",
            produces = "application/json",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Boolean createByPost(@RequestBody Name name) {
        Boolean message = true;
        try {
            checkListService.create(name.getName());
        }
        catch (IllegalArgumentException e)
        {message = false ;}

        return message;
    }

    private static class Name {
        private String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
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

