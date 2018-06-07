package by.matrosov.studentservice.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "student_id")
    private long studentId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "patronymic")
    private String patronymic;

    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "sex")
    private char sex;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="group_id")
    private Group group;

    @Column(name = "education_year")
    private int educationYear;
}
