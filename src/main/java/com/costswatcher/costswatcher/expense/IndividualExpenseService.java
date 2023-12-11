package com.costswatcher.costswatcher.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndividualExpenseService {
    final private IndividualExpenseRepository indExpRepository;

    @Autowired
    public IndividualExpenseService(IndividualExpenseRepository indExpRepository) {
        this.indExpRepository = indExpRepository;
    }

    public void addExpense(IndividualExpense expense) {
        indExpRepository.save(expense);
    }

}
