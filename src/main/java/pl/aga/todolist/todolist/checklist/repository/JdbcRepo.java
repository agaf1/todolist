package pl.aga.todolist.todolist.checklist.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.aga.todolist.todolist.checklist.service.CheckList;
import pl.aga.todolist.todolist.checklist.service.CheckListRepository;
import pl.aga.todolist.todolist.checklist.service.Task;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("jdbcRepo")
public class JdbcRepo implements CheckListRepository {
    private DataSource dataSource;
    private Connection con ;

    public JdbcRepo(@Autowired  DataSource dataSource) {
        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/todolist", "root", "agnieszka");
            con = dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void save(CheckList checkList) {
        try {
            String command1 = "INSERT INTO checklists (id_checklist,name_checklist) values (?,?)";
            PreparedStatement stat = con.prepareStatement(command1);
            stat.setString(1, checkList.getId().toString());
            stat.setString(2, checkList.getName());
            stat.executeUpdate();

            List<Task> tasks = checkList.getAllTasks();
            PreparedStatement taskStat = con.prepareStatement("INSERT INTO tasks (id_task,execute,name_task,id_checklist) " +
                    "Values (?,?,?,?)");

            for (Task task : tasks) {
                taskStat.setString(1, task.getIdTask().toString());
                taskStat.setBoolean(2, task.getExecute());
                taskStat.setString(3, task.getName());
                taskStat.setString(4, checkList.getId().toString());
                taskStat.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void delete(UUID id) {
        String command = "DELETE FROM checklists WHERE id_checklist LIKE ?";
        try (PreparedStatement stat = con.prepareStatement(command)) {
            stat.setString(1, id.toString());
            stat.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Optional<CheckList> find(UUID id) {
        CheckList checkList = findChecklistById(id);
        if(checkList !=null){
            List<Task> tasks = loadTasks(checkList.getId());
            checkList = new CheckList(checkList.getName(),checkList.getId(),tasks);
        }

        return Optional.ofNullable(checkList);
    }

    private CheckList findChecklistById(UUID idChecklist) {
        CheckList checkList = null;

        String command1 = "SELECT id_checklist, name_checklist FROM checklists WHERE id_checklist = ? ";
        ResultSet result = null;

        try (PreparedStatement stat = con.prepareStatement(command1)) {

            stat.setString(1, idChecklist.toString());
            result = stat.executeQuery();
            if (result.getFetchSize() > 0) {
                result.next();
                String nameChecklist = result.getString("name_checklist");
                checkList = new CheckList(nameChecklist, idChecklist);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return checkList;
    }

    private List<Task> loadTasks(UUID idChecklist) {

        String command2 = "SELECT id_task, execute, name_task, id_checklist " +
                "FROM tasks WHERE id_checklist = ? ";
        ResultSet resultTask = null;
        List<Task> tasks = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement(command2)) {

            statement.setString(1, idChecklist.toString());
            resultTask = statement.executeQuery();

            while (resultTask.next()) {
                UUID idTask = UUID.fromString(resultTask.getString("id_task"));
                boolean execute = resultTask.getBoolean("execute");
                String name = resultTask.getString("name_task");

                Task task = new Task(name, execute, idTask);
                tasks.add(task);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return tasks;
    }

    @Override
    public List<CheckList> loadCheckList() {
        List<CheckList> checklists = new ArrayList<>();

        String command1 = "SELECT id_checklist, name_checklist FROM checklists";
        ResultSet result = null;
        try {
            Connection con = dataSource.getConnection();
            result = con.createStatement().executeQuery(command1);
            while (result.next()) {
                UUID idChecklist = UUID.fromString(result.getString(1));
                String nameChecklist = result.getString(2);

                List<Task> tasks = loadTasks(idChecklist);

                CheckList checkList = new CheckList(nameChecklist, idChecklist, tasks);
                checklists.add(checkList);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return checklists;
    }
}
