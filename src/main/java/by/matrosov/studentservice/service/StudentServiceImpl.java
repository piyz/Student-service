package by.matrosov.studentservice.service;

import by.matrosov.studentservice.dao.GroupDao;
import by.matrosov.studentservice.dao.StudentDao;
import by.matrosov.studentservice.model.Group;
import by.matrosov.studentservice.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentDao studentDao;

    private final GroupDao groupDao;

    @Autowired
    public StudentServiceImpl(StudentDao studentDao, GroupDao groupDao) {
        this.studentDao = studentDao;
        this.groupDao = groupDao;
    }

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
        studentDao.deleteById(studentId);
    }

    @Override
    public Student getStudentById(long studentId) {
        return studentDao.getOne(studentId);
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
}
