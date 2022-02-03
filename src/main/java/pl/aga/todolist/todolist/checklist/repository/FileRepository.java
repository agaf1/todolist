package pl.aga.todolist.todolist.checklist.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import pl.aga.todolist.todolist.checklist.service.CheckList;
import pl.aga.todolist.todolist.checklist.service.CheckListRepository;
import pl.aga.todolist.todolist.checklist.service.Task;

import java.io.*;
import java.nio.file.*;
import java.util.*;

@Repository("fileRepo")
public class FileRepository implements CheckListRepository {

    private final String mainDir;

    public FileRepository(@Value("fileRepo.mainDir") String mainDir) {
        this.mainDir = mainDir;
    }


    @Override
    public void save(CheckList checkList, boolean createChecklist) {
        Path file = Paths.get(mainDir + checkList.getId() + ".txt");
        try {
            Files.writeString(file, checkList.toString(),StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void delete(UUID id) {
        String fileName = mainDir + id + ".txt";
        try {
            Files.delete(Paths.get(fileName));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Optional<CheckList> find(UUID checklistId) {
        List<CheckList> checkLists = loadCheckList();
        return checkLists.stream().filter(c -> c.getId().equals(checklistId)).findAny();
    }

    @Override
    public List<CheckList> loadCheckList() {
        List<CheckList> checkLists = new ArrayList<>();
        try {
            List<Path> pathsList = Files.list(Path.of(mainDir)).toList();
            for (Path path : pathsList) {
                List<String> strings = Files.readAllLines(path);

                String[] attributeChecklist = strings.get(0).split(",");

                List<Task> tasks = new ArrayList<>();
                if (strings.size() > 0) {
                    for (int i = 1; i < strings.size(); i++) {

                        String[] attributeTask = strings.get(i).split(",");
                        Task task = new Task(attributeTask[0],
                                Boolean.parseBoolean(attributeTask[1]),
                                UUID.fromString(attributeTask[2]));
                        tasks.add(task);
                    }
                }
                CheckList checkList = new CheckList(attributeChecklist[0],
                        UUID.fromString(attributeChecklist[1]),
                        tasks);
                checkLists.add(checkList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return checkLists;
    }
}
