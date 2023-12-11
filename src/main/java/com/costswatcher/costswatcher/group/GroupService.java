package com.costswatcher.costswatcher.group;

import com.costswatcher.costswatcher.constants.ConstantsUsers;
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

    @Autowired
    public GroupService(GroupRepository groupRepository, GroupMemberService groupMemberService) {
        this.groupRepository = groupRepository;
        this.groupMemberService = groupMemberService;
    }

    public List<Groups> findAllForUser(int idUser) {
        return groupRepository.findAllForUser(idUser);
    }

    public List<Groups> getGroups(){
        return groupRepository.findAll();
    }

    public Optional<Groups> getGroupById(int groupId) { return groupRepository.findById(groupId); }

    public void createNewGroup(Groups group) {
        Groups savedGroup = groupRepository.save(group);
        GroupMember firstMember = new GroupMember(new GroupMemberId(UserEntity.signedInUser.getIdUser(), savedGroup.getIdGroup()), false);
        groupMemberService.addNewGroupMember(firstMember);
    }
    @Transactional
    public void deleteGroup(Integer groupId) {
        groupMemberService.removeGroupMemberByGroupId(groupId);
        groupRepository.deleteById(groupId);
    }

    public void updateGroupName(Integer groupId, String newGroupName) {
        Groups groupToUpdate = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + groupId));

        groupToUpdate.setGroupName(newGroupName);
        groupRepository.save(groupToUpdate);
    }

    public List<UserEntity> getAllGroupMembers(int idGroup) {
        return groupRepository.getAllMembersForGroup(idGroup);
    }
}
