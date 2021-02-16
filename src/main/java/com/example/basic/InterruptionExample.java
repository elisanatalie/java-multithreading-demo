package com.example.basic;

import java.util.Random;

public class InterruptionExample {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Start");
        Thread t1 = new Thread(() -> {
            Random random = new Random(100);

            for (int i = 0; i < 10E7; i++) {
//                if (Thread.currentThread().isInterrupted()) {
//                    System.out.println("Interrupted !");
//                    break;
//                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted !");
                    break;
                }
                Math.sin(random.nextDouble());
            }
        });

        t1.start();
        Thread.sleep(500);
        t1.interrupt();
        t1.join();
        System.out.println("Done");
    }
}
