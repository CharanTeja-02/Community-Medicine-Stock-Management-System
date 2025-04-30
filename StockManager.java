import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class StockManager extends JFrame {
    private JTextField shopIdField, medIdField, quantityField;
    private JButton addButton, updateButton, deleteButton, searchButton;
    private Connection conn;

    public StockManager() {
        setTitle("Stock Manager");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        conn = DBConnection.getConnection();
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Shop ID:"));
        shopIdField = new JTextField();
        add(shopIdField);

        add(new JLabel("Medicine ID:"));
        medIdField = new JTextField();
        add(medIdField);

        add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        add(quantityField);

        addButton = new JButton("Add");
        addButton.addActionListener(e -> addStock());
        add(addButton);

        updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateStock());
        add(updateButton);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteStock());
        add(deleteButton);

        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchStock());
        add(searchButton);
    }

    private void addStock() {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Stock VALUES (?, ?, ?)");
            stmt.setInt(1, Integer.parseInt(shopIdField.getText()));
            stmt.setInt(2, Integer.parseInt(medIdField.getText()));
            stmt.setInt(3, Integer.parseInt(quantityField.getText()));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Stock added.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateStock() {
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE Stock SET quantity=? WHERE shop_id=? AND med_id=?");
            stmt.setInt(1, Integer.parseInt(quantityField.getText()));
            stmt.setInt(2, Integer.parseInt(shopIdField.getText()));
            stmt.setInt(3, Integer.parseInt(medIdField.getText()));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Stock updated.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteStock() {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Stock WHERE shop_id=? AND med_id=?");
            stmt.setInt(1, Integer.parseInt(shopIdField.getText()));
            stmt.setInt(2, Integer.parseInt(medIdField.getText()));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Stock deleted.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void searchStock() {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Stock WHERE shop_id=? AND med_id=?");
            stmt.setInt(1, Integer.parseInt(shopIdField.getText()));
            stmt.setInt(2, Integer.parseInt(medIdField.getText()));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                quantityField.setText(String.valueOf(rs.getInt("quantity")));
            } else {
                JOptionPane.showMessageDialog(this, "Stock not found.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
