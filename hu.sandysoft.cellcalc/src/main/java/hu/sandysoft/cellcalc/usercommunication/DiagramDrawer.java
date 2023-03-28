package hu.sandysoft.cellcalc.usercommunication;
import java.awt.Color;
import java.util.List;
import javax.swing.JFrame;

import hu.sandysoft.cellcalc.info.CellCount;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class DiagramDrawer {

    public void drawBarChart() {
        // Create a dataset containing the values
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < CellCount.sumOfAllCellsPerCellCycles.size(); i++) {
            dataset.addValue(CellCount.sumOfAllCellsPerCellCycles.get(i), "Value", Integer.toString(i));
        }

        // Create a bar chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Cell Count",          // Chart title
                "time",         // X axis label
                "number of cells",         // Y axis label
                dataset,        // Dataset
                PlotOrientation.VERTICAL,   // Orientation
                false,          // Include legend
                false,          // Tooltips
                false           // URLs
        );

        // Customize the chart appearance
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.setRangeGridlinesVisible(true);

        // Display the chart in a frame
        JFrame frame = new JFrame("Cell Count");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new ChartPanel(chart));
        frame.pack();
        frame.setVisible(true);
    }
}

