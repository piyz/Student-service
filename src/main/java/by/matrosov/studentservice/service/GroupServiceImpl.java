package by.matrosov.studentservice.service;

import by.matrosov.studentservice.dao.GroupDao;
import by.matrosov.studentservice.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService{

    @Autowired
    private GroupDao groupDao;

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
        group.setEnabled(1);
        groupDao.save(group);
    }

    @Override
    public void removeGroup(long groupId) {
        Group group = groupDao.getOne(groupId);
        group.setEnabled(0);
        groupDao.save(group);
        //groupDao.deleteById(groupId);
    }

    @Override
    public List<Group> getByEnabled(int a) {
        return groupDao.findByEnabled(1);
    }
}
