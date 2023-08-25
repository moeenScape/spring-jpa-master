package com.spring.jpa;

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
            Student moeen = new Student(
                    "Moeen",
                    "Ahmmed",
                    "moeen@gmail.com",
                    25);

            Student maria = new Student(
                    "Maria",
                    "Ahmmed",
                    "maria@gmail.com",
                    25);
            Student newMaria = new Student(
                    "Maria",
                    "Ahmmed",
                    "marianew@gmail.com",
                    25);
            System.out.println("Saving Student list");
            studentRepository.saveAll(List.of(moeen, maria, newMaria));

            System.out.println("Counting Number of Student in database");
            System.out.println(studentRepository.count());

            studentRepository
                    .findById(2L)
                    .ifPresentOrElse(System.out::println,
                            () -> System.out.println("Student is not present with ID 2"));

            studentRepository
                    .findById(3L)
                    .ifPresentOrElse(System.out::println,
                            () -> System.out.println("Student is not present with ID 3"));

            List<Student> studentList = studentRepository.findAll();
            studentList.forEach(System.out::println);

            //studentRepository.deleteById(1L);

            System.out.println(studentRepository.count());

            studentRepository.findStudentByEmail("moeen@gmail.com").ifPresentOrElse(System.out::println,
                    () -> System.out.println("Student is not present with ID 3"));

            System.out.println("FilterStudentList");
            studentRepository.findStudentsByFirstNameEqualsAndAge(
                            "Moeen",
                            25)
                    .forEach(System.out::println);

            System.out.println("***************************************");

            studentRepository.findStudentsByFirstNameEqualsAndAgeIsGreaterThan(
                            "Maria",
                            24)
                    .forEach(System.out::println);
        };
    }
}
