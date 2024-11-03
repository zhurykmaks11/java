package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class OrderDAO {
    private Connection connection;

    public OrderDAO(Connection connection) {
        this.connection = connection;
    }

    public static void insertOrder(Connection connection, Order order) throws SQLException {
        String sql = "INSERT INTO `norder` (distance, driver_id, client_id) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, order.getDistance());
            preparedStatement.setInt(2, order.getDriverId());
            preparedStatement.setInt(3, order.getClientId());
            preparedStatement.executeUpdate();
        }
    }



    // Метод для отримання всіх замовлень
    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM norder";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                double distance = resultSet.getDouble("distance");
                int driverId = resultSet.getInt("driver_id");
                int clientId = resultSet.getInt("client_id");
                orders.add(new Order(id, distance, driverId, clientId));
            }
        }
        return orders;
    }
    // Отримати водія за ID
    private Driver getDriverById(int driverId) throws SQLException {
        String sql = "SELECT * FROM driver WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, driverId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Driver(
                        resultSet.getInt("id"),
                        resultSet.getString("full_name"),
                        resultSet.getDate("birth_date").toLocalDate(),
                        resultSet.getInt("driving_experience"),
                        resultSet.getString("phone_number")
                );
            }
        }
        return null;
    }


}
