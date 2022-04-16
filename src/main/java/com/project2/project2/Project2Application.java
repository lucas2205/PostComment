package com.project2.project2;

import org.modelmapper.ModelMapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Project2Application {

    
        @Bean
        public ModelMapper modelMapper(){
            return new ModelMapper();
        }
	public static void main(String[] args) {
		SpringApplication.run(Project2Application.class, args);
	}

}
