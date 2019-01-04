package by.matrosov.studentservice.service;

import by.matrosov.studentservice.model.Student;

import java.util.Date;
import java.util.List;

public interface StudentService {
    List<Student> getStudentsByGroupName(String groupName);
    List<Student> getStudentsByBirthday(Date studentBirthday);
    List<Student> getAllStudents();
    void removeStudent(long studentId);
    Student getStudentById(long studentId);
    void addStudent(Student student);
    List<Student> getStudentsByGroupId(long groupId);
    List<Student> getStudentsByLastName(String lastName);

    void editStudent(Student student);
    List<Student> getAllByEnabled(int b);
}
