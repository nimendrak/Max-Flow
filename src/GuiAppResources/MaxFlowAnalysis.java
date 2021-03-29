package GuiAppResources;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Name - Nimendra Kariyawasam
 * IIT Student ID - 2019264
 * UOW Student ID - w1761259
 */

public class MaxFlowAnalysis extends JFrame {
    public MaxFlowAnalysis(List<Long> executionTime, List<Integer> numOfVertices) {
        JFrame jFrame = new JFrame();
        jFrame.setTitle("Max Flow");

        JPanel jPanel = new JPanel();

        XYSeries series = new XYSeries(
                "Performance",
                false,
                true);

        // Mark data points on the graph as (x, y)
        for (int i = 0; i < executionTime.size(); i++) {
            series.add(numOfVertices.get(i), executionTime.get(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Performance Analysis",
                "Matrix Size",
                "Execution Time (ms)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Render the graph as a curve
        chart.getXYPlot().setRenderer(new XYSplineRenderer());
        ChartPanel chartPanel = new ChartPanel(chart);

        jPanel.add(chartPanel);
        jFrame.add(jPanel);

        jFrame.setSize(700, 450);
        jFrame.setBackground(Color.WHITE);

        jFrame.setVisible(true);
    }
}
