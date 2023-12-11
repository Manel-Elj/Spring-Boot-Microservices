package com.example.demo;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner start(CustomerRepository customerRepository) {
		return args -> {
			customerRepository.save(new Customer(null, "eni", "contact@eni.tn"));
			customerRepository.save(new Customer(null, "fst", "contact@fst.tn"));
			customerRepository.save(new Customer(null, "ensi", "contact@ensi.tn"));

			customerRepository.findAll().forEach(System.out::println);
		};
	}

}
