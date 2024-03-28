package projetJava;

import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.sql.*;

public class MjobBySector extends ApplicationFrame {

    public MjobBySector(String title) {
        super(title);
        setContentPane(createDemoPanel());
    }

    private static CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Getting data from the database:
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjava", "root", "");
            String query = "SELECT " +
                    "CASE " +
                    "    WHEN `Sector Activity` LIKE '%Tourisme%' THEN 'Tourisme' " +
                    "	WHEN `Sector Activity` LIKE '%Logistique%' THEN 'Logistique'" +
                    "	WHEN `Sector Activity` LIKE '%Assurance%' THEN 'Assurance'" +
                    "	WHEN `Sector Activity` LIKE '%Santé%' THEN 'Santé'" +
                    "	WHEN `Sector Activity` LIKE '%Télécommunications%' THEN 'Télécommunications'" +
                    "	WHEN `Sector Activity` LIKE '%Consulting%' THEN 'Consulting'" +
                    "	WHEN `Sector Activity` LIKE '%Comptabilité%' THEN 'Comptabilité'" +
                    "	WHEN `Sector Activity` LIKE '%Education%' THEN 'Education'" +
                    "	WHEN `Sector Activity` LIKE '%Informatique%' THEN 'Informatique'" +
                    "	WHEN `Sector Activity` LIKE '%Grande distribution%' THEN 'Grande distribution'" +
                    "	WHEN `Sector Activity` LIKE '%Sports%' THEN 'Sports'" +
                    "	WHEN `Sector Activity` LIKE '%Transport%' THEN 'Transport'" +
                    "	WHEN `Sector Activity` LIKE '%Assurance%' THEN 'Assurance'" +
                    "    ELSE `Sector Activity` " +
                    "END AS CleanedSector, " +
                    "COUNT(*) AS Count " +
                    "FROM `poste m-job` " +
                    "GROUP BY CleanedSector";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String sector = resultSet.getString("CleanedSector");
                int count = resultSet.getInt("Count");
                dataset.addValue(count, "Job offers", sector);
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
        JFreeChart chart = ChartFactory.createBarChart(
                "Job offers by Sectors",   // chart title
                "Job offers",              // x-axis label
                "Sectors",                 // y-axis label
                dataset,
                org.jfree.chart.plot.PlotOrientation.HORIZONTAL, // Set plot orientation to horizontal
                true,
                true,
                false);

        return chart;
    }

    public static JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        return new ChartPanel(chart);
    }

    public static void main(String[] args) {
    	MjobBySector demo = new MjobBySector("Job offers by Sectors");
        demo.setSize(760, 567);
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
}
