package com.example.deadlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Runner {
    private Account account1 = new Account();
    private Account account2 = new Account();
    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();

    void firstThread() throws InterruptedException {
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            getBothLocks(lock1, lock2);
            try {
                Account.transfer(account1, account2, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    void secondThread() throws InterruptedException {
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            getBothLocks(lock2, lock1);
            try {
                Account.transfer(account2, account1, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    void finished() {
        System.out.println("Account 1 balance is :" + account1.getBalance());
        System.out.println("Account 2 balance is :" + account2.getBalance());
        System.out.println("Total balance is :" + (account1.getBalance() + account2.getBalance()));
    }

    private void getBothLocks(Lock lock1, Lock lock2) throws InterruptedException {
        while (true) {
            boolean lock1Locked = false;
            boolean lock2Locked = false;
            try {
                lock1Locked = lock1.tryLock();
                lock2Locked = lock2.tryLock();
            } finally {
                if (lock1Locked && lock2Locked) {
                    return;
                }

                if (lock1Locked) {
                    lock1.unlock();
                }
                if (lock2Locked) {
                    lock2.unlock();
                }
            }
            Thread.sleep(1);
        }
    }
}

public class DeadlockExample {
    public static void main(String[] args) throws InterruptedException {
        Runner runner = new Runner();

        Thread t1 = new Thread(() -> {
            try {
                runner.firstThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                runner.secondThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        runner.finished();
    }
}