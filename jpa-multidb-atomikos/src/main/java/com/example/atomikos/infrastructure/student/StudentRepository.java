package com.example.atomikos.infrastructure.student;

import com.example.atomikos.domain.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ：Jerry
 * @date ：Created in 2019/7/23 15:46
 */
public interface StudentRepository extends JpaRepository<Student, Integer> {
}
