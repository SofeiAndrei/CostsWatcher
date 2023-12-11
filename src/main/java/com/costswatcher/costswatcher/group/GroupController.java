package com.costswatcher.costswatcher.group;

import com.costswatcher.costswatcher.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/group")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<Groups> getGroups(){
        return groupService.getGroups();
    }

    @PostMapping
    public void createNewGroup(@RequestBody Groups group) {
        groupService.createNewGroup(group);
    }

    @DeleteMapping(path = "{groupId}")
    public void deleteGroup(@PathVariable("groupId") Integer groupId){
        groupService.deleteGroup(groupId);
    }

    @PutMapping("/{groupId}")
    public void updateGroupName(@PathVariable("groupId") Integer groupId, @RequestBody Groups updatedGroup) {
        groupService.updateGroupName(groupId, updatedGroup.getGroupName());
    }
}
