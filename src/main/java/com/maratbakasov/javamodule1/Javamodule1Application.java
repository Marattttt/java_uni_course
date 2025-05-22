package com.maratbakasov.javamodule1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Javamodule1Application {

	public static void main(String[] args) {
		SpringApplication.run(Javamodule1Application.class, args);
	}

}
