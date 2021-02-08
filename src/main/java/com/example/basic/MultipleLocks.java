package com.example.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class MultipleLocks {
    public static void main(String[] args) {
        Worker worker = new Worker();
        worker.main();
    }
}

class Worker {
    private final Random random = new Random();

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    private final List<Integer> list1 = new ArrayList<>();
    private final List<Integer> list2 = new ArrayList<>();

    public void main() {
        System.out.println("Start...");

        final long start = System.currentTimeMillis();

        Thread t1 = new Thread(() -> process());
        Thread t2 = new Thread(() -> process());

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final long end = System.currentTimeMillis();

        System.out.println("Done...");
        System.out.println("Time taken: " + (end - start));
        System.out.println("List1 size " + list1.size() + ". List2 size " + list2.size());
    }

    private void process() {
        IntStream.range(0, 1000).forEach(i -> {
            stageOne();
            stageTwo();
        });
    }

    private void stageOne() {
        synchronized (lock1) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            list1.add(random.nextInt(100));
        }
    }

    private void stageTwo() {
        synchronized (lock2) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            list2.add(random.nextInt(100));
        }
    }
}
