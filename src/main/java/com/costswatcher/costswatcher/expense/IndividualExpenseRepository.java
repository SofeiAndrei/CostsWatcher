package com.costswatcher.costswatcher.expense;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IndividualExpenseRepository extends JpaRepository<IndividualExpense, Integer> {
}
