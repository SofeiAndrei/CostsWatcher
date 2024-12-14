package com.costswatcher.costswatcher.expense;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table
public class IndividualExpenseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idExpense;
    private int idGroup;
    private int idUser;
    private Timestamp date;
    private String name;
    private int amount;

    public IndividualExpenseEntity() {
    }

    public IndividualExpenseEntity(int idExpense, int idGroup, int idUser, Timestamp date, String name, int amount) {
        this.idExpense = idExpense;
        this.idGroup = idGroup;
        this.idUser = idUser;
        this.date = date;
        this.name = name;
        this.amount = amount;
    }

    public int getIdExpense() {
        return idExpense;
    }

    public void setIdExpense(int idExpense) {
        this.idExpense = idExpense;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "IndividualExpenseEntity{" +
                "idExpense=" + idExpense +
                ", idGroup=" + idGroup +
                ", idUser=" + idUser +
                ", date=" + date +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}
