package by.matrosov.studentservice.dao;

import by.matrosov.studentservice.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDao extends JpaRepository<Student, Long> {
}
