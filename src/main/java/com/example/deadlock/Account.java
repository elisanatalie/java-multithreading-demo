package com.example.deadlock;

public class Account {
    private int balance = 20000;

    public void withdraw(int amount) {
        balance = balance - amount;
    }

    public void deposit(int amount) {
        balance = balance + amount;
    }

    public static void transfer(Account acc1, Account acc2, int amount) {
        acc1.withdraw(amount);
        acc2.deposit(amount);
    }

    public int getBalance() {
        return balance;
    }
}
