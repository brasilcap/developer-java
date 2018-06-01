package br.com.brasilcap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	
	/**
	 * Main Method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		SpringApplication.run(Application.class, args);
		
		System.out.println("Hello World!");
	}

}
