package com.example.atomikos.controller;

import com.example.atomikos.application.TestApplication;
import com.example.atomikos.domain.student.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：Jerry
 * @date ：Created in 2019/7/23 15:53
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestApplication testApplication;

    @RequestMapping("/add")
    public void add() {
        testApplication.add(1);
    }

    @RequestMapping("/test")
    public void test() {
        testApplication.add(0);
    }

    @RequestMapping("/list")
    public List<Student> list() {
        List<Student> list = testApplication.findList();
        return list;
    }
}
