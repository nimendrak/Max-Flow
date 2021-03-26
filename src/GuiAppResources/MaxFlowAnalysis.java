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
    public MaxFlowAnalysis(List<Integer> timeDifferList, List<Integer> numOfVertices) {
        JFrame jFrame = new JFrame();

        XYSeries series = new XYSeries("MyGraph", false, true);

        for (int i = 0; i < 9; i++) {
            System.out.println("[" + i + "] x -> " + timeDifferList.get(i) + ", y ->" + numOfVertices.get(i));
        }

//        series.add(timeDifferList.get(0), numOfVertices.get(0));
        series.add(timeDifferList.get(1), numOfVertices.get(1));
        series.add(timeDifferList.get(2), numOfVertices.get(2));
        series.add(timeDifferList.get(3), numOfVertices.get(3));
        series.add(timeDifferList.get(4), numOfVertices.get(4));
        series.add(timeDifferList.get(5), numOfVertices.get(5));
        series.add(timeDifferList.get(6), numOfVertices.get(6));
        series.add(timeDifferList.get(7), numOfVertices.get(7));
        series.add(timeDifferList.get(8), numOfVertices.get(8));

//        series.add(10, 10);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Max Flow Analysis",
                "Execution Time",
                "Total Vertex",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        chart.getXYPlot().setRenderer(new XYSplineRenderer());
        ChartPanel chartPanel = new ChartPanel(chart);
        jFrame.add(chartPanel);
        jFrame.setSize(600, 400);
        jFrame.setVisible(true);
    }
}
