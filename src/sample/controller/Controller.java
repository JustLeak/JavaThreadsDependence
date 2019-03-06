package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.model.charts.ComplexityChartBuilder;
import sample.model.charts.ElementsChartBuilder;
import sample.model.charts.ThreadsChartBuilder;
import sample.model.handlers.ArrayHandler;
import sample.model.handlers.ThreadHandler;

public class Controller {

    @FXML
    private TextField elemCountTextField;

    @FXML
    private TextField treadCountTextField;

    @FXML
    private TextField complexityTextField;

    @FXML
    private TextField oneThreadTimeTextField;

    @FXML
    private TextField multithreadingTimeTextField;

    @FXML
    private LineChart<?, ?> complexityChart;

    @FXML
    private LineChart<?, ?> elemCountChart;

    @FXML
    private LineChart<?, ?> threadsChart;

    @FXML
    private Button calculateChart1Button;

    @FXML
    private Button calculateChart2Button;

    @FXML
    private Button calculateChart3Button;

    @FXML
    private Button startButton;

    @FXML
    private void initialize() {


        startButton.setOnAction(event -> {
            String elemCount = elemCountTextField.getText().trim();
            String complexity = complexityTextField.getText().trim();
            String threadCount = treadCountTextField.getText().trim();

            if (isValid(elemCount, complexity, threadCount)) {
                ArrayHandler arrHandler = new ArrayHandler(Integer.valueOf(complexity), 1, Integer.valueOf(elemCount));
                double startTime;
                ThreadHandler threadHandler;

                startTime = System.currentTimeMillis();
                arrHandler.handle();
                oneThreadTimeTextField.setText(String.valueOf((System.currentTimeMillis() - startTime) / 1000));

                arrHandler.restart();
                arrHandler.setThreadsCount(Integer.valueOf(threadCount));

                threadHandler = new ThreadHandler(arrHandler.getThreadsCount());
                startTime = System.currentTimeMillis();
                threadHandler.handle(arrHandler);
                multithreadingTimeTextField.setText(String.valueOf((System.currentTimeMillis() - startTime) / 1000));
            }
        });

        calculateChart1Button.setOnAction(event -> {
            String elemCount = elemCountTextField.getText().trim();
            String complexity = complexityTextField.getText().trim();
            String threadCount = treadCountTextField.getText().trim();

            if (isValid(elemCount, complexity, threadCount)) {
                ComplexityChartBuilder complexityChartBuilder = new ComplexityChartBuilder();
                complexityChartBuilder.calculate(Integer.valueOf(elemCount), Integer.valueOf(threadCount));
                XYChart.Series series = new XYChart.Series();

                for (ComplexityChartBuilder.Point point : complexityChartBuilder.getSeriesInfo()
                        ) {
                    series.getData().add(new XYChart.Data(String.valueOf((double) point.getComplexity() / 1000000) + "ml", point.getTime()));
                }
                complexityChart.getData().add(series);
            }
        });

        calculateChart2Button.setOnAction(event -> {
            String elemCount = elemCountTextField.getText().trim();
            String complexity = complexityTextField.getText().trim();
            String threadCount = treadCountTextField.getText().trim();

            if (isValid(elemCount, complexity, threadCount)) {
                ElementsChartBuilder chartBuilder = new ElementsChartBuilder();
                chartBuilder.calculate(Integer.valueOf(complexity), Integer.valueOf(threadCount));
                XYChart.Series series = new XYChart.Series();

                for (ElementsChartBuilder.Point point : chartBuilder.getSeriesInfo()
                        ) {
                    series.getData().add(new XYChart.Data(String.valueOf(point.getElemCount()), point.getTime()));
                }
                elemCountChart.getData().add(series);
            }
        });

        calculateChart3Button.setOnAction(event -> {
            String elemCount = elemCountTextField.getText().trim();
            String complexity = complexityTextField.getText().trim();
            String threadCount = treadCountTextField.getText().trim();

            if (isValid(elemCount, complexity, threadCount)) {
                ThreadsChartBuilder chartBuilder = new ThreadsChartBuilder();
                chartBuilder.calculate(Integer.valueOf(complexity), Integer.valueOf(elemCount));
                XYChart.Series series = new XYChart.Series();

                for (ThreadsChartBuilder.Point point : chartBuilder.getSeriesInfo()
                        ) {
                    series.getData().add(new XYChart.Data(String.valueOf(point.getThreadsCount()), point.getTime()));
                }
                threadsChart.getData().add(series);
            }
        });
    }

    private boolean isValid(String elemCount, String complexity, String threadCount) {
        if (!elemCount.equals("") && !complexity.equals("") && !threadCount.equals(""))
            return true;
        return false;
    }

}
