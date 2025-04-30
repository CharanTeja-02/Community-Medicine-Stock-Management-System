import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ShopsManager extends JFrame {
    private JTextField idField, nameField, locationField;
    private JButton addButton, updateButton, deleteButton, searchButton;
    private Connection conn;

    public ShopsManager() {
        setTitle("Shops Manager");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        conn = DBConnection.getConnection();
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Shop ID:"));
        idField = new JTextField();
        add(idField);

        add(new JLabel("Shop Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Location:"));
        locationField = new JTextField();
        add(locationField);

        addButton = new JButton("Add");
        addButton.addActionListener(e -> addShop());
        add(addButton);

        updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateShop());
        add(updateButton);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteShop());
        add(deleteButton);

        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchShop());
        add(searchButton);
    }

    private void addShop() {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Shops VALUES (?, ?, ?)");
            stmt.setInt(1, Integer.parseInt(idField.getText()));
            stmt.setString(2, nameField.getText());
            stmt.setString(3, locationField.getText());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Shop added.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateShop() {
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE Shops SET shop_name=?, location=? WHERE shop_id=?");
            stmt.setString(1, nameField.getText());
            stmt.setString(2, locationField.getText());
            stmt.setInt(3, Integer.parseInt(idField.getText()));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Shop updated.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteShop() {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Shops WHERE shop_id=?");
            stmt.setInt(1, Integer.parseInt(idField.getText()));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Shop deleted.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void searchShop() {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Shops WHERE shop_id=?");
            stmt.setInt(1, Integer.parseInt(idField.getText()));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nameField.setText(rs.getString("shop_name"));
                locationField.setText(rs.getString("location"));
            } else {
                JOptionPane.showMessageDialog(this, "Shop not found.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
