package com.costswatcher.costswatcher.group;

import com.costswatcher.costswatcher.expense.IndividualExpenseRepository;
import com.costswatcher.costswatcher.expense.IndividualExpenseService;
import com.costswatcher.costswatcher.user.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMemberService groupMemberService;
    private final IndividualExpenseService individualExpenseService;

    @Autowired
    public GroupService(GroupRepository groupRepository, GroupMemberService groupMemberService, IndividualExpenseService individualExpenseService) {
        this.groupRepository = groupRepository;
        this.groupMemberService = groupMemberService;
        this.individualExpenseService = individualExpenseService;
    }

    public List<GroupEntity> findAllForUser(int idUser) {
        return groupRepository.findAllForUser(idUser);
    }

    public Optional<GroupEntity> getGroupById(int groupId) { return groupRepository.findById(groupId); }

    public void createNewGroup(GroupEntity group) {
        GroupEntity savedGroup = groupRepository.save(group);
        GroupMember firstMember = new GroupMember(new GroupMemberId(UserEntity.signedInUser.getIdUser(), savedGroup.getIdGroup()), false);
        groupMemberService.addNewGroupMember(firstMember);
    }
    @Transactional
    public void deleteGroup(Integer groupId) {
        individualExpenseService.deleteAllByIdGroup(groupId);
        groupMemberService.removeGroupMemberByGroupId(groupId);
        groupRepository.deleteById(groupId);
    }

    public List<UserEntity> getAllGroupMembers(int idGroup) {
        return groupRepository.getAllMembersForGroup(idGroup);
    }
}
