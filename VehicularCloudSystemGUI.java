/* Project: Project Milestone 2: GUI
* Class: VehicularCloudConsole.java
* Author: Jeremy Infante, Logan Amade, Brandon Khan, Ramon Guzman
* Date: February 23rd, 2025
* * This program produces a GUI for a vehicular cloud system. The GUI console provides
* two options: one for the client and one for the owner. The owner provides information
* regarding owner ID, vehicle information, and residency times in hours. The client
* provides information of their client ID, job ID, Job Duration (hrs), and Job Deadline
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//Enables for two separate fields for either client and owner side. With the submit button function
public class VehicularCloudConsole{
    private JFrame frame;
    private JPanel panel;
    private JRadioButton clientButton, ownerButton;
    private JTextArea clientIdField, jobIdField, jobDurationField, jobDeadlineField, ownerIdField, vehicleInfoField, residencyTimeField;
    private JButton submitButton;
    private ButtonGroup group;

    //Creates the gui system which allows for a specific format for the information to be dispalyed
    public VehicularCloudConsole(){
        frame = new JFrame("Vehicular Cloud Console");
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1;
        gbc.weighty = 0;
        
        //Inserts text so the user can read the selection types for Client and Owner
        JLabel userTypeLabel = new JLabel("Select User Type:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(userTypeLabel, gbc);
        
        //Seperate buttons for each entry for both Client and Owner
        clientButton = new JRadioButton("Client");
        ownerButton = new JRadioButton("Owner");
        clientButton.setSelected(true);

        group = new ButtonGroup();
        group.add(clientButton);
        group.add(ownerButton);

        clientButton.addActionListener(e -> updateFields());
        ownerButton.addActionListener(e -> updateFields());

        //Ensures the window will display only the selected user type
        gbc.gridx = 1;
        panel.add(clientButton, gbc);
        gbc.gridx = 2;
        panel.add(ownerButton, gbc);

        //Allows for only appropriate attributes are assigned to the user type. 
        addField("Client ID:", gbc, 1, true);
        addField("Job ID:", gbc, 2, true);
        addField("Job Duration (hrs):", gbc, 3, true);
        addField("Job Deadline:", gbc, 4, true);
        addField("Owner ID:", gbc, 5, false);
        addField("Vehicle Information:", gbc, 6, false);
        addField("Residency Time (hrs):", gbc, 7, false);

        submitButton = new JButton("Submit");
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(submitButton, gbc);

        submitButton.addActionListener((ActionEvent e) -> saveData());

        //Creates UI and has a set size to fit more clean looking format
        frame.add(panel);
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        updateFields();
    }

    //Creates and allows for inputs within a box that allows for multiple entries at a time
    private void addField(String labelText, GridBagConstraints gbc, int yPos, boolean isClientField) {
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.gridwidth = 1;
        JLabel label = new JLabel(labelText);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        JTextArea textArea = new JTextArea(3, 25);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, gbc);

        switch(labelText){
            case "Client ID:": clientIdField = textArea; break;
            case "Job ID:": jobIdField = textArea; break;
            case "Job Duration (hrs):": jobDurationField = textArea; break;
            case "Job Deadline:": jobDeadlineField = textArea; break;
            case "Owner ID:": ownerIdField = textArea; break;
            case "Vehicle Information:": vehicleInfoField = textArea; break;
            case "Residency Time (hrs):": residencyTimeField = textArea; break;
        }

        label.setVisible(isClientField == clientButton.isSelected());
        scrollPane.setVisible(isClientField == clientButton.isSelected());
    }
    //Only the fields assigned to the specific job type are shown and ensures it remains consistent
    private void updateFields(){
        boolean isClient = clientButton.isSelected();

        for(Component comp : panel.getComponents()){
        if(comp instanceof JLabel){
           String text = ((JLabel) comp).getText();
           boolean isClientField = text.equals("Client ID:") || text.equals("Job ID:") || text.equals("Job Duration (hrs):") || text.equals("Job Deadline:");
           comp.setVisible(isClient == isClientField);
            } 
        else if(comp instanceof JScrollPane){
          JTextArea textArea = (JTextArea) ((JScrollPane) comp).getViewport().getView();
           boolean isClientField = textArea == clientIdField || textArea == jobIdField || textArea == jobDurationField || textArea == jobDeadlineField;
            comp.setVisible(isClient == isClientField);
            }
        }

        panel.revalidate();
        panel.repaint();
    }
    //Allows for updates to be done immediately by date in which it was input.
    private void saveData(){
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String userType = clientButton.isSelected() ? "Client" : "Owner";
        String data = timestamp + ", " + userType + ", " +
                      (clientButton.isSelected() ? clientIdField.getText() + ", " + jobIdField.getText() + ", " + jobDurationField.getText() + ", " + jobDeadlineField.getText()
                                                : ownerIdField.getText() + ", " + vehicleInfoField.getText() + ", " + residencyTimeField.getText());

        try(PrintStream output = new PrintStream(new File("vehicular_cloud_data.txt"))){
            output.println(data);
            JOptionPane.showMessageDialog(frame, "Information saved successfully!");
        } 
        catch(FileNotFoundException ex){
            JOptionPane.showMessageDialog(frame, "Error saving data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VehicularCloudConsole::new);
    }
}
