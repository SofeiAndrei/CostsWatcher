package com.costswatcher.costswatcher.expense.helper;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GroupExpenseParticipantId implements Serializable {
    private int idUser;
    private int idExpense;

    public GroupExpenseParticipantId() {
    }

    public GroupExpenseParticipantId(int idUser, int idExpense) {
        this.idUser = idUser;
        this.idExpense = idExpense;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdExpense() {
        return idExpense;
    }

    public void setIdExpense(int idExpense) {
        this.idExpense = idExpense;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupExpenseParticipantId that = (GroupExpenseParticipantId) o;
        return getIdUser() == that.getIdUser() && getIdExpense() == that.getIdExpense();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdUser(), getIdExpense());
    }

    @Override
    public String toString() {
        return "GroupExpenseParticipantId{" +
                "idUser=" + idUser +
                ", idExpense=" + idExpense +
                '}';
    }
}
