package com.costswatcher.costswatcher.group;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroupController.class)
@AutoConfigureMockMvc
class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GroupService groupService;

    @Test
    void getGroups() throws Exception {
        List<Groups> mockGroups = Arrays.asList(
                new Groups(1, "Group 1"),
                new Groups(2, "Group 2")
        );

        when(groupService.getGroups()).thenReturn(mockGroups);

        mockMvc.perform(get("/api/v1/group"))
                .andExpect(status().isOk()) // Expect HTTP status 200 OK
                .andExpect(jsonPath("$").isArray()) // Expect a JSON array in the response body
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].idGroup").value(1))
                .andExpect(jsonPath("$[0].groupName").value("Group 1"))
                .andExpect(jsonPath("$[1].idGroup").value(2))
                .andExpect(jsonPath("$[1].groupName").value("Group 2"));
    }

    @Test
    void createNewGroup() throws Exception {
        Groups group = new Groups(1, "TestGroup");

        // Mock the behavior of the service method
        doNothing().when(groupService).createNewGroup(any(Groups.class));

        // Perform POST request to create a new group
        mockMvc.perform(post("/api/v1/group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(group)))
                .andExpect(status().isOk()); // Assuming the endpoint returns 200 OK upon successful creation
    }

    @Test
    void deleteGroup() throws Exception {
        int groupIdToDelete = 1;

        // Mock the behavior of the service method
        doNothing().when(groupService).deleteGroup(anyInt());

        // Perform DELETE request to delete a group
        mockMvc.perform(delete("/api/v1/group/{groupId}", groupIdToDelete))
                .andExpect(status().isOk()); // Assuming the endpoint returns 200 OK upon successful deletion
    }

    @Test
    void updateGroupName() throws Exception {
        int groupIdToUpdate = 1;
        String updatedName = "Updated Group Name";

        // Mock the behavior of the service method
        doNothing().when(groupService).updateGroupName(anyInt(), anyString());

        // Perform PUT request to update group name
        mockMvc.perform(put("/api/v1/group/{groupId}", groupIdToUpdate)
                        .contentType("application/json")
                        .content("{\"groupName\": \"" + updatedName + "\"}"))
                .andExpect(status().isOk()); // Assuming the endpoint returns 200 OK upon successful update
    }
}