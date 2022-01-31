package pl.aga.todolist.todolist.checklist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.aga.todolist.todolist.checklist.service.CheckList;
import pl.aga.todolist.todolist.checklist.service.CheckListService;

import java.util.List;

@RestController
public class CheckListController {

    @Autowired
    private CheckListService checkListService;


    @RequestMapping(
            method = RequestMethod.GET,
            path = "checklist/all",
            produces="application/json"
    )

    @ResponseBody
    public List<CheckList> getAll(){
        return checkListService.getAll();

    }
}

