package ru.sbt.threads;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class FixedThreadPool implements ThreadPool {
    private final Queue<Runnable> taskList = new LinkedList<>();
    private final Collection<Thread> threadList = new ArrayList<>();

    public FixedThreadPool(int numberOfThreads) {
        createThreadPool(numberOfThreads);
    }

    private void createThreadPool(int numThreads) {
        if (numThreads <= 0) return;

        threadList.clear();
        while (numThreads > 0) {
            threadList.add(new Thread(new FixedTarget(taskList)));
            numThreads--;
        }
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
}
