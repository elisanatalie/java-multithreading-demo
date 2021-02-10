package com.example.library;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Runner {
    private int count;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    void firstThread() throws InterruptedException {
        lock.lock();
        System.out.println("Waiting....");
        condition.await();
        System.out.println("Waken up!");

        try {
            increment();
        } finally {
            lock.unlock();
        }
    }

    void secondThread() throws InterruptedException {
        Thread.sleep(1000);
        lock.lock();
        System.out.println("Press enter!");
        new Scanner(System.in).nextLine();
        condition.signal(); // Don't forget to unlock after signaling, otherwise the thread will hold to the lock forever
        System.out.println("Enter is pressed!");

        try {
            increment();
        } finally {
            lock.unlock();
        }
    }

    void finished() {
        System.out.println("Count is :" + count);
    }

    private void increment() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }
}

public class ReentrantLockExample {
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