package by.matrosov.studentservice.service;

import by.matrosov.studentservice.dao.GroupDao;
import by.matrosov.studentservice.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService{

    private final GroupDao groupDao;

    @Autowired
    public GroupServiceImpl(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    @Override
    public List<Group> getAllGroups() {
        return groupDao.findAll();
    }

    @Override
    public Group getGroupById(long groupId) {
        return groupDao.findGroupByGroupId(groupId);
    }

    @Override
    public void saveGroup(Group group) {
        groupDao.save(group);
    }
}
