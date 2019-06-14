package sample.model.charts;

import sample.Constants;
import sample.model.handlers.ArrayHandler;
import sample.model.handlers.ThreadHandler;

import java.util.ArrayList;

public class ThreadsChartBuilder {
    private static final int MIN_THREADS = 1;
    private static final int MAX_THREADS = 10;
    private static final int STEP = 1;
    private ArrayList<ThreadsChartBuilder.Point> seriesInfo = new ArrayList<>();

    public ArrayList<ThreadsChartBuilder.Point> getSeriesInfo() {
        return seriesInfo;
    }

    public void calculate(int complexity, int elemCount) {
        double time = 0;
        double startTime;
        ArrayHandler arrHandler = new ArrayHandler(complexity, MIN_THREADS, elemCount);
        ThreadHandler threadHandler;

        for (int j = MIN_THREADS; j <= MAX_THREADS; j += STEP) {
            arrHandler.setThreadsCount(j);
            for (int i = 0; i < Constants.ACCURACY; i++) {
                threadHandler = new ThreadHandler(j);
                startTime = System.currentTimeMillis();
                threadHandler.handle(arrHandler);
                time += (System.currentTimeMillis() - startTime);
                arrHandler.restart();
            }
            time /= (Constants.ACCURACY * 1000);
            seriesInfo.add(new ThreadsChartBuilder.Point(time, j));
            time = 0;
        }
    }

    public static class Point {
        private double time;
        private int threadsCount;

        public Point(double time, int elemCount) {
            this.time = time;
            this.threadsCount = elemCount;
        }

        public double getTime() {
            return time;
        }

        public int getThreadsCount() {
            return threadsCount;
        }
    }
}
