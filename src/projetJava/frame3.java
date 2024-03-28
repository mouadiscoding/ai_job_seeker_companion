package projetJava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class frame3 {
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    private JCheckBox checkBox3;
    private JCheckBox checkBox4;
    private JTextField searchField;

    public frame3() {
        JFrame frame = new JFrame("Database Integration with Search Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        checkBox1 = createStyledCheckbox("Skill 1");
        checkBox2 = createStyledCheckbox("Skill 2");
        checkBox3 = createStyledCheckbox("Skill 3");
        checkBox4 = createStyledCheckbox("Skill 4");

        searchField = new JTextField(15);
        searchField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Unused
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Unused
            }

            @Override
            public void keyReleased(KeyEvent e) {
                filterSkills(searchField.getText());
            }
        });

        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(checkBox1);
        popupMenu.add(checkBox2);
        popupMenu.add(checkBox3);
        popupMenu.add(checkBox4);

        JButton dropdownButton = new JButton("Open Dropdown");
        dropdownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupMenu.show(dropdownButton, 0, dropdownButton.getHeight());
            }
        });

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the selected checkboxes
                String selectedSkills = getSelectedSkillsAsString();
                insertSelectedOptionsIntoDatabase(selectedSkills);
            }
        });

        JPanel panel = new JPanel();
        panel.add(searchField);
        panel.add(dropdownButton);
        panel.add(submitButton);

        frame.getContentPane().add(panel);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JCheckBox createStyledCheckbox(String text) {
        JCheckBox checkBox = new JCheckBox(text);
        // (styling code as before...)
        return checkBox;
    }

    private String getSelectedSkillsAsString() {
        List<String> selectedSkillsList = new ArrayList<>();

        if (checkBox1.isSelected()) {
            selectedSkillsList.add("Skill 1");
        }
        if (checkBox2.isSelected()) {
            selectedSkillsList.add("Skill 2");
        }
        if (checkBox3.isSelected()) {
            selectedSkillsList.add("Skill 3");
        }
        if (checkBox4.isSelected()) {
            selectedSkillsList.add("Skill 4");
        }

        // Join the selected skills into a single string, separated by commas
        return String.join(", ", selectedSkillsList);
    }

    private void insertSelectedOptionsIntoDatabase(String selectedSkills) {
        String url = "jdbc:mysql://localhost:3306/projetjava";
        String username = "root";
        String password = "";
        String name = "Othman";
        String email = "othmanettahiri2@gmail.com";
        String password2 = "root";
        String softSkills = "hhhh";
        String experience = "jjjj";
        String region = "Oujda";
        		
       
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO users (name,email,password,hard_skills,soft_skills,experience,region) VALUES (?,?,?,?,?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
            	statement.setString(1, name);
            	statement.setString(2, email);
            	statement.setString(3, password2);
                statement.setString(4, selectedSkills);
                statement.setString(5, softSkills);
                statement.setString(6, experience);
                statement.setString(7, region);
                statement.executeUpdate();
                System.out.println("Data inserted into the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void filterSkills(String searchText) {
        // Use the search text to filter checkboxes
        checkBox1.setVisible(checkBox1.getText().toLowerCase().contains(searchText.toLowerCase()));
        checkBox2.setVisible(checkBox2.getText().toLowerCase().contains(searchText.toLowerCase()));
        checkBox3.setVisible(checkBox3.getText().toLowerCase().contains(searchText.toLowerCase()));
        checkBox4.setVisible(checkBox4.getText().toLowerCase().contains(searchText.toLowerCase()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new frame3();
        });
    }
}




