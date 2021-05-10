package local;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@Slf4j
public class SlideshowService extends ScheduledService<Integer> {
    private AtomicInteger limit = new AtomicInteger();
    private AtomicInteger index = new AtomicInteger();

    @Override
    public boolean cancel() {
        log.info("Cancelling");
        return super.cancel();
    }

    @Override
    public void restart() {
        log.info("Restarting");
        super.restart();
    }

    @Override
    public void start() {
        log.info("Starting; {}, {}", index.get(), limit.get());
        super.start();
    }

    @Override
    protected Task<Integer> createTask() {
        return new Task<>() {
            protected Integer call() {
                if (index.incrementAndGet() % limit.get() == 0) {
                        index.set(0);
                }
                updateValue(index.get());
                return index.get();
            }
        };
    }

    public void updateIndex(int newIdx) {
        index.lazySet(newIdx);
    }

    public void updateLimit(int newLimit) {
        limit.lazySet(newLimit);
    }
}
