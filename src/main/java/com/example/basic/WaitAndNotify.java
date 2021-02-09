package com.example.basic;

import java.util.Scanner;

class ProcessorWaitNotify {
    void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Starting producer...");
            wait();
            System.out.println("Finish producer.");
        }
    }

    void consume() throws InterruptedException {
        Thread.sleep(2000);

        synchronized (this) {
            System.out.println("Press enter to continue");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            notify();
            Thread.sleep(5000);
        }
    }
}

public class WaitAndNotify {
    public static void main(String[] args) throws InterruptedException {
        ProcessorWaitNotify processor = new ProcessorWaitNotify();

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
