package com.costswatcher.costswatcher.expense;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupExpenseRepository extends JpaRepository<GroupExpenseEntity, Integer> {
    List<GroupExpenseEntity> findAllByIdGroup(int idGroup);
    void deleteByIdExpenseAndIdGroup(int idExpense, int idGroup);
}
