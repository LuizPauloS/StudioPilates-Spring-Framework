package br.com.studiolpilates.mail;

import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import br.com.studiolpilates.model.Paciente;

public class EnviarEmail implements EmailManager{
	
	private static final String EMAIL_SISTEMA = "sistemafinanceirosteste@gmail.com";
	private static final String SENHA_SISTEMA = "sistemateste";
	//private static final SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public void enviarEmail(Paciente paciente) throws MessagingException {
		try {
			//exemplo retirado da documentacao spring https://docs.spring.io/spring/docs/current/spring-framework-reference/html/mail.html
			JavaMailSenderImpl sender = new JavaMailSenderImpl();
			sender.setHost("smtp.gmail.com");
			sender.setPort(587);
			sender.setProtocol("smtp");
			sender.setUsername(EMAIL_SISTEMA);
			sender.setPassword(SENHA_SISTEMA);
			sender.setDefaultEncoding("utf-8");

			Properties properties = new Properties();
			properties.setProperty("usarname", EMAIL_SISTEMA);
			properties.setProperty("password", SENHA_SISTEMA);
			properties.setProperty("mail.smtp.auth", "true");
			properties.setProperty("mail.smtp.starttls.enable", "true");
			properties.setProperty("mail.transport.protocol", "smtp");
			properties.setProperty("mail.smtp.port", "587");
			properties.setProperty("mail.smtp.host", "smtp.gmail.com");
			sender.setJavaMailProperties(properties);

			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setFrom(EMAIL_SISTEMA);
			helper.setSubject("Cadastro realizado no sistema Studio Le Pilates");
			helper.setTo(paciente.getEmail());
			helper.setText("Seu cadastro foi efetuado com sucesso");
			helper.addCc("luiz.paulo.09@hotmail.com");
			helper.addCc(EMAIL_SISTEMA);
			sender.send(message);
		} catch (MailException ex) {
			// simply log it and go on...
			ex.printStackTrace();
			System.err.println(ex.getMessage());
		}
	}
}
