// src/main/java/iscteiul/ista/emails/EmailsService.java
package iscteiul.ista.emails;

import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailsService {
    private static final Logger logger = LoggerFactory.getLogger(EmailsService.class);

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String defaultFrom;

    public EmailsService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSimpleEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(text);
            if (defaultFrom != null && !defaultFrom.isBlank()) {
                msg.setFrom(defaultFrom);
            }
            mailSender.send(msg);
            logger.info("Email simples enviado para {}", to);
        } catch (Exception e) {
            logger.error("Erro ao enviar email simples para {}: {}", to, e.getMessage());
            throw new RuntimeException("Erro ao enviar email", e);
        }
    }

    public void sendHtmlEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            if (defaultFrom != null && !defaultFrom.isBlank()) {
                helper.setFrom(defaultFrom);
            }
            mailSender.send(mimeMessage);
            logger.info("Email HTML enviado para {}", to);
        } catch (Exception e) {
            logger.error("Erro ao enviar email HTML para {}: {}", to, e.getMessage());
            throw new RuntimeException("Erro ao enviar email HTML", e);
        }
    }

    public void send(EmailDto dto) {
        if (dto.isHtml()) {
            sendHtmlEmail(dto.getTo(), dto.getSubject(), dto.getBody());
        } else {
            sendSimpleEmail(dto.getTo(), dto.getSubject(), dto.getBody());
        }
    }
}
