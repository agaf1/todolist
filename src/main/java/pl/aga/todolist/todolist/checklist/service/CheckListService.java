package pl.aga.todolist.todolist.checklist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CheckListService {

    private CheckListRepository repo ;

    public CheckListService(@Autowired @Qualifier("jdbcRepo") CheckListRepository repo){
        this.repo = repo;
    }

    public void create(String name) {
        CheckList checkList = new CheckList(name, UUID.randomUUID());
        repo.save(checkList,true);
    }
    public void delete(UUID id){
        repo.delete(id);
    }
    public List<CheckList> getAll(){
        return repo.loadCheckList();
    }
}

