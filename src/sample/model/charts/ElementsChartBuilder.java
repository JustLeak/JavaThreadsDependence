package sample.model.charts;

import sample.Constants;
import sample.model.handlers.ArrayHandler;
import sample.model.handlers.ThreadHandler;

import java.util.ArrayList;

public class ElementsChartBuilder {
    private static final int MIN_ELEMENTS = 100;
    private static final int MAX_ELEMENTS = 1500;
    private static final int STEP = 200;
    private ArrayList<ElementsChartBuilder.Point> seriesInfo = new ArrayList<>();

    public ArrayList<ElementsChartBuilder.Point> getSeriesInfo() {
        return seriesInfo;
    }

    public void calculate(int complexity, int threadCount) {
        double time = 0;
        double startTime;
        ArrayHandler arrHandler = new ArrayHandler(complexity, threadCount, MIN_ELEMENTS);
        ThreadHandler threadHandler;

        for (int j = MIN_ELEMENTS; j <= MAX_ELEMENTS; j += STEP) {
            arrHandler.refreshArray(j);
            for (int i = 0; i < Constants.ACCURACY; i++) {
                threadHandler = new ThreadHandler(threadCount);
                startTime = System.currentTimeMillis();
                threadHandler.handle(arrHandler);
                time += (System.currentTimeMillis() - startTime);
                arrHandler.restart();
            }
            time /= (Constants.ACCURACY * 1000);
            seriesInfo.add(new ElementsChartBuilder.Point(time, j));
            time = 0;
        }
    }

    public static class Point {
        private double time;
        private int elemCount;

        public Point(double time, int elemCount) {
            this.time = time;
            this.elemCount = elemCount;
        }

        public double getTime() {
            return time;
        }

        public int getElemCount() {
            return elemCount;
        }
    }
}
