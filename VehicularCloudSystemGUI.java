import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class git {
    public static void main(String[] args) {
        new VehicularCloudFrame();
    }
}

class VehicularCloudFrame extends JFrame implements ActionListener {
    private JRadioButton ownerButton, clientButton;
    private JPanel inputPanel;
    private JTextField ownerIdField, vehicleInfoField, residencyTimeField;
    private JTextField clientIdField, jobDurationField, jobDeadlineField;
    private JButton submitButton;
    
    // ---------------------------------------------------------------
    // Constructor: Sets up the GUI components and event listeners.
    public VehicularCloudFrame() {
        setTitle("Vehicular Cloud Console");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel rolePanel = new JPanel();
        ownerButton = new JRadioButton("Owner");
        clientButton = new JRadioButton("Client");
        ButtonGroup group = new ButtonGroup();
        group.add(ownerButton);
        group.add(clientButton);
        rolePanel.add(ownerButton);
        rolePanel.add(clientButton);
        add(rolePanel, BorderLayout.NORTH);
        
        inputPanel = new JPanel(new GridLayout(4, 2));
        add(inputPanel, BorderLayout.CENTER);
        
        submitButton = new JButton("Submit");
        add(submitButton, BorderLayout.SOUTH);
        
        ownerButton.addActionListener(this);
        clientButton.addActionListener(this);
        submitButton.addActionListener(this);
        
        setVisible(true);
    }
    
    // ---------------------------------------------------------------
    // This method handles button click events.
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ownerButton) {
            showOwnerFields();
        } else if (e.getSource() == clientButton) {
            showClientFields();
        } else if (e.getSource() == submitButton) {
            saveData();
        }
    }
    
    // ---------------------------------------------------------------
    // This method displays the input fields for an Owner.
    private void showOwnerFields() {
        inputPanel.removeAll();
        inputPanel.add(new JLabel("Owner ID:"));
        ownerIdField = new JTextField();
        inputPanel.add(ownerIdField);
        
        inputPanel.add(new JLabel("Vehicle Info:"));
        vehicleInfoField = new JTextField();
        inputPanel.add(vehicleInfoField);
        
        inputPanel.add(new JLabel("Residency Time (hrs):"));
        residencyTimeField = new JTextField();
        inputPanel.add(residencyTimeField);
        
        inputPanel.revalidate();
        inputPanel.repaint();
    }
    
    // ---------------------------------------------------------------
    // This method displays the input fields for a Client.
    private void showClientFields() {
        inputPanel.removeAll();
        inputPanel.add(new JLabel("Client ID:"));
        clientIdField = new JTextField();
        inputPanel.add(clientIdField);
        
        inputPanel.add(new JLabel("Job Duration (hrs):"));
        jobDurationField = new JTextField();
        inputPanel.add(jobDurationField);
        
        inputPanel.add(new JLabel("Job Deadline:"));
        jobDeadlineField = new JTextField();
        inputPanel.add(jobDeadlineField);
        
        inputPanel.revalidate();
        inputPanel.repaint();
    }
    
    // ---------------------------------------------------------------
    // This method saves the collected data to a file with a timestamp.
    private void saveData() {
        String data;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String timestamp = dtf.format(LocalDateTime.now());
        
        if (ownerButton.isSelected()) {
            data = "Owner," + ownerIdField.getText() + "," + vehicleInfoField.getText() + "," + residencyTimeField.getText() + "," + timestamp;
        } else if (clientButton.isSelected()) {
            data = "Client," + clientIdField.getText() + "," + jobDurationField.getText() + "," + jobDeadlineField.getText() + "," + timestamp;
        } else {
            JOptionPane.showMessageDialog(this, "Please select a role.");
            return;
        }
        
        try (FileWriter writer = new FileWriter("vehicular_cloud_data.txt", true)) {
            writer.write(data + "\n");
            JOptionPane.showMessageDialog(this, "Data saved successfully.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving data.");
        }
    }
}
