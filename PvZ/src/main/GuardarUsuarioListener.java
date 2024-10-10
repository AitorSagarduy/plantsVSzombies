package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GuardarUsuarioListener implements ActionListener {

    private JTextField usuario;

    public GuardarUsuarioListener(JTextField usuario) {
        this.usuario = usuario;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usuario.getText();
        if (!username.isEmpty()) {
            try {
                // Write the username to the file
                java.io.File usernameFile = new java.io.File("username.txt");
                try (PrintWriter writer = new PrintWriter(new FileWriter(usernameFile))) {
                    writer.println(username);
                    JOptionPane.showMessageDialog(null, "Username saved successfully!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error saving username: " + ex.getMessage());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid username.");
        }
    }
}