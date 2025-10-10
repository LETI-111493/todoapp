package iscteiul.ista.examplefeature;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@Route("tasks")
@PageTitle("Task List")
public class TaskListView extends VerticalLayout {

    private final TaskService taskService;
    private final Grid<Task> grid = new Grid<>(Task.class);
    private final TextField descriptionField = new TextField("Descrição");
    private final TextField dueDateField = new TextField("Due Date (YYYY-MM-DD)");

    @Autowired
    public TaskListView(TaskService taskService) {
        this.taskService = taskService;

        configureGrid();
        configureForm();

        add(descriptionField, dueDateField, new HorizontalLayout(createAddButton()), grid);

        updateGrid();
    }

    private void configureGrid() {
        grid.setColumns("description", "dueDate", "creationDate");
        grid.getColumnByKey("description").setHeader("Descrição");
        grid.getColumnByKey("dueDate").setHeader("Data Limite");
        grid.getColumnByKey("creationDate").setHeader("Criado Em");
        grid.setSizeFull();
    }

    private void configureForm() {
        descriptionField.setWidth("400px");
        dueDateField.setPlaceholder("YYYY-MM-DD");
        dueDateField.setWidth("150px");
    }

    private Button createAddButton() {
        Button addButton = new Button("Adicionar Task");
        addButton.addClickListener(event -> {
            String description = descriptionField.getValue();
            String dueDateStr = dueDateField.getValue();

            if (description == null || description.isBlank()) {
                Notification.show("Descrição é obrigatória.");
                return;
            }

            LocalDate dueDate = null;
            if (dueDateStr != null && !dueDateStr.isBlank()) {
                try {
                    dueDate = LocalDate.parse(dueDateStr);
                } catch (Exception e) {
                    Notification.show("Data inválida. Use formato YYYY-MM-DD.");
                    return;
                }
            }

            try {
                taskService.createTask(description, dueDate);
                Notification.show("Task adicionada com sucesso.");
                descriptionField.clear();
                dueDateField.clear();
                updateGrid();
            } catch (Exception e) {
                Notification.show("Erro ao adicionar task: " + e.getMessage());
            }
        });
        return addButton;
    }

    private void updateGrid() {
        grid.setItems(taskService.list(null)); // Pode ser melhorado para paginação
    }
}
