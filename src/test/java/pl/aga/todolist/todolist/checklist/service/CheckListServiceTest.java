package pl.aga.todolist.todolist.checklist.service;

import org.junit.jupiter.api.Test;
import pl.aga.todolist.todolist.checklist.repository.FileRepository;
import pl.aga.todolist.todolist.checklist.repository.JdbcRepo;
import pl.aga.todolist.todolist.checklist.repository.MemoryRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CheckListServiceTest {

    private MemoryRepository repository = new MemoryRepository();
    private FileRepository fileRepository = new FileRepository("c:/_CheckList/");
    private JdbcRepo jdbcRepo = new JdbcRepo();
    private CheckListService service = new CheckListService(repository);
    private CheckListService serviceFile = new CheckListService(fileRepository);
    private CheckListService serviseJdbc = new CheckListService(jdbcRepo);

    @Test
    public void shouldCreate() throws IOException {
        service.create("test");
        assertEquals(1, repository.size());
    }

    @Test
    public void shouldCreateInFile() throws IOException {
        serviceFile.create("lala");
        List<Path> pathsList = Files.list(Path.of("c:/_CheckList/")).toList();
        assertEquals(1, pathsList.size());
    }

    @Test
    public void shouldCreateInJdbc() throws IOException{
        serviseJdbc.create("garage");
        boolean result = jdbcRepo.loadCheckList().stream().
                filter(e -> e.getName().equals("garage")).
                findAny().isPresent();

        assertEquals(true,result);
    }

    @Test
    public void shouldDelete() throws IOException {
        service.create("test");
        service.create("test2");

        List<CheckList> cL = repository.loadCheckList();
        UUID id = cL.get(0).getId();
        service.delete(id);
        assertEquals(1, repository.size());
    }

    @Test
    public void shouldDeleteFromFile() throws IOException {
        serviceFile.create("ala");
        serviceFile.create("beta");

        List<Path> pathsList = Files.list(Path.of("c:/_CheckList/")).toList();
        List<String> strings = Files.readAllLines(pathsList.get(0));
        String[] attributeChecklist = strings.get(0).split(",");
        String string = attributeChecklist[1];
        UUID id = UUID.fromString(string);
        serviceFile.delete(id);
        List<Path> pathsList1 = Files.list(Path.of("c:/_CheckList/")).toList();
        assertEquals(2,pathsList.size());
        assertEquals(1,pathsList1.size());
    }

    @Test
    public void shouldDeleteFromJdbc() throws IOException {
        UUID id = UUID.fromString("c24a9c47-b94d-4c6f-bb35-b2c850f9f26b");
        serviseJdbc.delete(id);

        boolean result = jdbcRepo.loadCheckList().stream().
                filter(e -> e.getId().equals(id)).
                findAny().isEmpty();

        assertEquals(true,result);
    }
}