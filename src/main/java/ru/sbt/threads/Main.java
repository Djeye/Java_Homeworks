package ru.sbt.threads;

public class Main {
    public static void main(String[] args) {
        // // Choose your fighter!
        // fixedThreadPoolExample();
        // scalableThreadPoolExample();
    }

    private static void fixedThreadPoolExample() {
        final int NUMBER_OF_TASKS = 7;
        final int NUMBER_OF_THREADS = 5;

        FixedThreadPool fixedThreadPool = new FixedThreadPool(NUMBER_OF_THREADS);

        for (int counter = 1; counter < NUMBER_OF_TASKS + 1; counter++) {
            int resultCounter = counter;

            fixedThreadPool.execute(() -> {
                Long sleepTime = 1000L * resultCounter;
                System.out.printf("Fixed thread %d sleep for %d ms %n", resultCounter, sleepTime);
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("Fixed thread %d wake up after %d ms %n", resultCounter, sleepTime);
            });
        }
        fixedThreadPool.start();
    }

    private static void scalableThreadPoolExample() {
        final int NUMBER_OF_TASKS_WITH_START = 5;
        final int NUMBER_OF_TASKS_CONTINUE = 15;
        final int MIN_NUMBER_OF_THREADS = 5;
        final int MAX_NUMBER_OF_THREADS = 10;

        ScalableThreadPool scalableThreadPool = new ScalableThreadPool(MIN_NUMBER_OF_THREADS, MAX_NUMBER_OF_THREADS);

        for (int counter = 1; counter < NUMBER_OF_TASKS_WITH_START + 1; counter++) {
            int resultCounter = counter;

            scalableThreadPool.execute(() -> {
                Long sleepTime = 1000L * resultCounter;
                System.out.printf("Scalable thread %d sleep for %d ms %n", resultCounter, sleepTime);
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("Scalable thread %d wake up after %d ms %n", resultCounter, sleepTime);
            });
        }

        scalableThreadPool.start();

        for (int counter = NUMBER_OF_TASKS_WITH_START; counter < NUMBER_OF_TASKS_WITH_START +
                NUMBER_OF_TASKS_CONTINUE + 1; counter++) {
            int resultCounter = counter;

            scalableThreadPool.execute(() -> {
                Long sleepTime = 1000L * resultCounter;
                System.out.printf("Scalable thread %d sleep for %d ms %n", resultCounter, sleepTime);
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("Scalable thread %d wake up after %d ms %n", resultCounter, sleepTime);
            });
        }
    }
}
