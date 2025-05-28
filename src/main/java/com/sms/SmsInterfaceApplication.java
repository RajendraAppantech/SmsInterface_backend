package com.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(
   considerNestedRepositories = true
)
@EnableJpaAuditing
public class SmsInterfaceApplication {
   public static void main(String[] args) {
      SpringApplication.run(SmsInterfaceApplication.class, args);
      System.out.println("Server Start V1...");
   }
}
