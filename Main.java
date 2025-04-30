// --- Main.java ---
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Create main window
        JFrame mainFrame = new JFrame("Pharmacy Management System");
        mainFrame.setSize(300, 350);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        // Use a grid layout: 6 rows, 1 column
        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Button for each manager
        JButton medicineBtn = new JButton("Manage Medicines");
        medicineBtn.addActionListener(e -> new MedicineManager().setVisible(true));

        JButton shopBtn = new JButton("Manage Shops");
        shopBtn.addActionListener(e -> new ShopsManager().setVisible(true));

        JButton stockBtn = new JButton("Manage Stock");
        stockBtn.addActionListener(e -> new StockManager().setVisible(true));

        JButton patientBtn = new JButton("Manage Patients");
        patientBtn.addActionListener(e -> new PatientsManager().setVisible(true));

        JButton salesBtn = new JButton("Manage Sales");
        salesBtn.addActionListener(e -> new SalesManager().setVisible(true));

        JButton detailsBtn = new JButton("Manage Sales Details");
        detailsBtn.addActionListener(e -> new SalesDetailsManager().setVisible(true));

        // Add buttons to panel
        panel.add(medicineBtn);
        panel.add(shopBtn);
        panel.add(stockBtn);
        panel.add(patientBtn);
        panel.add(salesBtn);
        panel.add(detailsBtn);

        // Show it all
        mainFrame.add(panel);
        mainFrame.setVisible(true);
    }
}
