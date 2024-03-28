package projetJava;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


public class reKrute extends JFrame {

	private JPanel contentPane;

	
	int xx,xy;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					reKrute frame = new reKrute();
					frame.setUndecorated(true);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public reKrute() {
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 729, 476);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(0, 0, 346, 490);
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
		home.setBounds(100, 370, 160, 27);
		panel.add(home);
		
		JLabel companies = new JLabel("<html><u>Companies</u></html>");
		companies.setHorizontalAlignment(SwingConstants.CENTER);
		companies.setFont(new Font("Tahoma", Font.PLAIN, 14));
		companies.setForeground(new Color(0x739bcb));
		companies.setBounds(100, 390, 160, 27);
		panel.add(companies);
		
		JLabel stats = new JLabel("<html><u>Statistics</u></html>");
		stats.setHorizontalAlignment(SwingConstants.CENTER);
		stats.setFont(new Font("Tahoma", Font.PLAIN, 14));
		stats.setForeground(new Color(0x739bcb));
		stats.setBounds(100, 410, 160, 27);
		panel.add(stats);
		
		JLabel logOut = new JLabel("<html><u>Log Out</u></html>");
		logOut.setHorizontalAlignment(SwingConstants.CENTER);
		logOut.setFont(new Font("Tahoma", Font.PLAIN, 14));
		logOut.setForeground(new Color(0x739bcb));
		logOut.setBounds(100, 430, 160, 27);
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
		
		
		companies.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        dispose();
		        SwingUtilities.invokeLater(() -> {
		            dashboard3 dashboard3 = new dashboard3();
		            dashboard3.setUndecorated(true);
		            dashboard3.setLocationRelativeTo(null);
		            dashboard3.setVisible(true);
		        });
		    }

		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	companies.setForeground(Color.WHITE);
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	companies.setForeground(new Color(0x739bcb));
		    }
		});
		
		
		stats.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        dispose();
		        SwingUtilities.invokeLater(() -> {
		            StatRekrute dashboard3 = new StatRekrute();
		            dashboard3.setUndecorated(true);
		            dashboard3.setLocationRelativeTo(null);
		            dashboard3.setVisible(true);
		        });
		    }

		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	stats.setForeground(Color.WHITE);
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	stats.setForeground(new Color(0x739bcb));
		    }
		});
		
		
		logOut.addMouseListener(new MouseAdapter() {
		    @Override
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
		        reKrute.this.setLocation(x - xx, y - xy);
		    }
		});
		label.setBounds(0, 0, 346, 490);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setIcon(new ImageIcon(reKrute.class.getResource("/bg3.jpg")));
		panel.add(label);
		
		JLabel title2 = new JLabel("....Find Your Matching Job....");
		title2.setHorizontalAlignment(SwingConstants.CENTER);
		title2.setForeground(new Color(0x706b63));
		title2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		title2.setBounds(85,343, 200, 27);
		panel.add(title2);
		
		JLabel titleRekrute = new JLabel("OFFERS FROM REKRUTE");
		titleRekrute.setHorizontalAlignment(SwingConstants.CENTER);
		titleRekrute.setForeground(new Color(0x706b63));
		titleRekrute.setFont(new Font("Tahoma", Font.BOLD, 15));
		titleRekrute.setBounds(390,120, 200, 14);
		contentPane.add(titleRekrute);
        
		
		JLabel allData = new JLabel("ALL DATA");
		allData.setForeground(new Color(0xbc7c46));
		allData.setBounds(395, 160, 200, 14);
		contentPane.add(allData);
		
		JButton allDataButton = new JButton("Get all data");
		allDataButton.setForeground(Color.WHITE);
		allDataButton.setBackground(new Color(0x739bcb));
		allDataButton.setBounds(395, 180, 283, 36);
		allDataButton.setBorder(null);
		allDataButton.setFocusPainted(false);
		allDataButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	showDashboard();
            }
        });
        contentPane.add(allDataButton);
		
		
		JLabel DataProcessed = new JLabel("DATA PROCESSED");
		DataProcessed.setForeground(new Color(0xbc7c46));
		DataProcessed.setBounds(395, 240, 200, 14);
		contentPane.add(DataProcessed);
		
		JButton DataProcessedButton = new JButton("Get data processed");
		DataProcessedButton.setForeground(Color.WHITE);
		DataProcessedButton.setBackground(new Color(0x739bcb));
		DataProcessedButton.setBounds(395, 260, 283, 36);
		DataProcessedButton.setBorder(null);
		DataProcessedButton.setFocusPainted(false);
		DataProcessedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	showDashboard2();
            }
        });
        contentPane.add(DataProcessedButton);
        
        
        
		

		
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
		lbl_close.setBounds(691, 0, 37, 27);
		contentPane.add(lbl_close);
	}
	
	private void showDashboard() {
        SwingUtilities.invokeLater(() -> {
           dashboard dashboard1 = new dashboard();
           	dashboard1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           	dashboard1.setUndecorated(true);
           	dashboard1.setLocationRelativeTo(null);
           	dashboard1.setVisible(true);
        });
    }
	
	private void showDashboard2() {
        SwingUtilities.invokeLater(() -> {
           dashboard2 dashboard2 = new dashboard2();
           	dashboard2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           	dashboard2.setUndecorated(true);
           	dashboard2.setLocationRelativeTo(null);
           	dashboard2.setVisible(true);
        });
    }
}
