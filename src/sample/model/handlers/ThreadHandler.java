package sample.model.handlers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadHandler {
    private ExecutorService service;
    private int piece;
    private int remainder;

    public ThreadHandler(int maxThreadPool) {
        this.service = Executors.newFixedThreadPool(maxThreadPool);
    }

    private void dataDistribution(int elemCount, int threadsCount) {
        piece = elemCount / threadsCount;
        if (elemCount / piece != 0) {
            remainder = elemCount % piece;
        } else remainder = 0;
    }

    public void handle(ArrayHandler arrHandler) {
        int threads = arrHandler.getThreadsCount();
        dataDistribution(arrHandler.getElemCount(), arrHandler.getThreadsCount());

        if (remainder != 0) {
            for (int i = 0; i < threads - 1; i++) {
                service.submit(() -> arrHandler.handle(piece));
            }
            service.submit(() -> arrHandler.handle(remainder + piece));
        } else {
            for (int i = 0; i < threads; i++) {
                service.submit(() -> arrHandler.handle(piece));
            }
        }
        service.shutdown();
        try {
            service.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

