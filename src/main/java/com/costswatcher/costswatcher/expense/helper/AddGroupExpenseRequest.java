package com.costswatcher.costswatcher.expense.helper;

public class AddGroupExpenseRequest {
    private String expenseName;
    private String username1;
    private String username2;
    private int amount1;
    private int amount2;

    public AddGroupExpenseRequest() {
    }

    public AddGroupExpenseRequest(String expenseName, String username1, String username2, int amount1, int amount2) {
        this.expenseName = expenseName;
        this.username1 = username1;
        this.username2 = username2;
        this.amount1 = amount1;
        this.amount2 = amount2;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }

    public int getAmount1() {
        return amount1;
    }

    public void setAmount1(int amount1) {
        this.amount1 = amount1;
    }

    public int getAmount2() {
        return amount2;
    }

    public void setAmount2(int amount2) {
        this.amount2 = amount2;
    }

    @Override
    public String toString() {
        return "AddGroupExpenseRequest{" +
                "expenseName='" + expenseName + '\'' +
                ", username1='" + username1 + '\'' +
                ", username2='" + username2 + '\'' +
                ", amount1=" + amount1 +
                ", amount2=" + amount2 +
                '}';
    }
}
