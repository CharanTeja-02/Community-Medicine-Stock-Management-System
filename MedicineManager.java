import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class MedicineManager extends JFrame {
    private JTextField idField, nameField, expiryField, quantityField;
    private JButton addButton, updateButton, deleteButton, searchButton;
    private Connection conn;

    public MedicineManager() {
        setTitle("Medicine Manager");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        connectDB();
    }

    private void initComponents() {
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Medicine ID:"));
        idField = new JTextField();
        add(idField);

        add(new JLabel("Medicine Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Expiry Date (YYYY-MM-DD):"));
        expiryField = new JTextField();
        add(expiryField);

        add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        add(quantityField);

        addButton = new JButton("Add");
        addButton.addActionListener(e -> addMedicine());
        add(addButton);

        updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateMedicine());
        add(updateButton);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteMedicine());
        add(deleteButton);

        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchMedicine());
        add(searchButton);
    }

    private void connectDB() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/charan", "root", "Charan#206#");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Connection Failed: " + ex.getMessage());
        }
    }

    private void addMedicine() {
        try {
            String sql = "INSERT INTO Medicines VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(idField.getText()));
            stmt.setString(2, nameField.getText());
            stmt.setDate(3, Date.valueOf(expiryField.getText()));
            stmt.setInt(4, Integer.parseInt(quantityField.getText()));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Medicine added.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Add Failed: " + ex.getMessage());
        }
    }

    private void updateMedicine() {
        try {
            String sql = "UPDATE Medicines SET med_name=?, expiry_date=?, quantity=? WHERE med_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nameField.getText());
            stmt.setDate(2, Date.valueOf(expiryField.getText()));
            stmt.setInt(3, Integer.parseInt(quantityField.getText()));
            stmt.setInt(4, Integer.parseInt(idField.getText()));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Medicine updated.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Update Failed: " + ex.getMessage());
        }
    }

    private void deleteMedicine() {
        try {
            String sql = "DELETE FROM Medicines WHERE med_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(idField.getText()));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Medicine deleted.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Delete Failed: " + ex.getMessage());
        }
    }

    private void searchMedicine() {
        try {
            String sql = "SELECT * FROM Medicines WHERE med_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(idField.getText()));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nameField.setText(rs.getString("med_name"));
                expiryField.setText(rs.getDate("expiry_date").toString());
                quantityField.setText(String.valueOf(rs.getInt("quantity")));
            } else {
                JOptionPane.showMessageDialog(this, "Medicine not found.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Search Failed: " + ex.getMessage());
        }
    }


}
