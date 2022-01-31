package pl.aga.todolist.todolist.checklist.service;

import java.util.List;
import java.util.UUID;

public class TaskService {

    private CheckListRepository repo ;
    public TaskService(CheckListRepository repo){
        this.repo = repo;
    }

    public void addTask(UUID idCheckList, String nameTask){
        Task task = new Task(nameTask,false,UUID.randomUUID());
        repo.find(idCheckList).ifPresent(checklist ->{
            checklist.addTask(task);
            repo.save(checklist);
        });
    }
    public void deleteTask(UUID idCheckList, UUID idTask){
        CheckList checkList = repo.find(idCheckList).get();
        List<Task> tasks = checkList.getAllTasks();
        for(Task task: tasks){
            if (task.getIdTask().equals(idTask)) {
                tasks.remove(task);
                break;
            }
        }
        repo.save(checkList);
    }
    public void changeState(UUID idCheckList, UUID idTask){
        CheckList checkList = repo.find(idCheckList).get();
        List<Task> tasks = checkList.getAllTasks();
        for(Task task: tasks){
            if (task.getIdTask().equals(idTask)) {
                task.changeState();
            }
        }
        repo.save(checkList);
    }
}
