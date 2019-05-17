package com.phincon.dialogflow.apps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.phincon.dialogflow"})
@PropertySources({
	@PropertySource("classpath:application.properties")
})
public class DialogflowProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(DialogflowProjectApplication.class, args);
	}

}
