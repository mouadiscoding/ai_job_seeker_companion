package projetJava;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicScrollBarUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


public class dashboard3 extends JFrame {

	private JPanel contentPane;
	private JLabel errorLabel;
	private JTable table;
	private DefaultTableModel tableModel;
	private static final String DB_URL = "jdbc:mysql://localhost:3306/projetjava";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";
	private static final int MAX_ROW_HEIGHT = 150;
	private JTextField searchField;
	private JComboBox<String> columnComboBox;
	int xx,xy;
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					dashboard3 frame = new dashboard3();
					frame.setUndecorated(true);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public dashboard3() {
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(0, 0, 346, 600);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Job Finder");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setForeground(new Color(0x739bcb));
		lblNewLabel.setBounds(134, 305, 100, 27);
		panel.add(lblNewLabel);
		
		JLabel home = new JLabel("<html><u>Home</u></html>");
		home.setHorizontalAlignment(SwingConstants.CENTER);
		home.setFont(new Font("Tahoma", Font.PLAIN, 14));
		home.setForeground(new Color(0x739bcb));
		home.setBounds(100, 400, 160, 27);
		panel.add(home);
		
		JLabel Rekrutechoise = new JLabel("<html><u>Rekrute Data</u></html>");
		Rekrutechoise.setHorizontalAlignment(SwingConstants.CENTER);
		Rekrutechoise.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Rekrutechoise.setForeground(new Color(0x739bcb));
		Rekrutechoise.setBounds(100, 430, 160, 27);
		panel.add(Rekrutechoise);
		
		JLabel logOut = new JLabel("<html><u>Log Out</u></html>");
		logOut.setHorizontalAlignment(SwingConstants.CENTER);
		logOut.setFont(new Font("Tahoma", Font.PLAIN, 14));
		logOut.setForeground(new Color(0x739bcb));
		logOut.setBounds(100, 460, 160, 27);
		panel.add(logOut);
		
		JLabel label = new JLabel("");
		
		home.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        dispose();
		        SwingUtilities.invokeLater(() -> {
		            Home home1 = new Home();
		            home1.setUndecorated(true);
		            home1.setLocationRelativeTo(null);
		            home1.setVisible(true);
		        });
		    }

		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	home.setForeground(Color.WHITE);
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	home.setForeground(new Color(0x739bcb));
		    }
		});
		
		logOut.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        dispose();
		        SwingUtilities.invokeLater(() -> {
		            frame1 frame1Instance = new frame1();
		            frame1Instance.setUndecorated(true);
		            frame1Instance.setLocationRelativeTo(null);
		            frame1Instance.setVisible(true);
		        });
		    }
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        logOut.setForeground(Color.WHITE);
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        logOut.setForeground(new Color(0x739bcb));
		    }
		});
		
		Rekrutechoise.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        dispose();
		        SwingUtilities.invokeLater(() -> {
		            reKrute rekruteMenu = new reKrute();
		            rekruteMenu.setUndecorated(true);
		            rekruteMenu.setLocationRelativeTo(null);
		            rekruteMenu.setVisible(true);
		        });
		    }
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	Rekrutechoise.setForeground(Color.WHITE);
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	Rekrutechoise.setForeground(new Color(0x739bcb));
		    }
		});
		
		label.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mousePressed(MouseEvent e) {
		        xx = e.getX();
		        xy = e.getY();
		    }
		});

		label.addMouseMotionListener(new MouseMotionAdapter() {
		    @Override
		    public void mouseDragged(MouseEvent arg0) {
		        int x = arg0.getXOnScreen();
		        int y = arg0.getYOnScreen();
		        dashboard3.this.setLocation(x - xx, y - xy);
		    }
		});
		label.setBounds(0, 0, 346, 490);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setIcon(new ImageIcon(dashboard.class.getResource("./bg3.jpg")));
		panel.add(label);
		
		JLabel lblWeGotYou = new JLabel("....Find Your Matching Job....");
		lblWeGotYou.setHorizontalAlignment(SwingConstants.CENTER);
		lblWeGotYou.setForeground(new Color(0x706b63));
		lblWeGotYou.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblWeGotYou.setBounds(85,343, 200, 27);
		panel.add(lblWeGotYou);
		
		
		
		errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setBounds(330, 270, 283, 20); 
        contentPane.add(errorLabel);
        

        String[] columnNames = {"Name", "Address", "Description", "Sector Activity"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer());
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        setModernScrollBarUI(scrollPane);
        table.setPreferredScrollableViewportSize(new Dimension(1200, 600));
        scrollPane.setBounds(380, 80, 800, 500);
        contentPane.add(scrollPane);
        
        searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        columnComboBox = new JComboBox<>(columnNames);
        columnComboBox.setBounds(380, 20, 150, 30);
        columnComboBox.setBackground(Color.WHITE);
        searchField.setBounds(540, 20, 150, 30);
        searchButton.setBounds(700, 20, 80, 30);
        searchButton.setBackground(new Color(0x739bcb));
        searchButton.setForeground(Color.white);
        searchButton.setBorder(null);
        searchButton.setFocusPainted(false);
        
        

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	updateTableWithSearch((String) columnComboBox.getSelectedItem(), searchField.getText());
            }
        });
        contentPane.add(columnComboBox);
        contentPane.add(searchField);
        contentPane.add(searchButton);
        updateTable();

		
		JLabel lbl_close = new JLabel("X");
		lbl_close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				System.exit(0);
			}
		});
		lbl_close.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_close.setForeground(new Color(241, 57, 83));
		lbl_close.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_close.setBounds(1160, 0, 37, 27);
		contentPane.add(lbl_close);
		
		
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int columnIndex = table.getTableHeader().columnAtPoint(e.getPoint());
                sorter.setSortKeys(null);

                List<RowSorter.SortKey> sortKeys = new ArrayList<>();
                sortKeys.add(new RowSorter.SortKey(columnIndex, SortOrder.ASCENDING));
                sorter.setSortKeys(sortKeys);
                sorter.sort();
            }
        });
	}
	private void updateTable() {
	    int rowCount = tableModel.getRowCount();
	    for (int i = rowCount - 1; i >= 0; i--) {
	        tableModel.removeRow(i);
	    }

	    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
	        String query = "SELECT * FROM company";
	        try (PreparedStatement statement = connection.prepareStatement(query);
	             ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	                boolean allColumnsNotEmpty = true;

	                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
	                    String columnValue = resultSet.getString(i);
	                    if (columnValue == null || columnValue.isEmpty()) {
	                        allColumnsNotEmpty = false;
	                        break; 
	                    }
	                }

	                if (allColumnsNotEmpty) {
	                    Object[] rowData = {
	                            resultSet.getString("Name"),
	                            resultSet.getString("Address"),
	                            resultSet.getString("Description"),
	                            resultSet.getString("Sector Activity")
	                    };
	                    tableModel.addRow(rowData);
	                }
	            }
	            
	            int[] columnWidths = {150,400,1000,200};
	            for (int i = 0; i < columnWidths.length; i++) {
	                table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
	            }

	            for (int i = 0; i < columnWidths.length; i++) {
	                table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
	                MultiLineTableCellRenderer renderer = new MultiLineTableCellRenderer(MAX_ROW_HEIGHT);
	                table.getColumnModel().getColumn(i).setCellRenderer(renderer);
	            }

	      
	            int maxRowHeight = 150;
	            for (int i = 0; i < table.getRowCount(); i++) {
	                int preferredRowHeight = table.getRowHeight(i);
	                table.setRowHeight(i, Math.min(preferredRowHeight, maxRowHeight));
	            }


	            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	 private void updateTableWithSearch(String selectedColumn, String searchTerm) {
	        int rowCount = tableModel.getRowCount();
	        for (int i = rowCount - 1; i >= 0; i--) {
	            tableModel.removeRow(i);
	        }

	        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
	        	String query = "SELECT * FROM company WHERE `" + selectedColumn + "` LIKE ?";
	            try (PreparedStatement statement = connection.prepareStatement(query)) {
	                statement.setString(1, "%" + searchTerm + "%");
	                try (ResultSet resultSet = statement.executeQuery()) {
	                    while (resultSet.next()) {
	                    	String data1 = resultSet.getString("Name");
	    	                String data2 = resultSet.getString("Address");
	    	                String data3 = resultSet.getString("Description");
	    	                String data4 = resultSet.getString("Sector Activity");
	    	               

	    	                Object[] rowData = {data1, data2, data3, data4};
	    	                tableModel.addRow(rowData);
	                    }
	                }
	                int[] columnWidths = {150,400,1000,200};
		            for (int i = 0; i < columnWidths.length; i++) {
		                table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
		            }

		            for (int i = 0; i < columnWidths.length; i++) {
		                table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
		                MultiLineTableCellRenderer renderer = new MultiLineTableCellRenderer(MAX_ROW_HEIGHT);
		                table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		            }

		      
		            int maxRowHeight = 150;
		            for (int i = 0; i < table.getRowCount(); i++) {
		                int preferredRowHeight = table.getRowHeight(i);
		                table.setRowHeight(i, Math.min(preferredRowHeight, maxRowHeight));
		            }


		            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	class HeaderRenderer extends DefaultTableCellRenderer {
        public HeaderRenderer() {
            setHorizontalAlignment(SwingConstants.CENTER);
            setOpaque(true);
            setBackground(new Color(0x739bcb));
            setForeground(Color.WHITE);
            setFont(new Font("Tahoma", Font.BOLD, 14));
            setPreferredSize(new Dimension(getPreferredSize().width, 40));
        }
    }
	
	private void setModernScrollBarUI(JScrollPane scrollPane) {
        UIDefaults defaults = UIManager.getDefaults();
        defaults.put("ScrollBar.thumb", new ColorUIResource(0x739bcb));
        defaults.put("ScrollBar.thumbDarkShadow", new ColorUIResource(0x739bcb));
        defaults.put("ScrollBar.thumbHighlight", new ColorUIResource(0x739bcb));
        defaults.put("ScrollBar.thumbLightShadow", new ColorUIResource(0x739bcb)); 
        defaults.put("ScrollBar.track", new ColorUIResource(0xf0f0f0));
        defaults.put("ScrollBar.trackHighlight", new ColorUIResource(0xd9d9d9));
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new BasicScrollBarUI());
    }
	
	public class MultiLineTableCellRenderer extends JTextArea implements TableCellRenderer {
	    private int maxRowHeight;

	    public MultiLineTableCellRenderer(int maxRowHeight) {
	        setLineWrap(true);
	        setWrapStyleWord(true);
	        this.maxRowHeight = maxRowHeight;
	    }

	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        setText(value != null ? value.toString() : "");

	        int preferredHeight = getPreferredSize().height;
	        if (preferredHeight > maxRowHeight) {
	            table.setRowHeight(row, preferredHeight);
	        } else {
	            table.setRowHeight(row, maxRowHeight);
	        }

	        if (!isSelected) {
	            Color background = (row % 2 == 0) ? Color.WHITE : new Color(240, 240, 240); // Light gray
	            setBackground(background);
	        }

	        return this;
	    }

	    @Override
	    public void setSize(Dimension d) {
	        if (maxRowHeight > 0) {
	            d.height = maxRowHeight;
	        }
	        super.setSize(d);
	    }

	    @Override
	    public void setSize(int width, int height) {
	        if (maxRowHeight > 0) {
	            height = maxRowHeight;
	        }
	        super.setSize(width, height);
	    }
	}



}

