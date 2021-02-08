package com.example.basic;

import java.util.Scanner;

class LoopingThread extends Thread {
    private volatile boolean running = true;

    @Override
    public void run() {
        while (running) {
            System.out.println("Hello");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutDown() {
        running = false;
    }
}

public class BasicVolatileUsage {
    public static void main(String[] args) {
        LoopingThread loopingThread = new LoopingThread();
        loopingThread.start();

        System.out.println("Press enter to stop");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        loopingThread.shutDown();
    }
}
