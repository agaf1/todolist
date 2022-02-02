package pl.aga.todolist.todolist.checklist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private CheckListRepository repo ;
    public TaskService(@Autowired @Qualifier("jdbcRepo") CheckListRepository repo){
        this.repo = repo;
    }

    public void addTask(UUID idCheckList, String nameTask){
        Task task = new Task(nameTask,false,UUID.randomUUID());
        repo.find(idCheckList).ifPresent(checklist ->{
            checklist.addTask(task);
            repo.save(checklist,false);
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
        repo.save(checkList,false);
    }
    public void changeState(UUID idCheckList, UUID idTask){
        CheckList checkList = repo.find(idCheckList).get();
        List<Task> tasks = checkList.getAllTasks();
        for(Task task: tasks){
            if (task.getIdTask().equals(idTask)) {
                task.changeState();
            }
        }
        repo.save(checkList, false);
    }
}
