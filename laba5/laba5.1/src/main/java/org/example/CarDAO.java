package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {
    private final Connection connection;

    public CarDAO(Connection connection) {
        this.connection = connection;
    }


    public List<Car> getAll() throws SQLException {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM car";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Car car = new Car(resultSet.getInt("id"),
                        resultSet.getString("make"),
                        resultSet.getString("model"),
                        resultSet.getInt("year"),
                        resultSet.getString("fuel_type"),
                        resultSet.getString("body_type"),
                        resultSet.getString("color"),
                        resultSet.getString("license_plate"),
                        resultSet.getInt("passenger_capacity"));
                cars.add(car);
            }
        }
        return cars;
    }

    public void update(Car car) throws SQLException {
        String sql = "UPDATE car SET make=?, model=?, year=?, fuel_type=?, body_type=?, color=?, license_plate=?, passenger_capacity=?, WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, car.getMake());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.setInt(3, car.getYear());
            preparedStatement.setString(4, car.getFuelType());
            preparedStatement.setString(5, car.getBodyType());
            preparedStatement.setString(6, car.getColor());
            preparedStatement.setString(7, car.getLicensePlate());
            preparedStatement.setInt(8, car.getPassengerCapacity());
            preparedStatement.setInt(10, car.getId());
            preparedStatement.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM car WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

// Метод для вставки машини в базу даних
    public static void insertAuto(Connection connection, Car auto) throws SQLException {
        String sql = "INSERT INTO car (make, model, year, fuel_type, body_type, color, license_plate, passenger_capacity) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
           preparedStatement.setString(1, auto.getMake());
            preparedStatement.setString(2, auto.getModel());
            preparedStatement.setInt(3, auto.getYear());
            preparedStatement.setString(4, auto.getFuelType());
            preparedStatement.setString(5, auto.getBodyType());
            preparedStatement.setString(6, auto.getColor());
            preparedStatement.setString(7, auto.getLicensePlate());
            preparedStatement.setInt(8, auto.getPassengerCapacity());
            preparedStatement.executeUpdate();
        }
    }


    public void sortCarsByDistance(Connection connection) throws SQLException {
        String sql = "SELECT car.make, car.model, norder.distance " +
                "FROM car LEFT JOIN norder ON car.id = norder.id " +
                "ORDER BY distance DESC";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            System.out.println("Cars sorted by total distance:");
            while (resultSet.next()) {
                String make = resultSet.getString("make");
                String model = resultSet.getString("model");
                double Distance = resultSet.getDouble("distance");
                System.out.println("Car: " + make + " " + model + ", Total Distance: " + Distance);
            }
        }
    }
    // Реєстрація клієнта з хешованим паролем
    public static void registerClient(Connection connection, Client client) throws SQLException {
        String hashedPassword = PasswordUtil.hashPassword(client.getPassword()); // Хешування пароля
        String sql = "INSERT INTO client (full_name, birth_date, phone_number, password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, client.getFullName());
            preparedStatement.setDate(2, Date.valueOf(client.getBirthDate()));
            preparedStatement.setString(3, client.getPhoneNumber());
            preparedStatement.setString(4, hashedPassword);
            preparedStatement.executeUpdate();
        }
    }

    // Авторизація клієнта
    public static boolean authenticateClient(Connection connection, String fullName, String password) throws SQLException {
        String sql = "SELECT password FROM client WHERE full_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, fullName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                String hashedPassword = PasswordUtil.hashPassword(password);
                return storedPassword.equals(hashedPassword); // Перевірка пароля
            }
        }
        return false;
    }
}