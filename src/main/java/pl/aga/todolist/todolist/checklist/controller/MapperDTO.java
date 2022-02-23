package pl.aga.todolist.todolist.checklist.controller;

import org.springframework.stereotype.Component;
import pl.aga.todolist.todolist.checklist.service.CheckList;
import pl.aga.todolist.todolist.checklist.service.Task;

import java.util.List;
import java.util.function.Function;

@Component
class MapperDTO implements Function<CheckList,CheckListDTO> {

    @Override
    public CheckListDTO apply (CheckList checkList) {

        List<TaskDTO> taskDTOList = checkList
                .getAllTasks()
                .stream()
                .map(this::applyTask).toList();

        CheckListDTO checkListDTO =
                new CheckListDTO(checkList.getName(),checkList.getId(),taskDTOList);
        return checkListDTO;
    }

    private TaskDTO applyTask (Task task){
        TaskDTO taskDTO = new TaskDTO(task.getName(),task.getExecute(),task.getIdTask());
        return taskDTO;
    }

}
