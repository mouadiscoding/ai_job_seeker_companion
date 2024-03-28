package projetJava;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.sql.*;


public class MjobBYRegion extends ApplicationFrame {

    public MjobBYRegion(String title ) {
        super( title );
        setContentPane(createDemoPanel( ));
    }

    private static PieDataset createDataset( ) {
        DefaultPieDataset dataset = new DefaultPieDataset( );
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjava", "root", "");
            String query = "SELECT "+
            		"CASE " +
                    "    WHEN City LIKE '%Tanger%' THEN 'Tanger' " +
                    "    WHEN City LIKE '%Rabat%' THEN 'Rabat' " +
                    "    WHEN City LIKE '%Salé%' THEN 'Salé' " +
                    "    WHEN City LIKE '%Kenitra%' THEN 'Kenitra' " +
                    "    WHEN City LIKE '%Temara%' THEN 'Temara' " +
                    "    WHEN City LIKE '%Mohammedia%' THEN 'Mohammedia' " +
                    "    WHEN City LIKE '%Marrakech%' THEN 'Marrakech' " +
                    "    WHEN City LIKE '%Agadir%' THEN 'Agadir' " +
                    "    WHEN City LIKE '%Meknes%' THEN 'Meknes' " +
                    "    WHEN City LIKE '%Fes%' THEN 'Fes' " +
                    "    ELSE City " +
                    "END AS Cleanedcity, " +
            		"COUNT(*) as Count "+
                    "FROM `poste m-job` GROUP BY Cleanedcity";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                String region = resultSet.getString("Cleanedcity");
                int count = resultSet.getInt("Count");
                dataset.setValue(region,count);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dataset;
    }

    private static JFreeChart createChart( PieDataset dataset ) {
        JFreeChart chart = ChartFactory.createPieChart(
                "Job offers by region",   // chart title
                dataset,          // data
                true,             // include legend
                true,
                false);

        return chart;
    }

    public static JPanel createDemoPanel( ) {
        JFreeChart chart = createChart(createDataset( ) );
        return new ChartPanel( chart );
    }

    public static void main( String[ ] args ) {



    	MjobBYRegion demo = new MjobBYRegion( "Job offers by region :" );
        demo.setSize( 760 , 567 );
        RefineryUtilities.centerFrameOnScreen( demo );
        demo.setVisible( true );
    }
}
