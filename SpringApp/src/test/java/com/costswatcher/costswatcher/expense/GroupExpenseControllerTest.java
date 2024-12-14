package com.costswatcher.costswatcher.expense;

import com.costswatcher.costswatcher.user.UserEntity;
import jakarta.persistence.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GroupExpenseControllerTest {

    @Mock
    private GroupExpenseService groupExpenseService;

    @Mock
    private Model model;

    @InjectMocks
    private GroupExpenseController groupExpenseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void deleteGroupExpense_WhenUserIsSignedIn_ShouldRedirectToEditGroup() {
        UserEntity.signedInUser = new UserEntity();
        int idGroup = 123;
        int idExpense = 456;

        String viewName = groupExpenseController.deleteGroupExpense(idGroup, idExpense, model);

        assertEquals("redirect:/group/edit/" + idGroup, viewName);
        verify(groupExpenseService, times(1)).deleteGroupExpense(idExpense, idGroup);
    }

    @Test
    void deleteGroupExpense_WhenUserIsNull_ShouldRedirectToHomePage() {
        UserEntity.signedInUser = null;
        int idGroup = 123;
        int idExpense = 456;

        String viewName = groupExpenseController.deleteGroupExpense(idGroup, idExpense, model);

        assertEquals("redirect:/", viewName);
        verify(groupExpenseService, never()).deleteGroupExpense(anyInt(), anyInt());
    }

    @Test
    void testEditGroupExpense() {
        int idGroup = 123;
        int idExpense = 456;
        Model model = mock(Model.class);
        Optional<GroupExpenseEntity> mockExpense = Optional.of(new GroupExpenseEntity());
        List<Tuple> mockParticipants = new ArrayList<>();

        when(groupExpenseService.getExpenseObjByIdExpense(idExpense)).thenReturn(mockExpense);
        when(groupExpenseService.getExpenseParticipants(idExpense)).thenReturn(mockParticipants);

        UserEntity.signedInUser = new UserEntity();
        String viewName = groupExpenseController.editGroupExpense(idGroup, idExpense, model);

        assertEquals("edit_group_expense", viewName);

        verify(groupExpenseService, times(1)).getExpenseObjByIdExpense(idExpense);
        verify(model, times(1)).addAttribute("expenseEntity", mockExpense.get());
        verify(model, times(1)).addAttribute("groupId", idGroup);
        verify(model, times(1)).addAttribute("expenseId", idExpense);
        verify(model, times(1)).addAttribute("participants", mockParticipants);
    }

    @Test
    void testAddGroupExpense() {
        GroupExpenseService groupExpenseService = mock(GroupExpenseService.class);
        GroupExpenseController groupExpenseController = new GroupExpenseController(groupExpenseService);

        Model model = mock(Model.class);

        UserEntity.signedInUser = new UserEntity();
        String result = groupExpenseController.addGroupExpense(1, model);

        assertEquals("add_group_expense", result);
    }

    @Test
    void testDeleteExpenseParticipant() {
        GroupExpenseService groupExpenseService = mock(GroupExpenseService.class);
        GroupExpenseController groupExpenseController = new GroupExpenseController(groupExpenseService);

        Model model = mock(Model.class);

        int groupId = 321;
        int expenseId = 111;
        int userId = 123;

        UserEntity.signedInUser = new UserEntity();
        String result = groupExpenseController.deleteExpenseParticipant(groupId, expenseId, userId, model);

        assertEquals("redirect:/group/"+ groupId + "/edit/group-expense/" + expenseId, result);
    }

    @Test
    void testAddParticipantToGroupExpense() {
        GroupExpenseService groupExpenseService = mock(GroupExpenseService.class);
        GroupExpenseController groupExpenseController = new GroupExpenseController(groupExpenseService);

        Model model = mock(Model.class);

        int expenseId = 123;
        int groupId = 456;

        UserEntity.signedInUser = new UserEntity();
        String result = groupExpenseController.addExpenseParticipant(groupId, expenseId, model);

        assertEquals("add_group_expense_participant", result);
    }
}
