package pl.aga.todolist.todolist.checklist.service;

import java.util.Optional;
import java.util.UUID;

public interface CheckListRepository {
    void save(CheckList checkList);
    void delete(UUID id);
    Optional<CheckList> find(UUID id);
    Optional<Task> findTask(CheckList checkList, UUID idTask);
}
