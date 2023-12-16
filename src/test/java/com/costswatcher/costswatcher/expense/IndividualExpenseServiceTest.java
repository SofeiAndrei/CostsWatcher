package com.costswatcher.costswatcher.expense;

import com.costswatcher.costswatcher.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class IndividualExpenseServiceTest {

    @Mock
    private IndividualExpenseRepository individualExpenseRepository;

    @InjectMocks
    private IndividualExpenseService individualExpenseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllIndividualExpenses() {
        int groupId = 1;
        List<IndividualExpenseEntity> expectedExpenses = new ArrayList<>();
        when(individualExpenseRepository.findAllByIdGroup(groupId)).thenReturn(expectedExpenses);

        List<IndividualExpenseEntity> actualExpenses = individualExpenseService.getAllIndividualExpenses(groupId);

        assertEquals(expectedExpenses, actualExpenses);
        verify(individualExpenseRepository).findAllByIdGroup(groupId);
    }

    @Test
    void testDeleteIndividualExpense() {
        int expenseId = 1;
        int groupId = 1;

        individualExpenseService.deleteIndividualExpense(expenseId, groupId);

        verify(individualExpenseRepository).deleteByIdExpenseAndIdGroup(expenseId, groupId);
    }

    @Test
    void testAddIndividualExpense() {
        UserEntity.signedInUser = new UserEntity();
        IndividualExpenseEntity expense = new IndividualExpenseEntity();
        when(individualExpenseRepository.save(expense)).thenReturn(new IndividualExpenseEntity());

        IndividualExpenseEntity addedExpense = individualExpenseService.addIndividualExpense(expense);

        assertNotNull(addedExpense);
        verify(individualExpenseRepository).save(expense);
    }


    @Test
    void testDeleteAllByIdGroup() {
        int groupId = 1;

        individualExpenseService.deleteAllByIdGroup(groupId);

        verify(individualExpenseRepository).deleteAllByIdGroup(groupId);
    }

    @Test
    void testGetByIdExpense() {
        int expenseId = 1;
        IndividualExpenseEntity expectedExpense = new IndividualExpenseEntity();

        when(individualExpenseRepository.findById(expenseId)).thenReturn(Optional.of(expectedExpense));

        IndividualExpenseEntity actualExpense = individualExpenseService.getByIdExpense(expenseId);

        assertEquals(expectedExpense, actualExpense);
        verify(individualExpenseRepository).findById(expenseId);
    }

    @Test
    void testUpdateIndividualExpense() {
        IndividualExpenseEntity expense = new IndividualExpenseEntity();
        expense.setIdExpense(1);

        when(individualExpenseRepository.findById(expense.getIdExpense())).thenReturn(Optional.of(expense));
        when(individualExpenseRepository.save(expense)).thenReturn(expense);

        individualExpenseService.updateIndividualExpense(expense);

        verify(individualExpenseRepository).findById(expense.getIdExpense());
        verify(individualExpenseRepository).save(expense);
    }
}
