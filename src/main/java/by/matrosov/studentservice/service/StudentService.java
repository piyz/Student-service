package by.matrosov.studentservice.service;

import by.matrosov.studentservice.model.Group;
import by.matrosov.studentservice.model.Student;

import java.util.Date;
import java.util.List;

public interface StudentService {
    List<Student> getStudentsByGroupName(String groupName);
    List<Student> getStudentsByBirthday(Date studentBirthday);
    List<Student> getStudentsBySex(Character sex);
    List<Student> getAllStudents();
    List<Group> getAllGroups();
    void removeStudent(long studentId);
    Student getStudentById(long studentId);
}
