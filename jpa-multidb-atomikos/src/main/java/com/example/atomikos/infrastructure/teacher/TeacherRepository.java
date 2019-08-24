package com.example.atomikos.infrastructure.teacher;

import com.example.atomikos.domain.teacher.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ：Jerry
 * @date ：Created in 2019/7/23 15:47
 */
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
}
