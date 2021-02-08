package com.example.basic;

import java.util.stream.IntStream;

public class BasicSynchronized {
    private int count;

    public static void main(String[] args) {
        BasicSynchronized app = new BasicSynchronized();
        app.doTask();
    }

    public void doTask() {
        Thread thread1 = new Thread(() -> IntStream.range(0, 10000).forEach(i -> increment()));
        Thread thread2 = new Thread(() -> IntStream.range(0, 10000).forEach(i -> increment()));

        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Count is :" + count);
    }

    public synchronized void increment() {
        count++;
    }
}
