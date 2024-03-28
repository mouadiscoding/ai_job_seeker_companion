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


public class StatMJob extends JFrame {

	private JPanel contentPane;

	
	int xx,xy;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StatMJob frame = new StatMJob();
					frame.setUndecorated(true);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public StatMJob() {
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
		home.setBounds(100, 380, 160, 27);
		panel.add(home);
		
		JLabel Mjobchoise = new JLabel("<html><u>Menara Job Data</u></html>");
		Mjobchoise.setHorizontalAlignment(SwingConstants.CENTER);
		Mjobchoise.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Mjobchoise.setForeground(new Color(0x739bcb));
		Mjobchoise.setBounds(100, 400, 160, 27);
		panel.add(Mjobchoise);
		
		JLabel logOut = new JLabel("<html><u>Log Out</u></html>");
		logOut.setHorizontalAlignment(SwingConstants.CENTER);
		logOut.setFont(new Font("Tahoma", Font.PLAIN, 14));
		logOut.setForeground(new Color(0x739bcb));
		logOut.setBounds(100, 420, 160, 27);
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
		
		Mjobchoise.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        dispose();
		        SwingUtilities.invokeLater(() -> {
		            MJob MjobMenu = new MJob();
		            MjobMenu.setUndecorated(true);
		            MjobMenu.setLocationRelativeTo(null);
		            MjobMenu.setVisible(true);
		        });
		    }
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	Mjobchoise.setForeground(Color.WHITE);
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	Mjobchoise.setForeground(new Color(0x739bcb));
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
		        StatMJob.this.setLocation(x - xx, y - xy);
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
		
		JLabel titleRekrute = new JLabel("STATISTICS FROM MENARA JOB");
		titleRekrute.setHorizontalAlignment(SwingConstants.CENTER);
		titleRekrute.setForeground(new Color(0x706b63));
		titleRekrute.setFont(new Font("Tahoma", Font.BOLD, 15));
		titleRekrute.setBounds(380,80, 280, 14);
		contentPane.add(titleRekrute);
        
		
		JLabel dataDate = new JLabel("BY DATE");
		dataDate.setForeground(new Color(0xbc7c46));
		dataDate.setBounds(395, 120, 200, 14);
		contentPane.add(dataDate);
		
		JButton dataDateButton = new JButton("Stats By Date");
		dataDateButton.setForeground(Color.WHITE);
		dataDateButton.setBackground(new Color(0x739bcb));
		dataDateButton.setBounds(395, 140, 283, 36);
		dataDateButton.setBorder(null);
		dataDateButton.setFocusPainted(false);
		dataDateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        MjobByDate MjobByDate = new MjobByDate("Job offers by Date");
		        MjobByDate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        MjobByDate.setSize(760, 567);
		        MjobByDate.setLocationRelativeTo(null);
		        MjobByDate.setVisible(true);
		    }
		});
        contentPane.add(dataDateButton);
		
		
		JLabel dataSector = new JLabel("BY SECTOR");
		dataSector.setForeground(new Color(0xbc7c46));
		dataSector.setBounds(395, 200, 200, 14);
		contentPane.add(dataSector);
		
		JButton dataSectorButton = new JButton("Stats By Sector");
		dataSectorButton.setForeground(Color.WHITE);
		dataSectorButton.setBackground(new Color(0x739bcb));
		dataSectorButton.setBounds(395, 220, 283, 36);
		dataSectorButton.setBorder(null);
		dataSectorButton.setFocusPainted(false);
		dataSectorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	MjobBySector MjobBySector = new MjobBySector("Job offers by Date");
            	MjobBySector.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            	MjobBySector.setSize(760, 567);
            	MjobBySector.setLocationRelativeTo(null);
            	MjobBySector.setVisible(true);
            }
        });
        contentPane.add(dataSectorButton);
        
        
        JLabel dataRegion = new JLabel("BY REGION");
        dataRegion.setForeground(new Color(0xbc7c46));
        dataRegion.setBounds(395, 280, 200, 14);
		contentPane.add(dataRegion);
		
		JButton dataRegionButton = new JButton("Stats By Region");
		dataRegionButton.setForeground(Color.WHITE);
		dataRegionButton.setBackground(new Color(0x739bcb));
		dataRegionButton.setBounds(395, 300, 283, 36);
		dataRegionButton.setBorder(null);
		dataRegionButton.setFocusPainted(false);
		dataRegionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	MjobBYRegion MjobBYRegion = new MjobBYRegion("Job offers by Date");
            	MjobBYRegion.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            	MjobBYRegion.setSize(760, 567);
            	MjobBYRegion.setLocationRelativeTo(null);
            	MjobBYRegion.setVisible(true);
            }
        });
        contentPane.add(dataRegionButton);
        
        
        JLabel datacontrat = new JLabel("BY CONTRAT");
        datacontrat.setForeground(new Color(0xbc7c46));
        datacontrat.setBounds(395, 360, 200, 14);
		contentPane.add(datacontrat);
		
		JButton datacontratButton = new JButton("Stats By Contrat");
		datacontratButton.setForeground(Color.WHITE);
		datacontratButton.setBackground(new Color(0x739bcb));
		datacontratButton.setBounds(395, 380, 283, 36);
		datacontratButton.setBorder(null);
		datacontratButton.setFocusPainted(false);
		datacontratButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	RekruteByContrat rekruteByContrat = new RekruteByContrat("Job offers by Date");
            	rekruteByContrat.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            	rekruteByContrat.setSize(760, 567);
            	rekruteByContrat.setLocationRelativeTo(null);
            	rekruteByContrat.setVisible(true);
            }
        });
        contentPane.add(datacontratButton);
        
		

		
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