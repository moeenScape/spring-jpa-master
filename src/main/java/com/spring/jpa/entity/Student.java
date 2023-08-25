package com.spring.jpa.entity;

import jakarta.persistence.*;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "student", uniqueConstraints = {
        @UniqueConstraint(name = "student_email_unique",
                columnNames = "email")})
public class Student implements Serializable {
    @Id
    @SequenceGenerator(name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE,
            generator = "student_sequence")
    @Column(name = "id",
            updatable = false)
    private Long id;
    @Column(name = "first_name",
            updatable = false,
            columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "last_name",
            updatable = false,
            columnDefinition = "TEXT")
    private String lastName;

    @Column(name = "email",
            updatable = false,
            length = 100,
            columnDefinition = "VARCHAR(100)")
    private String email;

    @Column(name = "age",
            nullable = false)
    private Integer age;

    public Student() {
    }

    public Student(String firstName, String lastName, String email, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", LastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
