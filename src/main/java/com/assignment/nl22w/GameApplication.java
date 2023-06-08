package com.assignment.nl22w;

import com.assignment.nl22w.game.impl.GameImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@SpringBootApplication
public class GameApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(GameApplication.class, args);
		ApplicationContext applicationContext = SpringApplication.run(GameApplication.class, args);
		GameImpl game = applicationContext.getBean(GameImpl.class);
		int result = game.escapeFromTheWoods(new ClassPathResource("map1.txt"));
	}
}
