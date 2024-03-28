package projetJava;

import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.sql.*;

public class MjobByDate extends ApplicationFrame {

	public MjobByDate(String title) {
        super(title);
        setContentPane(createDemoPanel());
    }

    private static CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Getting data from the database:
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjava", "root", "");
            String query = "SELECT `Date Publication` AS JobDate, MIN(id) AS AppearanceOrder, COUNT(*) AS Count FROM `poste m-job` GROUP BY `Date Publication` ORDER BY AppearanceOrder;";;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String jobDate = resultSet.getString("JobDate");
                int count = resultSet.getInt("Count");
                dataset.addValue(count, "Job offers date", jobDate);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dataset;
    }

    private static JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createLineChart(
                "Job offers by Date",    // chart title
                "Date",                  // x-axis label
                "Job offers",            // y-axis label
                dataset ,
                PlotOrientation.VERTICAL,
                true,
                true,
                true
        );


        return chart;
    }

    public static JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        return new ChartPanel(chart);
    }

    public static void main(String[] args) {
    	MjobByDate demo = new MjobByDate("Job offers by Date");
        demo.setSize(760, 567);
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
}
