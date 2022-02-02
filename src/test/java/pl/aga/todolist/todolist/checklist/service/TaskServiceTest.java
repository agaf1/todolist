package pl.aga.todolist.todolist.checklist.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.aga.todolist.todolist.checklist.repository.FileRepository;
import pl.aga.todolist.todolist.checklist.repository.JdbcRepo;
import pl.aga.todolist.todolist.checklist.repository.MemoryRepository;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServiceTest {

    @Autowired
    DataSource dataSource;

    private TaskService taskService;
    private CheckListService checkListService;

    @BeforeEach
    public void setup() {
              JdbcRepo repo = new JdbcRepo(dataSource);
        //        FileRepository repo = new FileRepository("c:/_CheckList/");
        //      qMemoryRepository repo = new MemoryRepository();

        taskService = new TaskService(repo);
        checkListService = new CheckListService(repo);
        checkListService.create("test");
    }

    @Test
    public void shouldAddTask() {
        List<CheckList> cL = checkListService.getAll();
        CheckList list = cL.get(0);

        taskService.addTask(list.getId(), "read book");

        Optional<CheckList> resultList = checkListService.getAll().
                stream().
                filter(c -> c.getId().equals(list.getId())).
                findAny();

        Optional<Task> resultTask = resultList.map(c -> c.getAllTasks()).orElseGet(Collections::emptyList).
                stream().filter(t -> t.getName().equals("read book")).findAny();

        assertTrue(resultList.isPresent());
    }

    @Test
    public void shouldDeleteTask() {

        List<CheckList> checkLists = checkListService.getAll();
        UUID idCheckList = checkLists.get(0).getId();

        taskService.addTask(idCheckList, "read book");

        Optional<Task> newTask = findTask(idCheckList,(task)->"read book".equals(task.getName()));

        UUID idNewTask = newTask.get().getIdTask();

        taskService.deleteTask(idCheckList, idNewTask);

        Optional<Task> resultTask = findTask(idCheckList,(task)->idNewTask.equals(task.getIdTask()));

        assertTrue(resultTask.isEmpty());
    }

    private Optional<Task> findTask(UUID idCheckList, Predicate<Task> findTaskPredicate){
        Optional<CheckList> checkList = checkListService.getAll()
                .stream()
                .filter(c -> idCheckList.equals(c.getId()))
                .findAny();

        Optional<Task> task = checkList.map(c -> c.getAllTasks()).orElseGet(Collections::emptyList)
                .stream()
                .filter(findTaskPredicate)
                .findAny();
        return task;
    }
//
//
//
//    @Test
//    public void shouldChangeExecuteToTrue(){
//
//        List<CheckList> cL = repository.loadCheckList();
//        CheckList checkList = cL.get(0);
//
//        serviceTask.addTask(checkList.getId(),"read book");
//        List<Task> tasks = checkList.getAllTasks();
//        Task task = tasks.get(0);
//
//        assertEquals(false,task.getExecute());
//
//        serviceTask.changeState(checkList.getId(),task.getIdTask());
//
//        assertEquals(true,task.getExecute());
//    }
}