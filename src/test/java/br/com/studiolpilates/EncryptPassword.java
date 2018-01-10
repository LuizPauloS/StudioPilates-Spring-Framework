package br.com.studiolpilates;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptPassword {

	public static void main(String[] args) {
		String encryptPassword = new BCryptPasswordEncoder().encode("123456");
		System.out.println("Senha criptografada: " + encryptPassword);

	}

}
