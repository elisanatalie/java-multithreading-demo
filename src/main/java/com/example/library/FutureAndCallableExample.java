package com.example.library;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class FutureAndCallableExample {
    public static void main(String[] args) throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(1);

        final Future<Integer> sleep = executorService.submit(() -> {
            System.out.println("Starting...");

            Random random = new Random();
            int sleepTime = random.nextInt(4000);

            if (sleepTime > 2000) {
                throw new Exception("Sleeping for too long");
            }

            Thread.sleep(sleepTime);

            System.out.println("Awake after sleeping for " + sleepTime + " ms.");

            return sleepTime;
        });

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);

        try {
            System.out.println("Slept for : " + sleep.get() + " ms.");
        } catch (ExecutionException e) {
            System.out.println(e.getCause().getMessage());
        }
    }
}
