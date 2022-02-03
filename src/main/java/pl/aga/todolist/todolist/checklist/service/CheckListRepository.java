package pl.aga.todolist.todolist.checklist.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CheckListRepository {
    void save(CheckList checkList,boolean createChecklist);
    void delete(UUID id) ;
    Optional<CheckList> find(UUID id);
    List<CheckList> loadCheckList();

}
