package by.matrosov.studentservice.controller;

import by.matrosov.studentservice.model.Group;
import by.matrosov.studentservice.model.Student;
import by.matrosov.studentservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public ModelAndView getStudents(){
        ModelAndView modelAndView = new ModelAndView();
        List<Student> studentList = studentService.getAllStudents();
        modelAndView.addObject("studentList", studentList);
        modelAndView.setViewName("students");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getGroups(){
        ModelAndView modelAndView = new ModelAndView();
        List<Group> groupList = studentService.getAllGroups();
        modelAndView.addObject("groupList", groupList);
        modelAndView.setViewName("groups");
        return modelAndView;
    }

    @RequestMapping(value = "/delete_student", method = RequestMethod.GET)
    public String deleteStudent(@RequestParam(name = "studentId") long studentId) {
        studentService.removeStudent(studentId);
        return "redirect:/students";
    }

    @RequestMapping(value = "/edit_student", method = RequestMethod.GET)
    public String getEditStudentPage(@RequestParam(name = "studentId") long studentId, Model model){
        Student student = studentService.getStudentById(studentId);
        model.addAttribute("student", student);
        return "student-edit";
    }

    @RequestMapping(value = "/edit_student", method = RequestMethod.POST)
    public String editStudent(@RequestParam(name = "studentId") long studentId){
        //impl saveUser in studentService
        return "redirect:/students";
    }
}
