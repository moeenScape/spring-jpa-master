package com.spring.jpa;

import com.github.javafaker.Faker;
import com.spring.jpa.entity.Book;
import com.spring.jpa.entity.Course;
import com.spring.jpa.entity.Student;
import com.spring.jpa.entity.StudentIdCard;
import com.spring.jpa.repository.StudentIdCardRepository;
import com.spring.jpa.repository.StudentRepository;
import org.hibernate.Hibernate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, StudentIdCardRepository idCardRepository) {
        return args -> {
            //generateRandomStudent(studentRepository);
            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@gmail.com", firstName, lastName);

            Student student = new Student(firstName, lastName, email, faker.number().numberBetween(17, 25));

            student.addBook(new Book("Clean code", LocalDateTime.now().minusDays(4)));

            student.addBook(new Book("Learn and Earn", LocalDateTime.now().minusYears(4)));

            student.addBook(new Book("Environment", LocalDateTime.now().minusDays(4)));

            StudentIdCard studentIdCard = new StudentIdCard("12345678", student);

            student.enrolledCourse(new Course("Introduction to Programming","CSC"));
            student.enrolledCourse(new Course("Introduction to Unix","CSE"));



            student.setStudentIdCard(studentIdCard);
            studentRepository.save(student);

            idCardRepository.save(studentIdCard);
            System.out.println("$$$$$$$$$$$$Student$$$$$$$$$$$$$ ");
            studentRepository.findById(1L).ifPresentOrElse(student1 -> {
                System.out.println("Fetch with lazy.....");
                Hibernate.initialize(student1.getBooks());
                List<Book> books = student1.getBooks();
                books.forEach(book -> {
                    System.out.println(student1.getFirstName() + "borrowed " + book.getBookName());
                });
            }, () -> System.out.println("No student Present"));


//            System.out.println("$$$$$$$$$$$$ID-CARD$$$$$$$$$$$$$ ");
//            idCardRepository.findById(1L).ifPresentOrElse(System.out::println, () -> System.out.println("Student iis not exit"));
        };
    }

    private void generateRandomStudent(StudentRepository studentRepository) {
        Faker faker = new Faker();

        for (int i = 0; i < 20; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@gmail.com", firstName, lastName);

            Student student = new Student(firstName, lastName, email, faker.number().numberBetween(17, 25));
            studentRepository.save(student);
        }
    }
}
