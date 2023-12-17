package com.costswatcher.costswatcher.expense;

import com.costswatcher.costswatcher.expense.GroupExpenseEntity;
import com.costswatcher.costswatcher.expense.GroupExpenseParticipantRepository;
import com.costswatcher.costswatcher.expense.GroupExpenseRepository;
import com.costswatcher.costswatcher.expense.GroupExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class GroupExpenseServiceTest {

    @Mock
    private GroupExpenseRepository groupExpenseRepository;

    @Mock
    private GroupExpenseParticipantRepository groupExpenseParticipantRepository;

    @InjectMocks
    private GroupExpenseService groupExpenseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllGroupExpenses() {
        int groupId = 123;
        GroupExpenseEntity expense = new GroupExpenseEntity(1, "test", groupId, new Timestamp(System.currentTimeMillis()));
        List<GroupExpenseEntity> expectedExpenses = Collections.singletonList(expense);

        when(groupExpenseRepository.findAllByIdGroup(groupId)).thenReturn(expectedExpenses);

        List<GroupExpenseEntity> actualExpenses = groupExpenseService.getAllGroupExpenses(groupId);

        verify(groupExpenseRepository, times(1)).findAllByIdGroup(groupId);
        assertEquals(expectedExpenses, actualExpenses);
    }

    @Test
    void testDeleteGroupExpense() {
        int idExpense = 456;
        int idGroup = 789;

        groupExpenseService.deleteGroupExpense(idExpense, idGroup);

        verify(groupExpenseParticipantRepository, times(1)).deleteAllByIdExpense(idExpense);
        verify(groupExpenseRepository, times(1)).deleteByIdExpenseAndIdGroup(idExpense, idGroup);
    }

    @Test
    void testGetByIdExpense() {
        int expenseId = 1;
        GroupExpenseEntity mockExpense = new GroupExpenseEntity();
        mockExpense.setIdExpense(expenseId);

        when(groupExpenseRepository.findById(expenseId)).thenReturn(Optional.of(mockExpense));

        Optional<GroupExpenseEntity> result = groupExpenseService.getExpenseObjByIdExpense(expenseId);

        assertTrue(result.isPresent());

        verify(groupExpenseRepository, times(1)).findById(expenseId);
    }
}
