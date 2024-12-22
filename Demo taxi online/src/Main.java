import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class TaxiBookingApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TaxiBookingApp().createAndShowGUI());
    }

    // Properti Global untuk Kustomisasi
    private Font defaultFont = new Font("Arial", Font.PLAIN, 14); // Font default
    private Color backgroundColor = Color.LIGHT_GRAY; // Warna latar belakang
    private Color textColor = Color.BLACK; // Warna teks

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Taxi Booking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout()); // Layout utama untuk frame

        // Panel untuk Form Input
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout()); // Menggunakan GridBagLayout untuk fleksibilitas
        inputPanel.setBackground(backgroundColor); // Warna latar belakang panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margin antar komponen
        gbc.fill = GridBagConstraints.HORIZONTAL; // Komponen akan mengisi horizontal

        // Komponen Form
        JLabel nameLabel = createLabel("Nama:");
        JTextField nameField = createTextField();

        JLabel phoneLabel = createLabel("No. Telepon:");
        JTextField phoneField = createTextField();

        JLabel pickupLabel = createLabel("Lokasi Penjemputan:");
        JTextField pickupField = createTextField();

        JLabel destinationLabel = createLabel("Tujuan:");
        JTextField destinationField = createTextField();

        JLabel distanceLabel = createLabel("Jarak (km):");
        JTextField distanceField = createTextField();

        JLabel typeLabel = createLabel("Jenis Taksi:");
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Ekonomi", "Reguler", "Premium"});
        typeComboBox.setFont(defaultFont); // Mengatur font dropdown

        // Tambahkan Komponen ke GridBagLayout
        int row = 0;
        gbc.gridx = 0; // Kolom pertama untuk label
        gbc.gridy = row;
        inputPanel.add(nameLabel, gbc);
        gbc.gridx = 1; // Kolom kedua untuk field
        inputPanel.add(nameField, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        inputPanel.add(phoneLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(phoneField, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        inputPanel.add(pickupLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(pickupField, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        inputPanel.add(destinationLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(destinationField, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        inputPanel.add(distanceLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(distanceField, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        inputPanel.add(typeLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(typeComboBox, gbc);

        // Tabel untuk Menyimpan Data
        String[] columnNames = {"Nama", "No. Telepon", "Penjemputan", "Tujuan", "Jarak (km)", "Jenis Taksi", "Total Tarif"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable dataTable = new JTable(tableModel);
        dataTable.setFont(defaultFont);
        dataTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        JScrollPane tableScrollPane = new JScrollPane(dataTable);

        // Panel untuk Menyusun Input dan Tabel di Kiri dan Kanan
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.add(inputPanel);
        mainPanel.add(tableScrollPane);

        // Tombol Submit
        JButton submitButton = new JButton("Pesan");
        submitButton.setFont(defaultFont); // Font tombol
        submitButton.setBackground(Color.DARK_GRAY); // Warna latar belakang tombol
        submitButton.setForeground(Color.WHITE); // Warna teks tombol
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String phone = phoneField.getText().trim();
                String pickup = pickupField.getText().trim();
                String destination = destinationField.getText().trim();
                String distanceText = distanceField.getText().trim();
                String type = (String) typeComboBox.getSelectedItem();

                // Validasi input
                if (name.isEmpty() || phone.isEmpty() || pickup.isEmpty() || destination.isEmpty() || distanceText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Semua harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!phone.matches("\\d+")) {
                    JOptionPane.showMessageDialog(frame, "No. Telepon harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
                    phoneField.setText("");
                } else if (!distanceText.matches("\\d+")) {
                    JOptionPane.showMessageDialog(frame, "Jarak harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
                    distanceField.setText("");
                } else {
                    int distance = Integer.parseInt(distanceText);
                    int rate = switch (type) {
                        case "Ekonomi" -> 100;
                        case "Reguler" -> 500;
                        case "Premium" -> 1000;
                        default -> 0;
                    };
                    int totalFare = distance * rate;

                    // Tambahkan Data ke Tabel
                    tableModel.addRow(new Object[]{name, phone, pickup, destination, distance, type, totalFare});
                }
            }
        });

        // Menambahkan Komponen ke Frame
        frame.add(mainPanel, BorderLayout.CENTER); // Tambahkan panel utama di tengah
        frame.add(submitButton, BorderLayout.SOUTH); // Tambahkan tombol di bawah

        frame.setVisible(true);
    }

    // Helper untuk Membuat Label
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(defaultFont); // Mengatur font label
        label.setForeground(textColor); // Mengatur warna teks label
        return label;
    }

    // Helper untuk Membuat TextField
    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(defaultFont); // Mengatur font text field
        textField.setForeground(textColor); // Mengatur warna teks
        textField.setBackground(Color.WHITE); // Mengatur warna latar belakang
        return textField;
    }
}
