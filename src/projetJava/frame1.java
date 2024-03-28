package projetJava;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
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

public class frame1 extends JFrame {

	private JPanel contentPane;
	private JTextField textField_1;
	private JPasswordField passwordField;
	private JLabel errorLabel; 
	
	int xx,xy;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame1 frame = new frame1();
					frame.setUndecorated(true);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public frame1() {
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
		
		JLabel signIn = new JLabel("<html><u>Sign In</u></html>");
		signIn.setHorizontalAlignment(SwingConstants.CENTER);
		signIn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		signIn.setForeground(new Color(0x739bcb));
		signIn.setBounds(100, 400, 160, 27);
		panel.add(signIn);
		
		JLabel label = new JLabel("");
		
		signIn.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        dispose();
		        SwingUtilities.invokeLater(() -> {
		            frame2 frame2Instance = new frame2();
		            frame2Instance.setUndecorated(true);
		            frame2Instance.setLocationRelativeTo(null);
		            frame2Instance.setVisible(true);
		        });
		    }
		    public void mouseEntered(MouseEvent e) {
		    	signIn.setForeground(Color.WHITE);
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	signIn.setForeground(new Color(0x739bcb));
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
		        frame1.this.setLocation(x - xx, y - xy);
		    }
		});
		label.setBounds(0, 0, 346, 490);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setIcon(new ImageIcon(frame1.class.getResource("./bg3.jpg")));
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
        
		JButton button = new JButton("LOGIN");
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(0x739bcb));
		button.setBounds(395, 300, 283, 36);
		button.setBorder(null);
		button.setFocusPainted(false);
		button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = textField_1.getText();
                String password = new String(passwordField.getPassword());

                if (authenticateUser(email, password)) {
                    errorLabel.setText("");
                    resetInputBorders();
                } else {
                    errorLabel.setText("Invalid email or password");
                    setInvalidInputBorders();
                }
            }
        });
        contentPane.add(button);
		
        
		
		JLabel lblEmail = new JLabel("EMAIL");
		lblEmail.setForeground(new Color(0xbc7c46));
		lblEmail.setBounds(395, 132, 54, 14);
		contentPane.add(lblEmail);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(395, 157, 283, 36);
		contentPane.add(textField_1);
		
		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setForeground(new Color(0xbc7c46));
		lblPassword.setBounds(395, 204, 96, 14);
		contentPane.add(lblPassword);
		
		
		passwordField = new JPasswordField();
		passwordField.setBounds(395, 229, 283, 36);
		contentPane.add(passwordField);
		

		
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
    private void setInvalidInputBorders() {
        textField_1.setBorder(BorderFactory.createLineBorder(Color.RED));
        passwordField.setBorder(BorderFactory.createLineBorder(Color.RED));
    }

    private void resetInputBorders() {
        textField_1.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        passwordField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("PasswordField.border"));
    }
    
    private boolean authenticateUser(String email, String password) {
        try {
        
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjava", "root", "");

            
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    boolean authenticated = resultSet.next();
                    if (authenticated) {
                        dispose();
                        showHome();
                    }
                    return authenticated;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private void showHome() {
        SwingUtilities.invokeLater(() -> {
           Home home = new Home();
            home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            home.setUndecorated(true);
            home.setLocationRelativeTo(null);
            home.setVisible(true);
        });
    }
}
