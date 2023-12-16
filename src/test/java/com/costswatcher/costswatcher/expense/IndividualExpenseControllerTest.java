package com.costswatcher.costswatcher.expense;

import com.costswatcher.costswatcher.user.UserEntity;
import com.costswatcher.costswatcher.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IndividualExpenseController.class)
public class IndividualExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IndividualExpenseService individualExpenseService;

    @MockBean
    private UserService userService;

    @Test
    public void testDeleteIndividualExpense() throws Exception {
        UserEntity.signedInUser = new UserEntity();

        int groupId = 123;
        int expenseId = 456;

        doNothing().when(individualExpenseService).deleteIndividualExpense(expenseId, groupId);

        mockMvc.perform(get("/group/{groupId}/delete/individual-expense/{expenseId}", groupId, expenseId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/group/edit/" + groupId));
    }

    @Test
    public void testEditIndividualExpense() throws Exception {
        int groupId = 123;
        int expenseId = 456;

        UserEntity signedInUser = new UserEntity();
        signedInUser.setIdUser(1);
        UserEntity expenseUser = new UserEntity();
        expenseUser.setIdUser(2);
        expenseUser.setUsername("JohnDoe");
        IndividualExpenseEntity expenseEntity = new IndividualExpenseEntity();
        expenseEntity.setIdUser(2);

        UserEntity.signedInUser = signedInUser;

        when(individualExpenseService.getByIdExpense(anyInt())).thenReturn(expenseEntity);
        when(userService.getUserByIdUser(anyInt())).thenReturn(java.util.Optional.of(expenseUser));

        mockMvc.perform(get("/group/{groupId}/edit/individual-expense/{expenseId}", groupId, expenseId))
                .andExpect(status().isOk())
                .andExpect(model().attribute("groupId", groupId))
                .andExpect(model().attribute("expenseEntity", expenseEntity))
                .andExpect(model().attribute("username", expenseUser.getUsername()))
                .andExpect(view().name("edit_individual_expense"));
    }

    @Test
    public void testSubmitEditedIndividualExpense() throws Exception {
        UserEntity.signedInUser = new UserEntity();

        int groupId = 123;
        int expenseId = 456;

        IndividualExpenseEntity formExpense = new IndividualExpenseEntity();
        formExpense.setIdUser(1);
        formExpense.setName("ExpenseName");
        formExpense.setAmount(100);

        UserEntity user = new UserEntity();
        user.setIdUser(1);
        user.setUsername("JohnDoe");
        when(userService.getUserByIdUser(anyInt())).thenReturn(java.util.Optional.of(user));

        IndividualExpenseEntity existingExpense = new IndividualExpenseEntity();
        existingExpense.setIdExpense(expenseId);
        existingExpense.setIdGroup(groupId);
        when(individualExpenseService.getByIdExpense(expenseId)).thenReturn(existingExpense);

        formExpense.setAmount(0);

        mockMvc.perform(post("/group/{groupId}/edit/individual-expense/{expenseId}/submit", groupId, expenseId)
                        .flashAttr("formExpense", formExpense))
                .andExpect(status().isOk())
                .andExpect(view().name("edit_individual_expense"))
                .andExpect(model().attributeExists("invalidFormData"));
    }

    @Test
    public void testAddIndividualExpense() throws Exception {
        UserEntity.signedInUser = new UserEntity();

        int groupId = 123;

        mockMvc.perform(get("/group/{groupId}/add/individual-expense", groupId))
                .andExpect(status().isOk())
                .andExpect(view().name("add_individual_expense"))
                .andExpect(model().attribute("groupId", groupId))
                .andExpect(model().attributeExists("expenseEntity"));
    }

    @Test
    public void testSubmitNewIndividualExpense() throws Exception {
        UserEntity.signedInUser = new UserEntity();

        int groupId = 123;

        IndividualExpenseEntity formExpense = new IndividualExpenseEntity();
        formExpense.setName("ExpenseName");
        formExpense.setAmount(100);

        when(individualExpenseService.addIndividualExpense(formExpense)).thenReturn(formExpense);

        mockMvc.perform(post("/group/{groupId}/add/individual-expense/submit", groupId)
                        .flashAttr("expenseEntity", formExpense))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/group/edit/" + groupId));
    }

}

