package pl.aga.todolist.todolist.checklist.repository;

import org.junit.jupiter.api.Test;
import pl.aga.todolist.todolist.checklist.service.CheckList;
import pl.aga.todolist.todolist.checklist.service.Task;
import pl.aga.todolist.todolist.checklist.service.TaskService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FileRepositoryTest {

    @Test
    public void shouldCreateNewFile() {
        FileRepository repo = new FileRepository("c:/_CheckList/");
        CheckList checkList = new CheckList("dom", UUID.randomUUID());
        repo.save(checkList);
    }

    @Test
    public void shouldDeleteFile() {
        FileRepository repo = new FileRepository("c:/_CheckList/");
        CheckList checkList = new CheckList("dom", UUID.randomUUID());
        repo.save(checkList);
        Path path = Paths.get("c:/_CheckList/" + checkList.getId() + ".txt");
        assertTrue(Files.exists(path));

        repo.delete(checkList.getId());
        assertTrue(Files.notExists(path));
    }

    @Test
    public void shouldFindCheckList() {
        FileRepository repo = new FileRepository("c:/_CheckList/");
        CheckList checkList = new CheckList("dom", UUID.randomUUID());
        repo.save(checkList);
        CheckList checkList1 = new CheckList("garaz", UUID.randomUUID());
        repo.save(checkList1);

        CheckList foundedList = repo.find(checkList.getId()).orElse(null);

        assertEquals("dom", foundedList.getName());
    }

    @Test
    public void shouldFindTask() {
        FileRepository repo = new FileRepository("c:/_CheckList/");
        TaskService t = new TaskService(repo);
        CheckList checkList = new CheckList("dom", UUID.randomUUID());
        repo.save(checkList);

        t.addTask(checkList.getId(), "ala");
        t.addTask(checkList.getId(), "beta");

        }
}