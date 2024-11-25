package com.synergetics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Lab02AzureopenaiApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Lab02AzureopenaiApplication.class, args);
		for (String beanName : context.getBeanDefinitionNames()) {
			System.out.println(beanName);
		}

		MyClient client = context.getBean(MyClient.class);
		String response = client.call(
				"What are the names of continents. Produce JSON output.");

		System.out.println(response);
	}
}
