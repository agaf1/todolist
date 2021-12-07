package pl.aga.todolist.todolist.checklist.service;

import java.util.UUID;

public class TaskService {

    private CheckListRepository repo ;
    public TaskService(CheckListRepository repo){
        this.repo = repo;
    }

    public void addTask(UUID idCheckList, String nameTask){
        Task task = new Task(nameTask,false,UUID.randomUUID());
        repo.find(idCheckList).ifPresent(l -> l.addTask(task));
    }
    public void deleteTask(UUID idCheckList, UUID idTask){
        Task task = repo.findTask(repo.find(idCheckList).orElseThrow(), idTask).get();
        repo.find(idCheckList).ifPresent(l->l.deleteTask(task));
    }
    public void changeState(UUID idCheckList, UUID idTask){
        Task task = repo.findTask(repo.find(idCheckList).orElseThrow(),idTask).get();
        task.changeState();
    }
}
