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


public class RekruteByRegion extends ApplicationFrame {

    public RekruteByRegion(String title ) {
        super( title );
        setContentPane(createDemoPanel( ));
    }

    private static PieDataset createDataset( ) {
        DefaultPieDataset dataset = new DefaultPieDataset( );
        //geting data from database :
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjava", "root", "");
            String query = "SELECT Region, COUNT(*) as Count FROM poste GROUP BY Region";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                String region = resultSet.getString("Region");
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
    	RekruteByRegion demo = new RekruteByRegion( "Job offers by region :" );
        demo.setSize( 760 , 567 );
        RefineryUtilities.centerFrameOnScreen( demo );
        demo.setVisible( true );
    }
}
