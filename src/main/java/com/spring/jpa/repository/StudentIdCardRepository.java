package com.spring.jpa.repository;

import com.spring.jpa.entity.StudentIdCard;
import org.springframework.data.repository.CrudRepository;

public interface StudentIdCardRepository extends CrudRepository<StudentIdCard, Long> {
}
