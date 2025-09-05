package com.lisaanna.ws_todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class
})
public class WsTodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsTodoApplication.class, args);
	}

}
