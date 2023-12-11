package com.costswatcher.costswatcher.group;

import com.costswatcher.costswatcher.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller
@RestController
@RequestMapping(path = "api/v1/group_member")
public class GroupMemberController {
    private final GroupMemberService groupMemberService;

    @Autowired
    public GroupMemberController(GroupMemberService groupMemberService) {
        this.groupMemberService = groupMemberService;
    }

    @GetMapping
    public List<GroupMember> getGroupMembers() {
        return groupMemberService.getGroupMembers();
    }

    @PostMapping
    public void addNewGroupMember(@RequestBody GroupMember groupMember) {
        groupMemberService.addNewGroupMember(groupMember);
    }

    @DeleteMapping(path = "{memberId}/{groupId}")
    public void removeGroupMember(@PathVariable("memberId") Integer memberId, @PathVariable("groupId") Integer groupId) {
        groupMemberService.removeGroupMember(new GroupMemberId(memberId, groupId));
    }
}