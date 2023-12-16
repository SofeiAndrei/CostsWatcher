package com.costswatcher.costswatcher.expense;

import com.costswatcher.costswatcher.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        GroupExpenseEntity mockExpense = new GroupExpenseEntity();

        when(groupExpenseService.getByIdExpense(idExpense)).thenReturn(mockExpense);

        String viewName = groupExpenseController.editGroupExpense(idGroup, idExpense, model);

        assertEquals("edit_group_expense", viewName);

        verify(groupExpenseService, times(1)).getByIdExpense(idExpense);
        verify(model, times(1)).addAttribute("expenseEntity", mockExpense);
        verify(model, times(1)).addAttribute("idGroup", idGroup);
        verify(model, times(1)).addAttribute("idExpense", idExpense);
    }

    @Test
    void testAddGroupExpense() {
        GroupExpenseService groupExpenseService = mock(GroupExpenseService.class);
        GroupExpenseController groupExpenseController = new GroupExpenseController(groupExpenseService);

        Model model = mock(Model.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        String result = groupExpenseController.addGroupExpense(model, redirectAttributes);

        assertEquals("add_group_expense", result);
    }

    @Test
    void testDeleteMember() {
        GroupExpenseService groupExpenseService = mock(GroupExpenseService.class);
        GroupExpenseController groupExpenseController = new GroupExpenseController(groupExpenseService);

        Model model = mock(Model.class);

        int memberId = 123;

        String result = groupExpenseController.deleteMember(memberId, model);

        assertEquals("redirect:/group/members", result);
    }

    @Test
    void testAddMemberToGroupExpense() {
        GroupExpenseService groupExpenseService = mock(GroupExpenseService.class);
        GroupExpenseController groupExpenseController = new GroupExpenseController(groupExpenseService);

        Model model = mock(Model.class);

        int memberId = 123;
        int groupId = 456;

        String result = groupExpenseController.addMemberToGroupExpense(memberId, groupId, model);

        assertEquals("redirect:/group/edit/" + groupId, result);
    }
}
