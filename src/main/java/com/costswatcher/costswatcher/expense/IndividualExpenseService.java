package com.costswatcher.costswatcher.expense;

import com.costswatcher.costswatcher.user.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class IndividualExpenseService {
    private IndividualExpenseRepository individualExpenseRepository;

    public IndividualExpenseService(IndividualExpenseRepository individualExpenseRepository) {
        this.individualExpenseRepository = individualExpenseRepository;
    }

    public List<IndividualExpenseEntity> getAllIndividualExpenses(int groupId) {
        return individualExpenseRepository.findAllByIdGroup(groupId);
    }

    @Transactional
    public void deleteIndividualExpense(int expenseId, int groupId) {
        individualExpenseRepository.deleteByIdExpenseAndIdGroup(expenseId, groupId);
    }

    public IndividualExpenseEntity addIndividualExpense(IndividualExpenseEntity expenseEntity) {
        if (UserEntity.signedInUser == null)
            return null;
        expenseEntity.setIdUser(UserEntity.signedInUser.getIdUser());
        expenseEntity.setDate(new Timestamp(System.currentTimeMillis()));
        expenseEntity.setIdExpense(-1);
        IndividualExpenseEntity newExpense = individualExpenseRepository.save(expenseEntity);
        if (newExpense.getIdExpense() == -1)
            return null;
        return newExpense;
    }

    public void deleteAllByIdGroup(int idGroup) {
        individualExpenseRepository.deleteAllByIdGroup(idGroup);
    }

    public IndividualExpenseEntity getByIdExpense(int idExpense) {
        Optional<IndividualExpenseEntity> expense = individualExpenseRepository.findById(idExpense);
        return expense.orElse(null);
    }

    public void updateIndividualExpense(IndividualExpenseEntity expense) {
        if (this.getByIdExpense(expense.getIdExpense()) == null)
            return;
        individualExpenseRepository.save(expense);
    }
}
