package ru.sbt.threads;

import java.util.Queue;

public class FixedTarget implements Runnable {
    private final Queue<Runnable> taskList;
    private Runnable task;

    public FixedTarget(Queue<Runnable> taskList) {
        this.taskList = taskList;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (taskList) {
                while (taskList.isEmpty())
                    try {
                        taskList.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                if (!taskList.isEmpty())
                    task = taskList.poll();
                else return;
            }
            task.run();
        }
    }
}
