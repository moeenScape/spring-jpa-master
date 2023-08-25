package com.spring.jpa.repository;

import com.spring.jpa.entity.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;


public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findStudentByEmail(String email);

    List<Student> findDistinctByLastNameAndFirstName(String firstName, String LastName);

    List<Student> findStudentByFirstNameOrLastName(String firstName,String astName);

    List<Student> findStudentByFirstNameAndLastName(String firstName, String lastName);

    List<Student> findStudentByAgeBetween(int start, int end);


    @Transactional
    @Modifying
    void deleteStudentById(Long id);
}
