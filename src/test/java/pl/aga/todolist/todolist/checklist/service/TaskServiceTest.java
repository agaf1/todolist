package pl.aga.todolist.todolist.checklist.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.aga.todolist.todolist.checklist.repository.FileRepository;
import pl.aga.todolist.todolist.checklist.repository.MemoryRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {
    private MemoryRepository repository;
    private FileRepository fileRepository;
    private TaskService serviceTask ;
    private TaskService fileServiceTask;
    private CheckListService service;
    private CheckListService fileService;

    @BeforeEach
    public void setup() throws IOException {
        repository = new MemoryRepository();
        fileRepository = new FileRepository("c:/_CheckList/");
        serviceTask  = new TaskService(repository);
        fileServiceTask = new TaskService(fileRepository);
        service = new CheckListService(repository);
        fileService = new CheckListService(fileRepository);

        service.create("test");
        fileService.create("test");
    }

    @Test
    public void shouldAddTask(){
        List<CheckList> cL = repository.loadCheckList();
        CheckList l = cL.get(0);

        serviceTask.addTask(l.getId(),"read book");

        assertEquals(1,l.getAllTasks().size() );
    }

    @Test
    public void shouldAddTaskToFile() throws IOException {
        List<Path> paths = Files.list(Path.of("c:/_CheckList/")).toList();
        List<String> strings = Files.readAllLines(paths.get(0));
        String[] attributeChecklist = strings.get(0).split(",");
        String string = attributeChecklist[1];
        UUID id = UUID.fromString(string);
        fileServiceTask.addTask( id, "book");

        List<Path> paths1 = Files.list(Path.of("c:/_CheckList/")).toList();
        List<String> strings1 = Files.readAllLines(paths1.get(0));

        assertEquals(2,strings1.size());
    }

    @Test
    public void shouldDeleteTask(){
        List<CheckList> cL = repository.loadCheckList();
        CheckList l = cL.get(0);

        serviceTask.addTask(l.getId(),"read book");
        List<Task> ta = l.getAllTasks();
        Task t = ta.get(0);
        serviceTask.deleteTask(l.getId(),t.getIdTask());

        assertEquals(0,l.getAllTasks().size());
    }

    @Test
    public void shouldDeleteTaskFromFile() throws IOException {
        List<Path> paths = Files.list(Path.of("c:/_CheckList/")).toList();
        List<String> strings = Files.readAllLines(paths.get(0));
        String[] attributeChecklist = strings.get(0).split(",");
        String string = attributeChecklist[1];
        UUID id = UUID.fromString(string);
        fileServiceTask.addTask( id, "book");

        List<String> strings1 = Files.readAllLines(paths.get(0));
        String[] attributeTask = strings1.get(1).split(",");
        String string1 = attributeTask[2];
        UUID id1 = UUID.fromString(string1);

        assertEquals(2,strings1.size());

        fileServiceTask.deleteTask(id,id1);

        List<Path> paths1 = Files.list(Path.of("c:/_CheckList/")).toList();
        List<String> strings2 = Files.readAllLines(paths1.get(0));


        assertEquals(1,strings2.size());

    }

    @Test
    public void shouldChangeExecuteToTrue(){

        List<CheckList> cL = repository.loadCheckList();
        CheckList checkList = cL.get(0);

        serviceTask.addTask(checkList.getId(),"read book");
        List<Task> tasks = checkList.getAllTasks();
        Task task = tasks.get(0);

        assertEquals(false,task.getExecute());

        serviceTask.changeState(checkList.getId(),task.getIdTask());

        assertEquals(true,task.getExecute());
    }
}