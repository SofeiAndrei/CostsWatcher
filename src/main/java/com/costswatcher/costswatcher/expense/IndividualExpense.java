package com.costswatcher.costswatcher.expense;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table
public class IndividualExpense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idExpense;
    private int amount;
    private int idGroup;
    private int idUser;
    private Timestamp date;
    private String name;

    public IndividualExpense() {
    }

    public IndividualExpense(int idExpense, int amount, int idGroup, int idUser, Timestamp date, String name) {
        this.idExpense = idExpense;
        this.amount = amount;
        this.idGroup = idGroup;
        this.idUser = idUser;
        this.date = date;
        this.name = name;
    }

    public int getIdExpense() {
        return idExpense;
    }

    public void setIdExpense(int idExpense) {
        this.idExpense = idExpense;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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

    @Override
    public String toString() {
        return "IndividualExpense{" +
                "idExpense=" + idExpense +
                ", amount=" + amount +
                ", idGroup=" + idGroup +
                ", idUser=" + idUser +
                ", date=" + date +
                ", name='" + name + '\'' +
                '}';
    }
}
