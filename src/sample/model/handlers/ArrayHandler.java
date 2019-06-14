package sample.model.handlers;


import sample.Constants;

public class ArrayHandler {
    private double[] arr;
    private double[] newArr;
    private int complexity;
    private int threadsCount;
    private int elemCount;
    private int start;

    public ArrayHandler(int complexity, int threadsCount, int elemCount) {
        this.complexity = complexity;
        this.threadsCount = threadsCount;
        this.elemCount = elemCount;
        refreshArray(elemCount);
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    public int getElemCount() {
        return elemCount;
    }

    public int getThreadsCount() {
        return threadsCount;
    }

    public void setThreadsCount(int threadsCount) {
        this.threadsCount = threadsCount;
    }

    private synchronized int sync(int piece) {
        int ret = start;
        start += piece;
        return ret;
    }

    public void handle(int piece) {
        int start = sync(piece);

        for (int i = start; i < start + piece; i++) {
            for (int j = 0; j < complexity; j++)
                newArr[i] += Math.pow(arr[i], 1.789);
        }
    }

    public void handle(){
        for (int i = 0; i < elemCount; i++) {
            for (int j = 0; j < complexity; j++)
                newArr[i] += Math.pow(arr[i], 1.789);
        }
    }

    public void refreshArray(int elemCount) {
        this.elemCount = elemCount;
        arr = new double[elemCount];
        newArr = new double[elemCount];
        start = 0;
        for (int i = 0; i < elemCount; i++) {
            arr[i] = Constants.MIN_VALUE + (Math.random() * Constants.MAX_VALUE - Constants.MIN_VALUE);
        }
    }

    public void restart() {
        start = 0;
        newArr = new double[elemCount];
    }
}
