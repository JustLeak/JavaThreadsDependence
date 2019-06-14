package sample.model.charts;

import sample.Constants;
import sample.model.handlers.ArrayHandler;
import sample.model.handlers.ThreadHandler;

import java.util.ArrayList;

public class ComplexityChartBuilder {
    private static final int MIN_COMPLEXITY = 100000;
    private static final int MAX_COMPLEXITY = 500000;
    private static final int COMPLEXITY_STEP = 50000;
    private ArrayList<Point> seriesInfo = new ArrayList<>();

    public ArrayList<Point> getSeriesInfo() {
        return seriesInfo;
    }

    public void calculate(int elemCount, int threadCount) {
        double time = 0;
        double startTime;
        ArrayHandler arrHandler = new ArrayHandler(MIN_COMPLEXITY, threadCount, elemCount);
        ThreadHandler threadHandler;

        for (int j = MIN_COMPLEXITY; j <= MAX_COMPLEXITY; j += COMPLEXITY_STEP) {
            arrHandler.setComplexity(j);
            for (int i = 0; i < Constants.ACCURACY; i++) {
                threadHandler = new ThreadHandler(threadCount);
                startTime = System.currentTimeMillis();
                threadHandler.handle(arrHandler);
                time += (System.currentTimeMillis() - startTime);
                arrHandler.restart();
            }
            time /= (Constants.ACCURACY * 1000);
            seriesInfo.add(new Point(time, j));
            time = 0;
        }
    }

    public static class Point {
        private double time;
        private int complexity;

        public Point(double time, int complexity) {
            this.time = time;
            this.complexity = complexity;
        }

        public double getTime() {
            return time;
        }

        public int getComplexity() {
            return complexity;
        }
    }
}
