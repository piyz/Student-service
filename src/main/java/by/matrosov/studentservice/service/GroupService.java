package by.matrosov.studentservice.service;

import by.matrosov.studentservice.model.Group;

import java.util.List;

public interface GroupService {
    List<Group> getAllGroups();
    Group getGroupById(long groupId);
    void saveGroup(Group group);
}