package by.matrosov.studentservice.service;

import by.matrosov.studentservice.dao.GroupDao;
import by.matrosov.studentservice.dao.RoleDao;
import by.matrosov.studentservice.dao.StudentDao;
import by.matrosov.studentservice.model.Group;
import by.matrosov.studentservice.model.Role;
import by.matrosov.studentservice.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<Student> getStudentsByGroupName(String groupName) {
        Group currGroup = groupDao.findByGroupName(groupName);
        return studentDao.getStudentsByGroup(currGroup);
    }

    @Override
    public List<Student> getStudentsByBirthday(Date studentBirthday) {
        return studentDao.getStudentsByBirthday(studentBirthday);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentDao.findAll();
    }

    @Override
    public void removeStudent(long studentId) {
        Student student = studentDao.getOne(studentId);
        student.setEnabled(0);
        studentDao.save(student);
    }

    @Override
    public Student getStudentById(long studentId) {
        return studentDao.getOne(studentId);
    }

    @Override
    public void addStudent(Student student) {
        student.setPassword(bCryptPasswordEncoder.encode(student.getPassword()));
        Role userGroup = roleDao.findByRoleName("USER");
        student.setRoles(new HashSet<>(Arrays.asList(userGroup)));
        student.setEnabled(1);
        studentDao.save(student);
    }

    @Override
    public void editStudent(Student student) {
        studentDao.save(student);
    }

    @Override
    public List<Student> getStudentsByGroupId(long groupId) {
        return studentDao.getStudentsByGroup_GroupId(groupId);
    }

    @Override
    public List<Student> getStudentsByLastName(String lastName) {
        return studentDao.getStudentsByLastName(lastName);
    }

    @Override
    public List<Student> getAllByEnabled(int a) {
        return studentDao.getAllByEnabled(1);
    }
}
