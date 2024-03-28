package projetJava;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartPanel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

public class RekruteByDate extends ApplicationFrame {

	public RekruteByDate(String title) {
        super(title);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	dispose();
                showStatRekrute();
            }
        });

        setContentPane(createDemoPanel());
    }

    private static CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjava", "root", "");
            String query = "SELECT `Date Publication` AS JobDate, COUNT(*) AS Count FROM poste GROUP BY JobDate";
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
                "Job offers by Date", 
                "Date",
                "Job offers",
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

    private void showStatRekrute() {
        SwingUtilities.invokeLater(() -> {
            StatRekrute stat = new StatRekrute();
            stat.setUndecorated(true);
            stat.setLocationRelativeTo(null);
            stat.setVisible(true);
        });
    }
    
    public static void main(String[] args) {
        RekruteByDate demo = new RekruteByDate("Job offers by Date");
        demo.setSize(760, 567);
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
}

