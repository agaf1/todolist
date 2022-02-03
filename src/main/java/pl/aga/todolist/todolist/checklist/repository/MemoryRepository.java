package pl.aga.todolist.todolist.checklist.repository;

import org.springframework.stereotype.Repository;
import pl.aga.todolist.todolist.checklist.service.CheckList;
import pl.aga.todolist.todolist.checklist.service.CheckListRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("memRepo")
public class MemoryRepository implements CheckListRepository {
    private List<CheckList> checklists = new ArrayList<>();

    @Override
    public void save(CheckList checkList, boolean createChecklist) {
        if(!checklists.contains(checkList)){
            checklists.add(checkList);
        }
    }
    @Override
    public void delete(UUID id){
        find(id).ifPresent(el->checklists.remove(el));
    }
    @Override
    public Optional<CheckList> find(UUID id){
        return checklists.stream()
                .filter(e -> e.getId().equals(id))
                .findAny();
    }

    public int size(){
        return checklists.size();
    }

    @Override
    public List<CheckList> loadCheckList(){
        return checklists;
    }
}
