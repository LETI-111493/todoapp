// src/main/java/iscteiul/ista/emails/MailConfig.java
package iscteiul.ista.emails;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Value("${spring.mail.host:}")
    private String host;

    @Value("${spring.mail.port:25}")
    private int port;

    @Value("${spring.mail.username:}")
    private String username;

    @Value("${spring.mail.password:}")
    private String password;

    @Value("${spring.mail.properties.mail.smtp.auth:true}")
    private boolean smtpAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable:true}")
    private boolean starttls;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        if (host != null && !host.isBlank()) {
            sender.setHost(host);
        }
        sender.setPort(port);
        if (username != null && !username.isBlank()) {
            sender.setUsername(username);
            sender.setPassword(password);
        }
        Properties props = sender.getJavaMailProperties();
        props.put("mail.smtp.auth", String.valueOf(smtpAuth));
        props.put("mail.smtp.starttls.enable", String.valueOf(starttls));
        // timeout defaults (opcionais)
        props.put("mail.smtp.connectiontimeout", "5000");
        props.put("mail.smtp.timeout", "5000");
        return sender;
    }
}
