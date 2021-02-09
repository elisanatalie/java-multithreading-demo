package com.example.basic;

import java.util.LinkedList;
import java.util.Random;

class ProcessorWaitNotifyUsage {
    private static final int LIMIT = 10;
    private final LinkedList<Integer> list = new LinkedList<>();
    private final Object lock = new Object();

    void produce() throws InterruptedException {
        int value = 0;
        while (true) {
            synchronized (lock) {

                while (list.size() == LIMIT) {
                    lock.wait();
                }
                list.add(value++);
                lock.notify();
            }
        }
    }

    void consume() throws InterruptedException {
        Random random = new Random();
        while (true) {
            synchronized (lock) {
                while (list.size() <= 0) {
                    lock.wait();
                }
                System.out.print("List size is " + list.size());
                final Integer value = list.removeFirst();
                System.out.println(". Value  is " + value);
                lock.notify();
            }
            Thread.sleep(random.nextInt(1000));
        }
    }
}

public class WaitAndNotifyUsage {
    public static void main(String[] args) throws InterruptedException {
        ProcessorWaitNotifyUsage processor = new ProcessorWaitNotifyUsage();

        Thread t1 = new Thread(() -> {
            try {
                processor.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                processor.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
