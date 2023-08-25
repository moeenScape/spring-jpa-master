package com.spring.jpa;

import com.github.javafaker.Faker;
import com.spring.jpa.entity.Student;
import com.spring.jpa.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.sound.midi.Soundbank;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            generateRandomStudent(studentRepository);
            springJpa(studentRepository);
            sorting(studentRepository);
            pagging(studentRepository);


        };
    }

    private void pagging(StudentRepository studentRepository) {

        System.out.println("Pageing and Sorting By First Name");
        Sort sort = Sort.by("firstName").ascending()
                .and(Sort.by("age").descending());

        PageRequest pageRequest = PageRequest.of(
                0,
                10,
                sort
        );
        studentRepository.findAll(pageRequest)
                .forEach(student -> {
                    System.out.println(student.getFirstName() + "<----->" + student.getAge());
                });
    }

    private void sorting(StudentRepository studentRepository) {
        System.out.println("Sorting By First Name");
        Sort sort = Sort.by("firstName").ascending()
                .and(Sort.by("age").descending());
        studentRepository.findAll(sort)
                .forEach(student -> {
                    System.out.println(student.getFirstName() + "<----->" + student.getAge());
                });
    }

    private void generateRandomStudent(StudentRepository studentRepository) {
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
    }

    private void springJpa(StudentRepository studentRepository) {

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
        studentRepository.findById(1L).ifPresentOrElse(System.out::println, () -> {
            System.out.println("Student was deleted");
        });

        System.out.println("***********************************************************");
        System.out.println("Check Student if delete work or not");
        Optional<Student> newStudent = studentRepository.findById(2L);
        Student updateStudent = new Student();
        if (newStudent.isPresent()) {
            updateStudent = newStudent.get();
            updateStudent.setFirstName("Update");
            updateStudent.setLastName("Update");
            updateStudent.setAge(65);
        }
        studentRepository.updateStudentById(updateStudent.getId(), updateStudent.getFirstName());
        studentRepository.findById(updateStudent.getId()).ifPresentOrElse(System.out::println, () -> {
            System.out.println("Student is update");
        });

    }
}
