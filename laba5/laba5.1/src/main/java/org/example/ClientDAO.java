package org.example;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ClientDAO {

    // Метод для хешування паролю
    static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // Метод для реєстрації клієнта з хешуванням паролю
    public void registerClient(Connection connection, Client client) throws SQLException {
        String sql = "INSERT INTO client (full_name, birth_date, phone_number, password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, client.getFullName());
            preparedStatement.setDate(2, Date.valueOf(client.getBirthDate()));
            preparedStatement.setString(3, client.getPhoneNumber());
            preparedStatement.setString(4, hashPassword(client.getPassword()));  // Хешуємо пароль і зберігаємо
            preparedStatement.executeUpdate();
        }
    }


    public boolean authorizeClient(Connection connection, String phoneNumber, String password) throws SQLException {
        String sql = "SELECT password FROM client WHERE phone_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, phoneNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String storedHash = resultSet.getString("password");
                    String inputHash = hashPassword(password);  // Хешуємо введений пароль
                    return storedHash.equals(inputHash);  // Перевірка хешу
                } else {
                    return false;  // Клієнт з таким номером телефону не знайдений
                }
            }
        }
    }

//    public boolean authorizeClient(Connection connection, String phoneNumber, String password) throws SQLException {
//        String sql = "SELECT password FROM client WHERE phone_number = ?";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(1, phoneNumber);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                String storedPassword = resultSet.getString("password");
//                // Пряме порівняння пароля без хешування
//                return storedPassword.equals(password);
//            }
//        }
//        return false;
//    }
//    public void registerClient(Connection connection, Client client) throws SQLException {
//        String sql = "INSERT INTO client (full_name, birth_date, phone_number, password) VALUES (?, ?, ?, ?)";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(1, client.getFullName());
//            preparedStatement.setDate(2, Date.valueOf(client.getBirthDate()));
//            preparedStatement.setString(3, client.getPhoneNumber());
//            preparedStatement.setString(4, client.getPassword()); // Використання незахищеного пароля
//            preparedStatement.executeUpdate();
//        }
//    }

    public void insertClient(Connection connection, Client client) throws SQLException {
        String sql = "INSERT INTO client (full_name, birth_date, phone_number) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, client.getFullName());
            preparedStatement.setDate(2, Date.valueOf(client.getBirthDate()));
            preparedStatement.setString(3, client.getPhoneNumber());
            preparedStatement.executeUpdate();
        }
    }

    public Client getClientById(Connection connection, int clientId) throws SQLException {
        String sql = "SELECT * FROM client WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, clientId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String fullName = resultSet.getString("full_name");
                    Date birthDate = resultSet.getDate("birth_date");
                    String phoneNumber = resultSet.getString("phone_number");
                    String password = resultSet.getString("password"); // Отримайте пароль з бази даних
                    return new Client(clientId, fullName, birthDate.toLocalDate(), phoneNumber, password); // Додайте пароль
                } else {
                    return null;
                }
            }
        }
    }


    public void sortClientsByFrequency(Connection connection) throws SQLException {
        String sql = "SELECT client.full_name, COUNT(norder.client_id) AS frequency " +
                "FROM client LEFT JOIN norder ON client.id = norder.client_id " +
                "GROUP BY client.id ORDER BY frequency DESC";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            System.out.println("Clients sorted by frequency:");
            while (resultSet.next()) {
                String fullName = resultSet.getString("full_name");
                int frequency = resultSet.getInt("frequency");
                System.out.println("Client: " + fullName + ", Frequency: " + frequency);
            }
        }
    }
}
