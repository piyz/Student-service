package by.matrosov.studentservice.service;

import by.matrosov.studentservice.model.Group;
import by.matrosov.studentservice.model.Student;

import java.util.Date;
import java.util.List;

public interface StudentService {
    List<Student> getStudentsByGroupName(String groupName);
    List<Student> getStudentsByBirthday(Date studentBirthday);
    List<Student> getAllStudents();
    void removeStudent(long studentId);
    Student getStudentById(long studentId);
    void editStudent(Student student);
    List<Student> getStudentsByGroupId(long groupId);
}
