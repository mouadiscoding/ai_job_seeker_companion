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

public class EmploiBySector extends ApplicationFrame {

    public EmploiBySector(String title) {
        super(title);
        setContentPane(createDemoPanel());
    }

    private static CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjava", "root", "");
            String query = "SELECT " +
                    "CASE " +
                    "WHEN `Sector Activity` LIKE '%Agroalimentaire%' THEN 'Agroalimentaire' " +
                    "WHEN `Sector Activity` LIKE '%Distribution%' THEN 'Distribution' " +
                    "WHEN `Sector Activity` LIKE '%Santé%' THEN 'Santé' " +
                    "WHEN `Sector Activity` LIKE '%vente%' THEN 'vente' " +
                    "WHEN `Sector Activity` LIKE '%Édition%' THEN 'Édition' " +
                    "WHEN `Sector Activity` LIKE '%imprimerie%' THEN 'imprimerie' " +
                    "WHEN `Sector Activity` LIKE '%Éducation%' THEN 'Éducation' " +
                    "WHEN `Sector Activity` LIKE '%Intérim%' THEN 'Intérim' " +
                    "WHEN `Sector Activity` LIKE '%Administration%' THEN 'Administration' " +
                    "WHEN `Sector Activity` LIKE '%Industrie%' THEN 'Industrie' " +
                    "WHEN `Sector Activity` LIKE '%export%' THEN 'export' " +
                    "WHEN `Sector Activity` LIKE '%production%' THEN 'production' " +
                    "WHEN `Sector Activity` LIKE '%logistique%' THEN 'logistique' " +
                    "WHEN `Sector Activity` LIKE '%Services autres%' THEN 'Services autres' " +
                    "WHEN `Sector Activity` LIKE '%Informatique%' THEN 'Informatique' " +
                    "WHEN `Sector Activity` LIKE '%Automobile%' THEN 'Automobile' " +
                    "WHEN `Sector Activity` LIKE '%Marketing%' THEN 'Marketing' " +
                    "WHEN `Sector Activity` LIKE '%Ingénierie%' THEN 'Ingénierie' " +
                    "WHEN `Sector Activity` LIKE '%Conseil%' THEN 'Conseil' "+
                    "    ELSE `Sector Activity` " +
                    "END AS CleanedSector, " +
                    "COUNT(*) AS Count " +
                    "FROM `poste emploima` " +
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
                "Job offers by Sectors", 
                "Job offers", 
                "Sectors",
                dataset,
                org.jfree.chart.plot.PlotOrientation.HORIZONTAL,
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
    	EmploiBySector demo = new EmploiBySector("Job offers by Sectors");
        demo.setSize(760, 567);
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
}
