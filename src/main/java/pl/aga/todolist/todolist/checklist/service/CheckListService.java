package pl.aga.todolist.todolist.checklist.service;

import java.util.List;
import java.util.UUID;

public class CheckListService {

    private CheckListRepository repo ;

    public CheckListService(CheckListRepository repo){
        this.repo = repo;
    }

    public void create(String name){
        CheckList checkList = new CheckList(name, UUID.randomUUID());
        repo.save(checkList);
    }
    public void delete(UUID id){
        repo.delete(id);
    }
}

