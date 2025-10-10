// src/main/java/iscteiul/ista/emails/EmailsView.java
package iscteiul.ista.emails;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("emails")
public class EmailsView extends VerticalLayout {

    private final EmailsService emailsService;

    private final TextField to = new TextField("Para");
    private final TextField subject = new TextField("Assunto");
    private final TextArea body = new TextArea("Mensagem");
    private final Checkbox html = new Checkbox("HTML");

    @Autowired
    public EmailsView(EmailsService emailsService) {
        this.emailsService = emailsService;
        FormLayout form = new FormLayout();
        to.setWidthFull();
        subject.setWidthFull();
        body.setWidthFull();
        body.setHeight("200px");

        Button send = new Button("Enviar", e -> sendEmail());
        form.add(to, subject, body, html, send);
        add(form);
    }

    private void sendEmail() {
        String dest = to.getValue();
        if (dest == null || dest.isBlank()) {
            Notification.show("O campo 'Para' é obrigatório.", 3000, Notification.Position.MIDDLE);
            return;
        }
        EmailDto dto = new EmailDto(dest, subject.getValue(), body.getValue(), html.getValue());
        try {
            emailsService.send(dto);
            Notification.show("Email enviado com sucesso.", 3000, Notification.Position.BOTTOM_START);
            subject.clear();
            body.clear();
            html.setValue(false);
        } catch (Exception ex) {
            Notification.show("Falha ao enviar email: " + ex.getMessage(), 4000, Notification.Position.MIDDLE);
        }
    }
}

