package br.com.studiolpilates.mail;

import javax.mail.MessagingException;
import br.com.studiolpilates.model.Paciente;

public interface EmailManager {

	public void enviarEmail(Paciente p) throws MessagingException;
}
