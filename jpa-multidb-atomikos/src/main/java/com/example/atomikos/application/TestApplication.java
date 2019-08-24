package com.example.atomikos.application;

import com.example.atomikos.domain.student.model.Student;
import com.example.atomikos.domain.teacher.model.Teacher;
import com.example.atomikos.infrastructure.student.StudentRepository;
import com.example.atomikos.infrastructure.teacher.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：Jerry
 * @date ：Created in 2019/7/23 15:52
 */
@Service
public class TestApplication {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Transactional(rollbackFor = Exception.class)
    public void add(int code) {
        Student s1 = new Student();
        s1.setAge(10);
        s1.setGrade(10);
        s1.setName("s1");
        studentRepository.save(s1);

        Teacher t1 = new Teacher();
        t1.setAge(10);
        t1.setName("t1");
        t1.setCourse(10);
        teacherRepository.save(t1);
        int result = 1/code;
    }

    public List<Student> findList(){
        List<Student> list = studentRepository.findAll();
        return list;
    }
}
