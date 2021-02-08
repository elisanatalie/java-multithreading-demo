package com.example.basic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Processor implements Runnable {
    private final int id;

    Processor(final int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Starting : " + id);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Completed : " + id);
    }
}

public class BasicExecutorPool {
    public static void main(String[] args) {
        final ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 5; i++) {
            executorService.submit(new Processor(i));
        }
        executorService.shutdown();

        System.out.println("All task are submitted");

        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All task are completed");
    }
}
