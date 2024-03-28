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


public class EmploiByRegion extends ApplicationFrame {

    public EmploiByRegion(String title ) {
        super( title );
        setContentPane(createDemoPanel( ));
    }

    private static PieDataset createDataset( ) {
        DefaultPieDataset dataset = new DefaultPieDataset( );
        //geting data from database :
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjava", "root", "");
            String query = "SELECT "+
            		"CASE " +
                    "    WHEN Region LIKE '%Tanger%' THEN 'Tanger' " +
                    "    WHEN Region LIKE '%Rabat%' THEN 'Rabat' " +
                    "    WHEN Region LIKE '%Salé%' THEN 'Salé' " +
                    "    WHEN Region LIKE '%Kenitra%' THEN 'Kenitra' " +
                    "    WHEN Region LIKE '%Temara%' THEN 'Temara' " +
                    "    WHEN Region LIKE '%Mohammedia%' THEN 'Mohammedia' " +
                    "    WHEN Region LIKE '%Marrakech%' THEN 'Marrakech' " +
                    "    WHEN Region LIKE '%Bouskoura%' THEN 'Bouskoura' " +
                    "    WHEN Region LIKE '%Agadir%' THEN 'Agadir' " +
                    "    WHEN Region LIKE '%Meknes%' THEN 'Meknes' " +
                    "    WHEN Region LIKE '%Fès%' THEN 'Fes' " +
                    "    WHEN Region LIKE '%Casdablanca%' THEN 'Casablanca' " +
                    "    WHEN Region LIKE '%Québec%' THEN 'Quebec' " +
                    "    WHEN Region LIKE '%LAC%' THEN 'Sagenay lac saint jean' " +
                    "    WHEN Region LIKE '%Drummondville%' THEN 'Drummondville' " +
                    "    WHEN Region LIKE '%Différente Ville De France%' THEN 'France Autre' " +
                    "    WHEN Region LIKE '%Différentes Villes En France%' THEN 'France Autre' " +
                    "    ELSE Region " +
                    "END AS Cleanedregion, " +
            		"COUNT(*) as Count "+
                    "FROM `poste emploima` GROUP BY Cleanedregion";;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                String region = resultSet.getString("Cleanedregion");
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
    	EmploiByRegion demo = new EmploiByRegion( "Job offers by region :" );
        demo.setSize( 760 , 567 );
        RefineryUtilities.centerFrameOnScreen( demo );
        demo.setVisible( true );
    }
}
