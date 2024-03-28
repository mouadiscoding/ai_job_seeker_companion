package projetJava;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.concurrent.ExecutionException;


public class Home extends JFrame {

	private JPanel contentPane;

	
	int xx,xy;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setUndecorated(true);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private JLabel statusLabel;
	public Home() {
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
		lblNewLabel.setBounds(134, 290, 100, 27);
		panel.add(lblNewLabel);
		
		
		
		JLabel users = new JLabel("<html><u>Users</u></html>");
		users.setHorizontalAlignment(SwingConstants.CENTER);
		users.setFont(new Font("Tahoma", Font.PLAIN, 14));
		users.setForeground(new Color(0x739bcb));
		users.setBounds(-2, 400, 160, 27);
		panel.add(users);
		
		JLabel emploichoise = new JLabel("<html><u>Emploi Data</u></html>");
		emploichoise.setHorizontalAlignment(SwingConstants.CENTER);
		emploichoise.setFont(new Font("Tahoma", Font.PLAIN, 14));
		emploichoise.setForeground(new Color(0x739bcb));
		emploichoise.setBounds(18, 370, 160, 27);
		panel.add(emploichoise);
		
		JLabel Rekrutechoise = new JLabel("<html><u>Rekrute Data</u></html>");
		Rekrutechoise.setHorizontalAlignment(SwingConstants.CENTER);
		Rekrutechoise.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Rekrutechoise.setForeground(new Color(0x739bcb));
		Rekrutechoise.setBounds(20, 340, 160, 27);
		panel.add(Rekrutechoise);
		
		JLabel Mjobchoise = new JLabel("<html><u>Menara Job Data</u></html>");
		Mjobchoise.setHorizontalAlignment(SwingConstants.CENTER);
		Mjobchoise.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Mjobchoise.setForeground(new Color(0x739bcb));
		Mjobchoise.setBounds(170, 340, 160, 27);
		panel.add(Mjobchoise);
		
		JLabel MonCallchoise = new JLabel("<html><u>MonCallCenter Data</u></html>");
		MonCallchoise.setHorizontalAlignment(SwingConstants.CENTER);
		MonCallchoise.setFont(new Font("Tahoma", Font.PLAIN, 14));
		MonCallchoise.setForeground(new Color(0x739bcb));
		MonCallchoise.setBounds(180, 370, 160, 27);
		panel.add(MonCallchoise);
		
		JLabel prediction = new JLabel("<html><u>Prediction</u></html>");
		prediction.setHorizontalAlignment(SwingConstants.CENTER);
		prediction.setFont(new Font("Tahoma", Font.PLAIN, 14));
		prediction.setForeground(new Color(0x739bcb));
		prediction.setBounds(150, 400, 160, 27);
		panel.add(prediction);
		
		prediction.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        dispose();
		        SwingUtilities.invokeLater(() -> {
		            PredictionGUI frame1Instance = new PredictionGUI();
		            frame1Instance.setUndecorated(true);
		            frame1Instance.setLocationRelativeTo(null);
		            frame1Instance.setVisible(true);
		        });
		    }
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	prediction.setForeground(Color.WHITE);
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	prediction.setForeground(new Color(0x739bcb));
		    }
		});
		
		
		JLabel logOut = new JLabel("<html><u>Log Out</u></html>");
		logOut.setHorizontalAlignment(SwingConstants.CENTER);
		logOut.setFont(new Font("Tahoma", Font.PLAIN, 14));
		logOut.setForeground(new Color(0x739bcb));
		logOut.setBounds(6, 430, 160, 27);
		panel.add(logOut);
		
		JLabel label = new JLabel("");
		
		Rekrutechoise.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        dispose();
		        SwingUtilities.invokeLater(() -> {
		        	showRekrute();
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
		
		emploichoise.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        dispose();
		        SwingUtilities.invokeLater(() -> {
		        	showEmploiMa();
		        });
		    }

		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	emploichoise.setForeground(Color.WHITE);
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	emploichoise.setForeground(new Color(0x739bcb));
		    }
		});
		
			Mjobchoise.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	dispose();
		        SwingUtilities.invokeLater(() -> {
		        	showMJob();
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
			
			MonCallchoise.addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseClicked(MouseEvent e) {
			        dispose();
			        SwingUtilities.invokeLater(() -> {
			        	showMonnCall();
			        });
			    }

			    @Override
			    public void mouseEntered(MouseEvent e) {
			    	MonCallchoise.setForeground(Color.WHITE);
			    }

			    @Override
			    public void mouseExited(MouseEvent e) {
			    	MonCallchoise.setForeground(new Color(0x739bcb));
			    }
			});
		
			users.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        dispose();
		        SwingUtilities.invokeLater(() -> {
		        	showusers();
		        });
		    }

		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	users.setForeground(Color.WHITE);
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	users.setForeground(new Color(0x739bcb));
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
		        Home.this.setLocation(x - xx, y - xy);
		    }
		});
		label.setBounds(0, 0, 346, 490);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setIcon(new ImageIcon(Home.class.getResource("./bg3.jpg")));
		panel.add(label);
		
		JLabel title2 = new JLabel("....Find Your Matching Job....");
		title2.setHorizontalAlignment(SwingConstants.CENTER);
		title2.setForeground(new Color(0x706b63));
		title2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		title2.setBounds(85,310, 200, 27);
		panel.add(title2);
		
		statusLabel = new JLabel();
        statusLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        statusLabel.setBounds(395, 400, 283, 36);
        contentPane.add(statusLabel);
		
		JLabel rekrute = new JLabel("SCRAP REKRUTE");
		rekrute.setForeground(new Color(0xbc7c46));
		rekrute.setBounds(395, 130, 200, 14);
		contentPane.add(rekrute);
		
		JButton Rekrutebutton = new JButton("Rekrute");
		Rekrutebutton.setForeground(Color.WHITE);
		Rekrutebutton.setBackground(new Color(0x739bcb));
		Rekrutebutton.setBounds(395, 150, 283, 36);
		Rekrutebutton.setBorder(null);
		Rekrutebutton.setFocusPainted(false);
		Rekrutebutton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        showScrapingInProgressMessage();
		        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
		            @Override
		            protected Void doInBackground() throws Exception {
		                webScrap webScrap = new webScrap();
		                webScrap.runWebScraping();
		                return null;
		            }

		            @Override
		            protected void done() {
		                SwingUtilities.invokeLater(() -> {
		                    try {
		                        get();
		                        showScrapingFinishedMessage();
		                    } catch (InterruptedException | ExecutionException e) {
		                        e.printStackTrace();
		                        showScrapingFailedMessage();
		                    }
		                });
		            }
		        };
		        worker.execute();
		    }
		});

        contentPane.add(Rekrutebutton);
        
		
		JLabel MenaraJob = new JLabel("SCRAP MENARA JOB");
		MenaraJob.setForeground(new Color(0xbc7c46));
		MenaraJob.setBounds(395, 200, 200, 14);
		contentPane.add(MenaraJob);
		
		JButton MenaraJobbutton = new JButton("MENARA JOB");
		MenaraJobbutton.setForeground(Color.WHITE);
		MenaraJobbutton.setBackground(new Color(0x739bcb));
		MenaraJobbutton.setBounds(395, 220, 283, 36);
		MenaraJobbutton.setBorder(null);
		MenaraJobbutton.setFocusPainted(false);
		MenaraJobbutton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        showScrapingInProgressMessage();
		        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
		            @Override
		            protected Void doInBackground() throws Exception {
		                webScrap2 webScrap = new webScrap2();
		                webScrap.runWebScraping();
		                return null;
		            }

		            @Override
		            protected void done() {
		                SwingUtilities.invokeLater(() -> {
		                    try {
		                        get();
		                        showScrapingFinishedMessage();
		                    } catch (InterruptedException | ExecutionException e) {
		                        e.printStackTrace();
		                        showScrapingFailedMessage();
		                    }
		                });
		            }
		        };
		        worker.execute();
		    }
		});
        contentPane.add(MenaraJobbutton);
        
        
        JLabel emploima = new JLabel("SCRAP EMPLOI");
        emploima.setForeground(new Color(0xbc7c46));
        emploima.setBounds(395, 270, 200, 14);
		contentPane.add(emploima);
		
		JButton emploimabutton = new JButton("EMPLOI");
		emploimabutton.setForeground(Color.WHITE);
		emploimabutton.setBackground(new Color(0x739bcb));
		emploimabutton.setBounds(395, 290, 283, 36);
		emploimabutton.setBorder(null);
		emploimabutton.setFocusPainted(false);
		emploimabutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showScrapingInProgressMessage();
		        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
		            @Override
		            protected Void doInBackground() throws Exception {
		                webScrap3 webScrap = new webScrap3();
		                webScrap.runWebScraping();
		                return null;
		            }

		            @Override
		            protected void done() {
		                SwingUtilities.invokeLater(() -> {
		                    try {
		                        get();
		                        showScrapingFinishedMessage();
		                    } catch (InterruptedException | ExecutionException e) {
		                        e.printStackTrace();
		                        showScrapingFailedMessage();
		                    }
		                });
		            }
		        };
		        worker.execute();
            }
        });
        contentPane.add(emploimabutton);
		
        
        JLabel monCall = new JLabel("SCRAP MONCALLCENTER");
        monCall.setForeground(new Color(0xbc7c46));
        monCall.setBounds(395, 340, 200, 14);
		contentPane.add(monCall);

		
		JButton monCallbutton = new JButton("MONCALLCENTER");
		monCallbutton.setForeground(Color.WHITE);
		monCallbutton.setBackground(new Color(0x739bcb));
		monCallbutton.setBounds(395, 360, 283, 36);
		monCallbutton.setBorder(null);
		monCallbutton.setFocusPainted(false);
		monCallbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showScrapingInProgressMessage();
		        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
		            @Override
		            protected Void doInBackground() throws Exception {
		                webScrap4 webScrap = new webScrap4();
		                webScrap.runWebScraping();
		                return null;
		            }

		            @Override
		            protected void done() {
		                SwingUtilities.invokeLater(() -> {
		                    try {
		                        get();
		                        showScrapingFinishedMessage();
		                    } catch (InterruptedException | ExecutionException e) {
		                        e.printStackTrace();
		                        showScrapingFailedMessage();
		                    }
		                });
		            }
		        };
		        worker.execute();
            }
        });
        contentPane.add(monCallbutton);
		
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
	
	private void showScrapingInProgressMessage() {
        statusLabel.setForeground(new Color(255, 165, 0));
        statusLabel.setText("Scraping in progress. Please wait...");
    }

    private void showScrapingFinishedMessage() {
        statusLabel.setForeground(new Color(0, 128, 0));
        statusLabel.setText("Scraping finished!");
    }
    private void showScrapingFailedMessage() {
        statusLabel.setForeground(new Color(255, 0, 0)); // Red color for error
        statusLabel.setText("Scraping failed. Check the console for details.");
    }
	
	private void showRekrute() {
        SwingUtilities.invokeLater(() -> {
        	reKrute rekrute = new reKrute();
        	rekrute.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	rekrute.setUndecorated(true);
        	rekrute.setLocationRelativeTo(null);
        	rekrute.setVisible(true);
        });
    }
	
	private void showMJob() {
        SwingUtilities.invokeLater(() -> {
        	MJob mjob = new MJob();
        	 mjob.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	 mjob.setUndecorated(true);
        	 mjob.setLocationRelativeTo(null);
        	 mjob.setVisible(true);
        });
    }
	
	private void showEmploiMa() {
        SwingUtilities.invokeLater(() -> {
        	Emploi emploima = new Emploi();
        	emploima.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	emploima.setUndecorated(true);
        	emploima.setLocationRelativeTo(null);
        	emploima.setVisible(true);
        });
    }
	
	private void showMonnCall() {
        SwingUtilities.invokeLater(() -> {
        	MonCall MonCall = new MonCall();
        	MonCall.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	MonCall.setUndecorated(true);
        	MonCall.setLocationRelativeTo(null);
        	MonCall.setVisible(true);
        });
    }
	
	private void showusers() {
        SwingUtilities.invokeLater(() -> {
        	Users Users = new Users();
        	Users.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	Users.setUndecorated(true);
        	Users.setLocationRelativeTo(null);
        	Users.setVisible(true);
        });
    }
    
}
