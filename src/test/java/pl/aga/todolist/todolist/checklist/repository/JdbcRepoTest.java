package pl.aga.todolist.todolist.checklist.repository;

import org.junit.jupiter.api.Test;
import pl.aga.todolist.todolist.checklist.service.CheckList;
import pl.aga.todolist.todolist.checklist.service.Task;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JdbcRepoTest {

    @Test
    public void shouldSaveChecklist() {
        JdbcRepo repo = new JdbcRepo();
        List<Task> tasks = new ArrayList<Task>();
        tasks.add(new Task("cos", false, UUID.randomUUID()));

        CheckList checkList = new CheckList("home", UUID.randomUUID(), tasks);
        repo.save(checkList);
    }

    @Test
    public void shouldDeleteChecklist() {
        JdbcRepo repo = new JdbcRepo();

        List<Task> tasks = new ArrayList<Task>();
        tasks.add(new Task("bleble", false, UUID.randomUUID()));
        CheckList checkList = new CheckList("garden", UUID.randomUUID(), tasks);

        repo.save(checkList);
        repo.delete(checkList.getId());
    }

    @Test
    public void shouldFindChecklist() {
        JdbcRepo repo = new JdbcRepo();
        UUID id = UUID.fromString("2b2cf2e4-d186-485c-9eaa-3516027a12e2");
        Optional<CheckList> optionalCheckList = repo.find(id);
        String name = optionalCheckList.get().getName();

        assertEquals("garden",name);
    }

    @Test
    public void ShouldNotFindChecklist(){
        JdbcRepo repo = new JdbcRepo();
        UUID id = UUID.randomUUID();
        Optional<CheckList> optionalCheckList = repo.find(id);

        assertTrue(optionalCheckList.isEmpty());
    }
}