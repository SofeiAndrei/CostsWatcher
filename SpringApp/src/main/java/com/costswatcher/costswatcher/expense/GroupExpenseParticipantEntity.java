package com.costswatcher.costswatcher.expense;

import com.costswatcher.costswatcher.expense.helper.GroupExpenseParticipantId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table
public class GroupExpenseParticipantEntity {
    @EmbeddedId
    private GroupExpenseParticipantId id;
    private int amount;

    public GroupExpenseParticipantEntity() {
    }

    public GroupExpenseParticipantEntity(GroupExpenseParticipantId id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public GroupExpenseParticipantId getId() {
        return id;
    }

    public void setId(GroupExpenseParticipantId id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "GroupExpenseParticipantEntity{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }
}
