package local.util;

import javafx.concurrent.Task;

import java.util.concurrent.atomic.AtomicLong;

public class SimpleThreadFactory {
    private static final AtomicLong counter = new AtomicLong();

    public static Thread newDaemonThread(Runnable task, String namePrefix) {
        var result = new Thread(task, namePrefix + '-' + counter.incrementAndGet());
        return markDaemon(result);
    }

    public static Thread newDaemonThread(Runnable task) {
        var result = new Thread(task);
        return markDaemon(result);
    }

    private static Thread markDaemon(Thread task) {
        task.setDaemon(true);
        return task;
    }
}
