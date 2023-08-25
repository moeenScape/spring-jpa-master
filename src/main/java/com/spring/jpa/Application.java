package com.spring.jpa;

import com.github.javafaker.Faker;
import com.spring.jpa.entity.Student;
import com.spring.jpa.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sound.midi.Soundbank;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Faker faker = new Faker();

            for (int i = 0; i < 20; i++) {
                String firstName = faker.name().firstName();
                String lastName = faker.name().lastName();
                String email = String.format("%s.%s@gmail.com", firstName, lastName);

                Student student = new Student(
                        firstName,
                        lastName,
                        email,
                        faker.number().numberBetween(17, 25));
                studentRepository.save(student);
            }
            System.out.println("***********************************************************");
            System.out.println("Student List : ");
            studentRepository.findAll()
                    .forEach(System.out::println);

            System.out.println("***********************************************************");
            System.out.println("finding Student by email");
            studentRepository.findStudentByEmail("moeen@gmail.com")
                    .ifPresentOrElse(System.out::println, () ->
                            System.out.println("Student not fount with given email"));

            System.out.println("***********************************************************");
            System.out.println("Use distinct Keyword ");
            studentRepository.findDistinctByLastNameAndFirstName("Moeen", "Ahmmed")
                    .forEach(System.out::println);

            System.out.println("***********************************************************");
            System.out.println("User AND keyword");
            studentRepository.findStudentByFirstNameAndLastName("Moeen", "Ahmmed")
                    .forEach(System.out::println);

            System.out.println("***********************************************************");
            System.out.println("User OR keyword");
            studentRepository.findStudentByFirstNameOrLastName("Moeen", "Ahmmed")
                    .forEach(System.out::println);

            System.out.println("***********************************************************");
            System.out.println("Between Keyword");
            studentRepository.findStudentByAgeBetween(17, 30)
                    .forEach(System.out::println);

            System.out.println("***********************************************************");
            System.out.println("Delete by Id ");
            studentRepository.deleteStudentById(1L);

            System.out.println("***********************************************************");
            System.out.println("Check Student if delete work or not");
            studentRepository.findById(1L).ifPresentOrElse(System.out::println,()->{
                System.out.println("Student was deleted");
            });

        };
    }
}
