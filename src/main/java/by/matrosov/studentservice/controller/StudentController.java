package by.matrosov.studentservice.controller;

import by.matrosov.studentservice.model.Group;
import by.matrosov.studentservice.model.Student;
import by.matrosov.studentservice.service.GroupService;
import by.matrosov.studentservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    //for all users
    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public String hello(Model model, Principal principal){
        model.addAttribute("username", "You are logged in as " + principal.getName());

        List<Student> studentList = studentService.getAllStudents();
        model.addAttribute("studentList", studentList);

        return "index";
    }

    //only for admin
    @RequestMapping(value = "/admin/student", method = RequestMethod.GET) //add value = "/students"
    public ModelAndView getStudents(){
        ModelAndView modelAndView = new ModelAndView();
        //List<Student> studentList = studentService.getAllStudents();
        List<Student> studentList = studentService.getAllByEnabled(1);
        modelAndView.addObject("studentList", studentList);
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public ModelAndView getGroups(){
        ModelAndView modelAndView = new ModelAndView();
        List<Group> groupList = groupService.getByEnabled(1);
        modelAndView.addObject("groupList", groupList);
        modelAndView.setViewName("groups");

        modelAndView.addObject("group", new Group());
        return modelAndView;
    }

    @RequestMapping(value = "/admin/student/delete", method = RequestMethod.GET)
    public String deleteStudent(@RequestParam(name = "studentId") long studentId) {
        studentService.removeStudent(studentId);
        return "redirect:/admin/student";
    }

    @RequestMapping(value = "/groups/delete", method = RequestMethod.GET)
    public String deleteGroup(@RequestParam(name = "groupId") long groupId) {
        groupService.removeGroup(groupId);
        return "redirect:/groups";
    }

    @RequestMapping(value = "/admin/student/edit", method = RequestMethod.GET)
    public String getEditStudentPage(@RequestParam(name = "studentId") long studentId, Model model){
        Student student = studentService.getStudentById(studentId);
        model.addAttribute("student", student);
        return "student-edit";
    }

    @RequestMapping(value = "/admin/student/edit", method = RequestMethod.POST)
    public String editStudent(@RequestParam(name = "studentId") long studentId, @Valid Student student,
                              BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "student-edit";
        }

        Student studentExist = studentService.getStudentById(studentId);

        studentExist.setFirstName(student.getFirstName());
        studentExist.setLastName(student.getLastName());
        studentExist.setPatronymic(student.getPatronymic());
        studentExist.setUsername(student.getUsername());
        studentExist.setPassword(bCryptPasswordEncoder.encode(student.getPassword()));
        studentExist.setGender(student.getGender());
        studentExist.setGroup(student.getGroup());
        studentExist.setEducationYear(student.getEducationYear());

        studentService.editStudent(studentExist);
        model.addAttribute("success", "user edited successfully " + student.getFirstName() + " " + student.getLastName());
        return "results";
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

    @RequestMapping(value = "/groups/add", method = RequestMethod.POST)
    public String addGroup(Group group){
        //add @valid, bindingResult
        groupService.saveGroup(group);
        return "redirect:/groups";
    }

    @ModelAttribute("allGroups")
    public List<Group> getAllGroups(){
        return groupService.getByEnabled(1);
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

    @RequestMapping(value = "/student/search", method = RequestMethod.GET)
    public ModelAndView searchStudentsByLastName(@RequestParam("studentLastName") String lastName){
        List<Student> studentSearchList = studentService.getStudentsByLastName(lastName);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("studentSearchList", studentSearchList);
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @RequestMapping(value="/admin/student/add", method=RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("student", new Student());
        return "add-form";
    }

    @RequestMapping(value="/admin/student/add", method=RequestMethod.POST)
    public String checkStudentInfo(Model model, @Valid Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-form";
        }
        studentService.addStudent(student);
        model.addAttribute("success", "user saved successfully " + student.getFirstName() + " " + student.getLastName());
        return "results";
    }
}
