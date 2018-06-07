package by.matrosov.studentservice.model;

import javax.persistence.*;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "group_id")
    private long groupId;
}
