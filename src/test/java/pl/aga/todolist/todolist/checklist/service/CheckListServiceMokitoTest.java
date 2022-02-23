package pl.aga.todolist.todolist.checklist.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckListServiceMokitoTest {
    @Test
    public void shouldCreate() throws IOException {
        CheckListRepository repo = Mockito.mock(CheckListRepository.class);
        CheckListService service =  new CheckListService(repo);

        service.create("test");

        Mockito.verify(repo).save(Mockito.any(),Mockito.eq(true));

//        Optional<CheckList> checkList = service.getAll().stream().filter(c->"test".equals(c.getName())).findAny();
//        assertTrue(checkList.isPresent());
    }
}
