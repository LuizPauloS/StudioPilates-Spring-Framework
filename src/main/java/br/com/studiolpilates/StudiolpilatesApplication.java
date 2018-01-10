package br.com.studiolpilates;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

@SpringBootApplication
public class StudiolpilatesApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudiolpilatesApplication.class, args);
	}
	
	@Bean
	public FixedLocaleResolver localeResolver(){
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
}
