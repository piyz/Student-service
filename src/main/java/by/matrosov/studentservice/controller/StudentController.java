package by.matrosov.studentservice.controller;

import by.matrosov.studentservice.model.Student;
import by.matrosov.studentservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        List<Student> studentList = studentService.getAllStudents();
        modelAndView.addObject("studentList", studentList);
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value = "/delete_student", method = RequestMethod.GET)
    public String handleDeleteUser(@RequestParam(name="studentId") long studentId) {
        studentService.removeStudent(studentId);
        return "redirect:/home";
    }
}
