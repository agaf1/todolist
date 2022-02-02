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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CheckListServiceTest {

    @Autowired
    DataSource dataSource;

    private  CheckListService service;

    @BeforeEach
    public void setup(){
 //       JdbcRepo repo = new JdbcRepo(dataSource);
         FileRepository repo = new FileRepository("c:/_CheckList/");
//         MemoryRepository repo = new MemoryRepository();

        service =  new CheckListService(repo);
    }
    @Test
    public void shouldCreate() throws IOException {
        service.create("test");
        Optional<CheckList> checkList = service.getAll().stream().filter(c->"test".equals(c.getName())).findAny();
        assertTrue(checkList.isPresent());
    }

    @Test
    public void shouldDelete() throws IOException {
        service.create("test");
        service.create("test2");

        List<CheckList> cL = service.getAll();

        UUID id = cL.get(0).getId();
        service.delete(id);

        List<CheckList> result = service.getAll();
        Optional<CheckList> resultList = result.stream().filter(c->c.getId().equals(id)).findAny();
        assertTrue(resultList.isEmpty());
    }
}