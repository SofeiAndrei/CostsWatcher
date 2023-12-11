package com.costswatcher.costswatcher.group;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroupMemberController.class)
@AutoConfigureMockMvc
class GroupMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupMemberService groupMemberService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getGroupMembers() throws Exception {
        // Create mock group members
        GroupMemberId id1 = new GroupMemberId(2, 8);
        GroupMemberId id2 = new GroupMemberId(5, 4);
        GroupMember member1 = new GroupMember(id1, false);
        GroupMember member2 = new GroupMember(id2, true);

        // Mock the behavior of the service method to return a list of mock group members
        List<GroupMember> mockGroupMembers = Arrays.asList(member1, member2);
        when(groupMemberService.getGroupMembers()).thenReturn(mockGroupMembers);

        // Perform GET request to retrieve group members
        mockMvc.perform(get("/api/v1/group_member"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2)) // Assuming there are two members in the list
                .andExpect(jsonPath("$[0].id").value(id1))
                .andExpect(jsonPath("$[0].pendingRequest").value(false))
                .andExpect(jsonPath("$[1].id").value(id2))
                .andExpect(jsonPath("$[1].pendingRequest").value(true));
    }

    @Test
    void addNewGroupMember() throws Exception {
        GroupMemberId id = new GroupMemberId(2, 8);
        GroupMember newMember = new GroupMember(id, true);

        // Convert the new member object to JSON
        String jsonMember = objectMapper.writeValueAsString(newMember);

        // Mock the service method to do nothing (since addNewGroupMember has void return type)
        doNothing().when(groupMemberService).addNewGroupMember(any(GroupMember.class));

        // Perform POST request to add a new group member
        mockMvc.perform(post("/api/v1/group_member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMember))
                .andExpect(status().isOk());
    }

    @Test
    void removeGroupMember() throws Exception {
        int memberId = 1;
        int groupId = 2;

        // Mock the service method to do nothing (since removeGroupMember has void return type)
        doNothing().when(groupMemberService).removeGroupMember(any(GroupMemberId.class));

        // Perform DELETE request to remove a group member
        mockMvc.perform(delete("/api/v1/group_member/{memberId}/{groupId}", memberId, groupId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
