package com.example.basic;

import java.util.stream.IntStream;

class MyThread extends Thread {
    private final int threadNumber;

    MyThread(final int threadNumber) {
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        IntStream.range(1, 10).forEach(
                i -> System.out.println(String.format("Hello #%d from thread %d", i, threadNumber))
        );

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyRunnable implements Runnable {
    private final int threadNumber;

    MyRunnable(final int threadNumber) {
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        IntStream.range(1, 10).forEach(
                i -> System.out.println(String.format("Hello #%d from thread %d", i, threadNumber))
        );

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Basic {
    public static void main(String[] args) {
        MyThread myThread1 = new MyThread(1);
        myThread1.start();

        MyThread myThread2 = new MyThread(2);
        myThread2.start();

        MyRunnable myRunnable3 = new MyRunnable(3);
        Thread myThread3 = new Thread(myRunnable3);
        myThread3.start();

        MyRunnable myRunnable4 = new MyRunnable(4);
        Thread myThread4 = new Thread(myRunnable4);
        myThread4.start();
    }
}
