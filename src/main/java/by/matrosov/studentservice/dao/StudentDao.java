package by.matrosov.studentservice.dao;

import by.matrosov.studentservice.model.Group;
import by.matrosov.studentservice.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface StudentDao extends JpaRepository<Student, Long> {
    List<Student> getStudentsByGroup(Group group);
    List<Student> getStudentsByBirthday(Date studentBirthday);
    List<Student> getStudentsBySex(Character sex);
    List<Student> getStudentsByGroup_GroupId(long groupId);
}
