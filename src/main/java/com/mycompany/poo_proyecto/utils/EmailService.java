package com.mycompany.poo_proyecto.utils;

import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {

    private final String remitente = "cafeteriautp2025@gmail.com";
    private final String password = "yusv mydj xtlk bmly";

    public boolean enviarCodigoVerificacion(String destinatario, String codigo) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("♨️ Código de Verificación - Cafetería UTP");

            String htmlContent = "<h1>¡Bienvenido a la Cafetería UTP (BETA)!</h1>"
                    + "<p>Para completar tu registro, usa el siguiente código:</p>"
                    + "<h2 style='color: #d32f2f; font-size: 24px;'>" + codigo + "</h2>"
                    + "<p>Si no solicitaste este código, ignora este mensaje y disculpe las molestias.</p>";

            message.setContent(htmlContent, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("✅ Correo enviado a: " + destinatario);
            return true;

        } catch (MessagingException e) {
            System.err.println("❌ Error enviando correo: " + e.getMessage());
            return false;
        }
    }

    public String generarCodigo() {
        return String.valueOf(new Random().nextInt(90000) + 10000);
    }
}
