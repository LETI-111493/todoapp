package iscteiul.ista.base.ui.imprimePDF;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import iscteiul.ista.base.ui.imprimePDF.Task;
import iscteiul.ista.base.ui.imprimePDF.ImprimirPDF;
import java.util.Arrays;
import java.time.Instant;



@SpringBootApplication
public class Application {
    public static void main(String[] args) throws Exception {
        //SpringApplication.run(Application.class, args);
        List<Task> tasks = Arrays.asList(
                new Task("Tarefa 1", "Descrição 1", Instant.now()),
                new Task("Tarefa 2", "Descrição 2", Instant.now())
        );
        ImprimirPDF pdf = new ImprimirPDF();
        pdf.exportTasksToPDF(tasks, "tarefas.pdf");
    }

    }
