package by.matrosov.studentservice.dao;

import by.matrosov.studentservice.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupDao extends JpaRepository<Group, Long> {
    Group findByGroupName(String groupName);
    Group findGroupByGroupId(long groupId);
}
