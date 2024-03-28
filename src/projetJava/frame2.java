package projetJava;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class frame2 extends JFrame {

	private JPanel contentPane;
	private JTextField textField_1;
	private JPasswordField passwordField;
	
	int xx,xy;
	private File selectedCVFile;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame2 frame = new frame2();
					frame.setUndecorated(true);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private void insertDataIntoDatabase() throws FileNotFoundException {
		FileInputStream cvInputStream = null;
        String name = textField_Name.getText();
        String email = textField_1.getText();
        String password = new String(passwordField.getPassword());

        String region = textField_Region.getText();
        String experience = textField_Exp.getText();

        if (!validateInput(name, email, password,region, experience)) {
            return;
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjava", "root", "");
            String query = "INSERT INTO users (name, email, password, experience,region,cv_content) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, experience);
            statement.setString(5, region);
            
            cvInputStream = new FileInputStream(selectedCVFile);
            statement.setBinaryStream(6, cvInputStream);

            statement.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (cvInputStream != null) {
                    cvInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateInput(String name, String email, String password, String region, String experience) {
        boolean isValid = true;

        if (name.isEmpty()) {
            textField_Name.setBorder(BorderFactory.createLineBorder(Color.RED));
            isValid = false;
        } else {
            textField_Name.setBorder(UIManager.getBorder("TextField.border"));
        }

        if (email.isEmpty() || !isValidEmail(email)) {
            textField_1.setBorder(BorderFactory.createLineBorder(Color.RED));
            isValid = false;
        } else {
            textField_1.setBorder(UIManager.getBorder("TextField.border"));
        }

        if (password.isEmpty()) {
            passwordField.setBorder(BorderFactory.createLineBorder(Color.RED));
            isValid = false;
        } else {
            passwordField.setBorder(UIManager.getBorder("TextField.border"));
        }


        if (region.isEmpty()) {
            textField_Region.setBorder(BorderFactory.createLineBorder(Color.RED));
            isValid = false;
        } else {
            textField_Region.setBorder(UIManager.getBorder("TextField.border"));
        }

        if (experience.isEmpty()) {
            textField_Exp.setBorder(BorderFactory.createLineBorder(Color.RED));
            isValid = false;
        } else {
            textField_Exp.setBorder(UIManager.getBorder("TextField.border"));
        }

        if (!isValidEmail(email)) {
            textField_1.setBorder(BorderFactory.createLineBorder(Color.RED));
            isValid = false;
        } else {
            textField_1.setBorder(UIManager.getBorder("TextField.border"));
        }

        if (!isValid) {
            errorLabel.setText("All fields are required.");
        } else {
            errorLabel.setText("");
        }

        return isValid;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
	

	private JLabel errorLabel; 
	private JTextField textField_Name;
	private JTextField textField_SoftSkills;
	private JTextField textField_HardSkills;
	private JTextField textField_Region;
	private JTextField textField_Exp;
	public frame2() {
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
		
		JLabel haveAccount = new JLabel("<html><u>Already Have Account?</u></html>");
		haveAccount.setHorizontalAlignment(SwingConstants.CENTER);
		haveAccount.setFont(new Font("Tahoma", Font.PLAIN, 14));
		haveAccount.setForeground(new Color(0x739bcb));
		haveAccount.setBounds(100, 400, 160, 27);
		panel.add(haveAccount);
		
		haveAccount.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        dispose();
		        SwingUtilities.invokeLater(() -> {
		            frame1 frame1Instance = new frame1();
		            frame1Instance.setUndecorated(true);
		            frame1Instance.setLocationRelativeTo(null);
		            frame1Instance.setVisible(true);
		        });
		    }
		    public void mouseEntered(MouseEvent e) {
		    	haveAccount.setForeground(Color.WHITE);
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	haveAccount.setForeground(new Color(0x739bcb));
		    }
		});
		
		JLabel label = new JLabel("");
		
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
		        frame2.this.setLocation(x - xx, y - xy);
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
        errorLabel.setBounds(395, 440, 283, 20); 
        contentPane.add(errorLabel);
        
		JButton button = new JButton("SIGN");
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(0x739bcb));
		button.setBounds(395, 390, 283, 36);
		button.setBorder(null);
		button.setFocusPainted(false);

		contentPane.add(button);
		
		button.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent arg0) {
		        try {
		            insertDataIntoDatabase();
		        } catch (FileNotFoundException e) {
		            e.printStackTrace();
		            // Handle or propagate the exception as needed
		        }
		    }
		});
        
		JLabel lblName = new JLabel("NAME");
		lblName.setForeground(new Color(0xbc7c46));
		lblName.setBounds(395, 10, 54, 14);
		contentPane.add(lblName);
		
		textField_Name = new JTextField();
		textField_Name.setColumns(10);
		textField_Name.setBounds(395, 30, 283, 36);
        contentPane.add(textField_Name);
		
		JLabel lblEmail = new JLabel("EMAIL");
		lblEmail.setForeground(new Color(0xbc7c46));
		lblEmail.setBounds(395, 70, 54, 14);
		contentPane.add(lblEmail);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(395, 90, 283, 36);
		contentPane.add(textField_1);
		
		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setForeground(new Color(0xbc7c46));
		lblPassword.setBounds(395, 130, 96, 14);
		contentPane.add(lblPassword);
		
		
		passwordField = new JPasswordField();
		passwordField.setBounds(395, 150, 283, 36);
		contentPane.add(passwordField);
		

        JLabel lblRegion = new JLabel("REGION");
        lblRegion.setForeground(new Color(0xbc7c46));
        lblRegion.setBounds(395, 250, 96, 14);
        contentPane.add(lblRegion);

        textField_Region = new JTextField();
        textField_Region.setColumns(10);
        textField_Region.setBounds(395, 270, 283, 36);
        contentPane.add(textField_Region);
        
        JLabel lblExp = new JLabel("EXPERIENCE");
        lblExp.setForeground(new Color(0xbc7c46));
        lblExp.setBounds(395, 190, 96, 14);
        contentPane.add(lblExp);
        
        JLabel lblCV = new JLabel("CV FILE");
        lblCV.setForeground(new Color(0xbc7c46));
        lblCV.setBounds(395, 310, 96, 14);
        contentPane.add(lblCV);
		
        textField_Exp = new JTextField();
        textField_Exp.setColumns(10);
        textField_Exp.setBounds(395, 210, 283, 36);
        contentPane.add(textField_Exp);
        
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
		
		JButton btnUploadCV = new JButton("Upload CV");
        btnUploadCV.setForeground(Color.WHITE);
        btnUploadCV.setBackground(new Color(0x739bcb));
        btnUploadCV.setBounds(395, 330, 283, 36);
        btnUploadCV.setBorder(null);
        btnUploadCV.setFocusPainted(false);
        contentPane.add(btnUploadCV);

        btnUploadCV.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                uploadCV();
            }
        });
	}
	
	private void uploadCV() {
	    JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	    fileChooser.setAcceptAllFileFilterUsed(false);
	    fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));

	    int result = fileChooser.showOpenDialog(this);

	    if (result == JFileChooser.APPROVE_OPTION) {
	        selectedCVFile = fileChooser.getSelectedFile();
	        System.out.println("Selected CV file: " + selectedCVFile.getAbsolutePath());
	    }
	}
    
}
