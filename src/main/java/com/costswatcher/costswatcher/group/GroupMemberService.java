package com.costswatcher.costswatcher.group;

import com.costswatcher.costswatcher.user.UserEntity;
import com.costswatcher.costswatcher.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupMemberService {
    private final GroupMemberRepository groupMemberRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Autowired
    public GroupMemberService(GroupMemberRepository groupMemberRepository, GroupRepository groupRepository, UserRepository userRepository) {
        this.groupMemberRepository = groupMemberRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public void addNewGroupMember(GroupMember groupMember) {
        Optional<GroupEntity> groupsOptional = groupRepository.findGroupsByIdGroup(groupMember.getId().getIdGroup());
        Optional<GroupMember> groupMemberOptional = groupMemberRepository.findGroupMemberById(groupMember.getId());
        // Need to check if user exists
        if (groupsOptional.isPresent() && groupMemberOptional.isEmpty()) {
            groupMemberRepository.save(groupMember);
        }
        else {
            throw new IllegalStateException("Something went wrong");
        }
    }

    public void removeGroupMember(GroupMemberId groupMemberId) {
        boolean exists = groupMemberRepository.existsById(groupMemberId);
        if (!exists) {
            throw new IllegalStateException("Member with id " + groupMemberId.toString() + " does not exists");
        }
        groupMemberRepository.deleteById(groupMemberId);
    }

    public void removeGroupMemberByGroupId(Integer groupId) {
        groupMemberRepository.deleteAllByGroupId(groupId);
    }

    public boolean checkIfGroupHasMember(int groupId, String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty())
            return false;
        Optional<GroupMember> member = groupMemberRepository.findGroupMemberById(new GroupMemberId(user.get().getIdUser(), groupId));
        return member.isPresent();
    }
}
