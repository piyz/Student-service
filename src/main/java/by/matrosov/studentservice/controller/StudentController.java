package by.matrosov.studentservice.controller;

import by.matrosov.studentservice.model.Group;
import by.matrosov.studentservice.model.Student;
import by.matrosov.studentservice.service.GroupService;
import by.matrosov.studentservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class StudentController {

    private final StudentService studentService;

    private final GroupService groupService;

    @Autowired
    public StudentController(StudentService studentService, GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    @RequestMapping(method = RequestMethod.GET) //add value = "/students"
    public ModelAndView getStudents(){
        ModelAndView modelAndView = new ModelAndView();
        List<Student> studentList = studentService.getAllStudents();
        modelAndView.addObject("studentList", studentList);
        modelAndView.setViewName("students");

        //for add student need to create new student here? hello
        //need to check model.contains('attribute') ? or don't
        modelAndView.addObject("student", new Student());
        //------------------------------------------------------
        return modelAndView;
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public ModelAndView getGroups(){
        ModelAndView modelAndView = new ModelAndView();
        List<Group> groupList = groupService.getAllGroups();
        modelAndView.addObject("groupList", groupList);
        modelAndView.setViewName("groups");
        return modelAndView;
    }

    @RequestMapping(value = "/student/delete", method = RequestMethod.GET)
    public String deleteStudent(@RequestParam(name = "studentId") long studentId) {
        studentService.removeStudent(studentId);
        return "redirect:/students";
    }

    @RequestMapping(value = "/groups/delete", method = RequestMethod.GET)
    public String deleteGroup(@RequestParam(name = "groupId") long groupId) {
        groupService.removeGroup(groupId);
        return "redirect:/groups";
    }

    @RequestMapping(value = "/student/edit", method = RequestMethod.GET)
    public String getEditStudentPage(@RequestParam(name = "studentId") long studentId, Model model){
        Student student = studentService.getStudentById(studentId);
        model.addAttribute("student", student);
        return "student-edit";
    }

    @RequestMapping(value = "/student/edit", method = RequestMethod.POST)
    public String editStudent(@RequestParam(name = "studentId") long studentId, Student student){
        //impl errors with binding result
        //add @valid to student
        //check null
        Student studentExist = studentService.getStudentById(studentId);

        studentExist.setFirstName(student.getFirstName());
        studentExist.setLastName(student.getLastName());

        studentService.editStudent(studentExist); //rename to save
        //model.addAttribute("studentId", studentExist);

        return "redirect:/students";
    }

    @RequestMapping(value = "/groups/edit", method = RequestMethod.GET)
    public String getEditGroupPage(@RequestParam(name = "groupId") long groupId, Model model){
        Group group = groupService.getGroupById(groupId);
        model.addAttribute("group", group);
        return "groups-edit";
    }

    @RequestMapping(value = "/groups/edit", method = RequestMethod.POST)
    public String editGroup(@RequestParam(name = "groupId") long groupId, Group group){
        //impl errors with binding result
        //add @valid to group
        //check null
        Group groupExist = groupService.getGroupById(groupId);
        groupExist.setGroupName(group.getGroupName());
        groupExist.setCuratorName(group.getCuratorName());
        groupExist.setSpecialty(group.getSpecialty());
        groupService.saveGroup(groupExist);
        return "redirect:/groups";
    }

    @RequestMapping(value = "/student/add", method = RequestMethod.POST)
    public String addStudent(Student student){
        //add @valid, bindingResult
        studentService.editStudent(student); //rename on save
        return "redirect:/students";
    }

    @ModelAttribute("allGroups")
    public List<Group> getAllGroups(){
        return groupService.getAllGroups();
    }

    @ModelAttribute("genderOptions")
    public char[] getGenderOptions(){
        char[] array = new char[2];
        array[0] = 'м';
        array[1] = 'ж';
        return array;
    }

    @RequestMapping(value = "groups/move", method = RequestMethod.GET)
    public String moveGroup(@RequestParam(name = "groupId") long groupId){
        Group currentGroup = groupService.getGroupById(groupId);
        long currentGroupName = Long.parseLong(currentGroup.getGroupName());
        long newGroupName = currentGroupName + 100;
        currentGroup.setGroupName(String.valueOf(newGroupName));
        groupService.saveGroup(currentGroup);
        return "redirect:/groups";
    }

    @RequestMapping(value = "groups/open", method = RequestMethod.GET)
    public String openGroup(@RequestParam(name = "groupId") long groupId, Model model){
        List<Student> studentList = studentService.getStudentsByGroupId(groupId);
        model.addAttribute("studentList", studentList);
        return "groups-open";
    }
}
