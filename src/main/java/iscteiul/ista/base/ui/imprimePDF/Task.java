package iscteiul.ista.base.ui.imprimePDF;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;
    private String description;
    private Instant creationDate;
    private LocalDate dueDate;


    public Task() { }
    public Task(String title, String description, Instant creationDate) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
    }
    public String getTitle() {
        return this.title; // 'title' deve ser o nome do atributo correspondente ao título da tarefa
    }
    public String getDescription() {
        return this.description;
    }
}

