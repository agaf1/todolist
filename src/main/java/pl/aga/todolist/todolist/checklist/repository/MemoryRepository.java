package pl.aga.todolist.todolist.checklist.repository;

import pl.aga.todolist.todolist.checklist.service.CheckList;
import pl.aga.todolist.todolist.checklist.service.CheckListRepository;
import pl.aga.todolist.todolist.checklist.service.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MemoryRepository implements CheckListRepository {
    private List<CheckList> checklists = new ArrayList<>();

    @Override
    public void save(CheckList checkList) {
        checklists.add(checkList);
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
    @Override
    public Optional<Task> findTask(CheckList checkList, UUID idTask){
        return checkList.getAllTasks().stream().filter(t->t.getIdTask().equals(idTask))
                .findAny();
    }

    public int size(){
        return checklists.size();
    }

    public List<CheckList> getAll(){
        return checklists;
    }
}
