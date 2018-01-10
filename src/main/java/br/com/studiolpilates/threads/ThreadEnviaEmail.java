package br.com.studiolpilates.threads;

import javax.mail.MessagingException;
import br.com.studiolpilates.mail.EnviarEmail;
import br.com.studiolpilates.model.Paciente;

public class ThreadEnviaEmail implements Runnable {

	private Paciente paciente;
	private EnviarEmail envio;

	public ThreadEnviaEmail(Paciente paciente) {
		this.paciente = paciente;
	}

	@Override
	public void run() {
		try {
			envio = new EnviarEmail();
			System.out.println("Enviando email de confirmação de cadastro para o cliente " + this.paciente.toString());
			this.envio.enviarEmail(this.paciente);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
