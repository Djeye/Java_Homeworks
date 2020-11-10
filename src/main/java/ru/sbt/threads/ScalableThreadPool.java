package ru.sbt.threads;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class ScalableThreadPool implements ThreadPool {
    private final Queue<Runnable> taskList = new LinkedList<>();
    private final Collection<Thread> threadList = new ArrayList<>();
    private final int minNumThreads, maxNumThreads;
    private volatile int countThreads, activeThreads, bottomLimit;

    public ScalableThreadPool(int minNumThreads, int maxNumThreads) {
        this.minNumThreads = minNumThreads;
        this.maxNumThreads = maxNumThreads;
        createThreadPool(this.minNumThreads);
    }

    private void createThreadPool(int numThreads) {
        if (numThreads <= 0) return;
        bottomLimit = minNumThreads;
        countThreads = 0;
        activeThreads = 0;
        threadList.clear();
        while (numThreads > 0) {
            threadList.add(new Thread(new ScalableTarget()));
            numThreads--;
        }
    }

    private void scaleThreadPool() {
        if (activeThreads == countThreads && countThreads < maxNumThreads)
            new Thread(new ScalableTarget()).start();
    }

    @Override
    public void start() {
        threadList.forEach(Thread::start);
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (taskList) {
            taskList.add(runnable);
            taskList.notify();
        }
    }

    private synchronized void changeCount(boolean increment) {
        if (increment)
            countThreads++;
        else
            countThreads--;
    }

    private synchronized void changeWorkers(boolean increment) {
        if (increment) {
            scaleThreadPool();
            activeThreads++;
        } else
            activeThreads--;
    }

    private class ScalableTarget implements Runnable {
        private Runnable task;

        @Override
        public void run() {
            changeCount(true);
            while (true) {
                synchronized (taskList) {
                    while (taskList.isEmpty() && countThreads <= bottomLimit)
                        try {
                            taskList.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    task = taskList.poll();
                }
                if (task == null) {
                    changeCount(false);
                    return;
                }

                changeWorkers(true);
                task.run();
                changeWorkers(false);
            }
        }
    }
}
