package pl.aga.todolist.todolist.checklist.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.aga.todolist.todolist.checklist.repository.MemoryRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {
    private MemoryRepository repository;
    private TaskService serviceTask ;
    private CheckListService service;

    @BeforeEach
    public void setup(){
        repository = new MemoryRepository();
        serviceTask  = new TaskService(repository);
        service = new CheckListService(repository);

        service.create("test");

    }


    @Test
    public void shouldAddTask(){

        List<CheckList> cL = repository.getAll();
        CheckList l = cL.get(0);

        serviceTask.addTask(l.getId(),"read book");

        assertEquals(1,l.getAllTasks().size() );
    }
    @Test
    public void shouldDeleteTask(){
        List<CheckList> cL = repository.getAll();
        CheckList l = cL.get(0);

        serviceTask.addTask(l.getId(),"read book");
        List<Task> ta = l.getAllTasks();
        Task t = ta.get(0);
        serviceTask.deleteTask(l.getId(),t.getIdTask());

        assertEquals(0,l.getAllTasks().size());
    }
    @Test
    public void shouldChangeExecuteToTrue(){

        List<CheckList> cL = repository.getAll();
        CheckList checkList = cL.get(0);

        serviceTask.addTask(checkList.getId(),"read book");
        List<Task> tasks = checkList.getAllTasks();
        Task task = tasks.get(0);

        assertEquals(false,task.getExecute());

        serviceTask.changeState(checkList.getId(),task.getIdTask());

        assertEquals(true,task.getExecute());
    }
}