package pl.aga.todolist.todolist.checklist.service;

import org.junit.jupiter.api.Test;
import pl.aga.todolist.todolist.checklist.repository.MemoryRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CheckListServiceTest {

    private MemoryRepository repository = new MemoryRepository();
    private  CheckListService service  = new CheckListService(repository);

    @Test
    public void shouldCreate(){

        service.create("test");
        assertEquals(1,repository.size());
    }

    @Test
    public void shouldDelete(){
        service.create("test");
        service.create("test2");

        List<CheckList> cL = repository.getAll();
        UUID id = cL.get(0).getId();
        service.delete(id);
        assertEquals(1,repository.size());
    }
}