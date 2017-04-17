package br.com.sol7.orcamento.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailUtil {

    private JavaMailSender mailSender;

    @Autowired
    public EmailUtil(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String email, String subject, String texto){
        try {
            MimeMessage mime = this.mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mime, true);
            helper.setFrom("noreply@bimachine.com.br");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(texto,true);
            this.mailSender.send(mime);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public void sendEmailWithLayout(String email, String subject, String texto){
        sendEmailWithLayout(email, subject, texto, null);
    }

    public void sendEmailWithLayout(String email, String subject, String texto, String link){
        String msg;
        if(!ObjectUtil.nullOrEmpty(link)){
            msg = new String("<table width=\"500\" border=\"1\" cellpadding=\"1\" cellspacing=\"1\" align=\"center\" style=\"background-color: rgb(255, 255, 255); border-collapse: collapse; border-color: #000000\">" +
                    "<tbody><tr style=\"background-color: #F36F35; font-weight: bold;\"><td><p style=\"text-align: center; margin: 0;\">Atenção!<span style=\"font-size: larger;\">" +
                    "</span></p></td></tr><tr><td>" +
                    "<p style=\"margin: 5px; font-weight: bold;\">"+texto+"</p>" +
                    "<a style=\"margin: 5px;\" href=\"http://"+link+"\">Clique aqui para acessar o Sistema.</a>" +
                    "</td>"+
                    "</tr></tbody></table>\n");
        }else{
            msg = new String("<table width=\"500\" border=\"1\" cellpadding=\"1\" cellspacing=\"1\" align=\"center\" style=\"background-color: rgb(255, 255, 255); border-collapse: collapse; border-color: #000000\">" +
                    "<tbody><tr style=\"background-color: #F36F35; font-weight: bold;\"><td><p style=\"text-align: center; margin: 0;\">Atenção!<span style=\"font-size: larger;\">" +
                    "</span></p></td></tr><tr><td>" +
                    "<p style=\"margin: 5px; font-weight: bold;\">"+texto+"</p></td></tr></tbody></table>\n");
        }
        sendEmail(email,subject, msg);

    }
}
