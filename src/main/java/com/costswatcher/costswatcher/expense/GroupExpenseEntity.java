package com.costswatcher.costswatcher.expense;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table
public class GroupExpenseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idExpense;
    private String name;
    private int idGroup;
    private Timestamp date;

    public GroupExpenseEntity() {
    }

    public GroupExpenseEntity(int idExpense, String name, int idGroup, Timestamp date) {
        this.idExpense = idExpense;
        this.name = name;
        this.idGroup = idGroup;
        this.date = date;
    }

    public int getIdExpense() {
        return idExpense;
    }

    public void setIdExpense(int idExpense) {
        this.idExpense = idExpense;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "GroupExpenseEntity{" +
                "idExpense=" + idExpense +
                ", name='" + name + '\'' +
                ", idGroup=" + idGroup +
                ", date=" + date +
                '}';
    }
}
