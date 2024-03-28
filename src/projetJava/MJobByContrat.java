

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


	public class MJobByContrat extends ApplicationFrame {

	    public MJobByContrat(String title ) {
	        super( title );
	        setContentPane(createDemoPanel( ));
	    }

	    private static PieDataset createDataset( ) {
	        DefaultPieDataset dataset = new DefaultPieDataset( );
	        //geting data from database :
	        try{
	            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjava", "root", "");
	            String query = "SELECT "+
	            		"CASE"+
	            		"    WHEN `Type Contrat` LIKE '%CDI%' THEN 'CDI' " +
	            		"    WHEN `Type Contrat` LIKE '%CDD%' THEN 'CDD' " +
	            		"    WHEN `Type Contrat` LIKE '%Free Lance%' THEN 'Free Lance' " +
	            		"    WHEN `Type Contrat` LIKE '%Intérim%' THEN 'Intérim' " +
	            		"    WHEN `Type Contrat` LIKE '%Lettre%' THEN 'Lettre Engagement' " +
	            		"    ELSE City " +
	                    "END AS Cleanedcontrat, "
	            		+ " COUNT(*) as Count "
	            		+ "FROM `poste m-job` "
	            		+ "GROUP BY `Cleanedcontrat`";
	            PreparedStatement statement = connection.prepareStatement(query);
	            ResultSet resultSet = statement.executeQuery();

	            while (resultSet.next()){
	                String region = resultSet.getString("Cleanedcontrat");
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
	                "Job offers by Contact",   // chart title
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



	    	MJobByContrat demo = new MJobByContrat( "Job offers by contracts :" );
	        demo.setSize( 760 , 567 );
	        RefineryUtilities.centerFrameOnScreen( demo );
	        demo.setVisible( true );
	    }
	}