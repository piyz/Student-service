package by.matrosov.studentservice.model;

import javax.persistence.*;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "group_id")
    private long groupId;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "curator_name")
    private String curatorName;

    @Column(name = "specialty")
    private String specialty;
}
