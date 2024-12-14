package com.costswatcher.costswatcher.group;

import com.costswatcher.costswatcher.expense.IndividualExpenseRepository;
import com.costswatcher.costswatcher.expense.IndividualExpenseService;
import com.costswatcher.costswatcher.user.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<GroupEntity> findAllForUser(int idUser, int pageIndex) {
        final int PAGE_SIZE = 7;
        Pageable pageQuery = PageRequest.of(pageIndex, PAGE_SIZE);
        return groupRepository.findAllForUser(idUser, pageQuery);
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
