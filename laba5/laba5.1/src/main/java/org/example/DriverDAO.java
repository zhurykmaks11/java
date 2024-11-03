package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class DriverDAO {
    private Connection connection;

    public DriverDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Driver> getAll() throws SQLException {
        List<Driver> drivers = new ArrayList<>();
        String sql = "SELECT * FROM Driver";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Driver driver = new Driver(resultSet.getInt("id"),
                        resultSet.getString("full_name"),
                        resultSet.getDate("birth_date").toLocalDate(),
                        resultSet.getInt("driving_experience"),
                        resultSet.getString("phone_number"));
                drivers.add(driver);
            }
        }
        return drivers;
    }


    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM driver WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
    public void insertDriver(Connection connection, Driver driver) throws SQLException {
        String sql = "INSERT INTO driver (full_name, birth_date, driving_experience, phone_number) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, driver.getFullName());
            preparedStatement.setDate(2, Date.valueOf(driver.getBirthDate()));
            preparedStatement.setInt(3, driver.getDrivingExperience());
            preparedStatement.setString(4, driver.getPhoneNumber());
            preparedStatement.executeUpdate();
        }
    }

    public Driver getDriverById(Connection connection, int driverId) throws SQLException {
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
