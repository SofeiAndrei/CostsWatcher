package com.costswatcher.costswatcher.expense;

import com.costswatcher.costswatcher.expense.helper.GroupExpenseParticipantId;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface GroupExpenseParticipantRepository extends JpaRepository<GroupExpenseParticipantEntity, GroupExpenseParticipantId> {
    @Modifying
    @Query("DELETE FROM GroupExpenseParticipantEntity ep WHERE ep.id.idExpense = ?1")
    void deleteAllByIdExpense(int idExpense);
    @Modifying
    @Query("SELECT us.username, us.idUser, ep.amount " +
            "FROM GroupExpenseParticipantEntity ep, UserEntity us " +
            "WHERE ep.id.idUser = us.idUser and ep.id.idExpense = ?1")
    List<Tuple> getAllByIdExpense(int idExpense);
}
