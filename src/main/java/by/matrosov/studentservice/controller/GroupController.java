package by.matrosov.studentservice.controller;

import by.matrosov.studentservice.model.Group;
import by.matrosov.studentservice.model.Student;
import by.matrosov.studentservice.service.GroupService;
import by.matrosov.studentservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/admin/group", method = RequestMethod.GET)
    public ModelAndView getGroups(){
        ModelAndView modelAndView = new ModelAndView();
        List<Group> groupList = groupService.getByEnabled(1);
        modelAndView.addObject("groupList", groupList);
        modelAndView.setViewName("groups");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/group/delete", method = RequestMethod.GET)
    public String deleteGroup(@RequestParam(name = "groupId") long groupId) {
        groupService.removeGroup(groupId);
        return "redirect:/admin/group";
    }

    @RequestMapping(value = "/admin/group/edit", method = RequestMethod.GET)
    public String getEditGroupPage(@RequestParam(name = "groupId") long groupId, Model model){
        Group group = groupService.getGroupById(groupId);
        model.addAttribute("group", group);
        return "group-edit-form";
    }

    @RequestMapping(value = "/admin/group/edit", method = RequestMethod.POST)
    public String editGroup(@RequestParam(name = "groupId") long groupId, @Valid Group group,
                            BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "group-edit-form";
        }

        Group groupExist = groupService.getGroupById(groupId);

        groupExist.setGroupName(group.getGroupName());
        groupExist.setCuratorName(group.getCuratorName());
        groupExist.setSpecialty(group.getSpecialty());

        groupService.saveGroup(groupExist);
        model.addAttribute("success", "group edited successfully " + group.getGroupName());
        return "results";
    }

    @RequestMapping(value = "/admin/group/move", method = RequestMethod.GET)
    public String moveGroup(@RequestParam(name = "groupId") long groupId){
        Group currentGroup = groupService.getGroupById(groupId);
        long currentGroupName = Long.parseLong(currentGroup.getGroupName());
        long newGroupName = currentGroupName + 100;
        currentGroup.setGroupName(String.valueOf(newGroupName));
        groupService.saveGroup(currentGroup);
        return "redirect:/admin/group";
    }

    @RequestMapping(value = "/admin/group/open", method = RequestMethod.GET)
    public String openGroup(@RequestParam(name = "groupId") long groupId, Model model){
        List<Student> studentList = studentService.getStudentsByGroupId(groupId);
        model.addAttribute("studentList", studentList);
        return "group-open";
    }

    @RequestMapping(value = "/admin/group/add", method = RequestMethod.POST)
    public String addGroup(Model model, @Valid Group group, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "group-add-form";
        }
        groupService.saveGroup(group);
        model.addAttribute("success", "group saved successfully " + group.getGroupName());
        return "results";
    }

    @RequestMapping(value="/admin/group/add", method=RequestMethod.GET)
    public String showGroupForm(Model model) {
        model.addAttribute("group", new Group());
        return "group-add-form";
    }
}
