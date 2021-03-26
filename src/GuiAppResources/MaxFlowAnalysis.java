package GuiAppResources;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.List;

public class MaxFlowAnalysis extends JFrame {
    public MaxFlowAnalysis(List<Long> timeDifferList, List<Integer> numOfVertices) {
        JFrame jFrame = new JFrame();

        XYSeries series = new XYSeries("MyGraph", false, true);

        for (int i = 0; i < timeDifferList.size(); i++) {
            series.add((timeDifferList.get(i)), numOfVertices.get(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Max Flow Analysis",
                "Execution Time (ms)",
                "Total Vertex",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Due to low amount of data graphs has been set to linear
//        chart.getXYPlot().setRenderer(new XYSplineRenderer());
        ChartPanel chartPanel = new ChartPanel(chart);
        jFrame.add(chartPanel);
        jFrame.setSize(600, 400);
        jFrame.setVisible(true);
    }
}
