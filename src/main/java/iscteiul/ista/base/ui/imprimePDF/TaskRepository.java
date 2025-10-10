package iscteiul.ista.base.ui.imprimePDF;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Métodos extra podem ser definidos aqui se necessário
}