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

public class RekruteBySector extends ApplicationFrame {

    public RekruteBySector(String title) {
        super(title);
        setContentPane(createDemoPanel());
    }

    private static CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjava", "root", "");
            String query = "SELECT " +
                    "CASE " +
                    "    WHEN `Sector Activity` LIKE '%Informatique%' THEN 'Info' " +
                    "    WHEN `Sector Activity` LIKE '%Travaux%' THEN 'Travaux' " +
                    "    WHEN `Sector Activity` LIKE '%RH%' THEN 'RH' " +
                    "    WHEN `Sector Activity` LIKE '%Tourisme%' THEN 'Tourisme' " +
                    "    WHEN `Sector Activity` LIKE '%Production%' THEN 'Production' " +
                    "    WHEN `Sector Activity` LIKE '%Indus%' THEN 'Indus' " +
                    "    WHEN `Sector Activity` LIKE '%Logistique%' THEN 'Logitsique' " +
                    "    WHEN `Sector Activity` LIKE '%Audit%' THEN 'Audit' " +
                    "    WHEN `Sector Activity` LIKE '%Call%' THEN 'Call centers' " +
                    "    WHEN `Sector Activity` LIKE '%Assistanat%' THEN 'Assisntante' " +
                    "    WHEN `Sector Activity` LIKE '%Elect%' THEN 'Electrique' " +
                    "    WHEN `Sector Activity` LIKE '%Compta%' THEN 'Comptabilite' " +
                    "    WHEN `Sector Activity` LIKE '%Commercial%' THEN 'Comerce' " +
                    "    WHEN `Sector Activity` LIKE '%Gestion%' THEN 'Gestion' " +
                    "    WHEN `Sector Activity` LIKE '%Restauration%' THEN 'Restauration et Hotellerie' " +
                    "    WHEN `Sector Activity` LIKE '%Marketing%' THEN 'Marketing' " +
                    "    WHEN `Sector Activity` LIKE '%Distribution%' THEN 'Distribution' " +
                    "    WHEN `Sector Activity` LIKE '%Avocat%' THEN 'Avocat' " +
                    "    WHEN `Sector Activity` LIKE '%Supply Chain%' THEN 'Suply chain' " +
                    "    WHEN `Sector Activity` LIKE '%Banque%' THEN 'Banque' " +
                    "    WHEN `Sector Activity` LIKE '%Environnement%' THEN 'Envirennoment' " +
                    "    ELSE `Sector Activity` " +
                    "END AS CleanedSector, " +
                    "COUNT(*) AS Count " +
                    "FROM poste " +
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
    	RekruteBySector demo = new RekruteBySector("Job offers by Sectors");
        demo.setSize(760, 567);
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
}
