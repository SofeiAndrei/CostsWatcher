package com.costswatcher.costswatcher.expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndividualExpenseRepository extends JpaRepository<IndividualExpenseEntity, Integer> {
    List<IndividualExpenseEntity> findAllByIdGroup(int idGroup);
    void deleteByIdExpenseAndIdGroup(int idExpense, int idGroup);
    void deleteAllByIdGroup(int idGroup);
}
