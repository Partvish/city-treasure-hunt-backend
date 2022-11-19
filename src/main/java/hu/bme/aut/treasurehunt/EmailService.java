package hu.bme.aut.treasurehunt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public void sendMessage(String to, String subject, String text){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("register.service@aut.bme.hu");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(message);
            logger.debug("MAIL SENT: TO(" +to +") Subject("+ subject+ ")");
        }
        catch (Exception e){
            logger.error("MAIL SENDING ERROR: Error whiile sending this message: To("+ to+") Subject("+subject+")" );
        }
    }
}
