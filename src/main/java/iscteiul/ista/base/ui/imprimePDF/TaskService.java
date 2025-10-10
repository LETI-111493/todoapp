package iscteiul.ista.base.ui.imprimePDF;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository repo;

    public List<Task> findAll() {
        return repo.findAll();
    }

    public Task save(Task task) {
        return repo.save(task);
    }
    // Outros métodos de negócio
}