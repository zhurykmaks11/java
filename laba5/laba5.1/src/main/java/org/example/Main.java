package org.example;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String user = "user";
        String password = "123456";

//        try (Connection connection = DriverManager.getConnction(url, user, password)) {
//            ClientDAO clientDAO = new ClientDAO();
//
//
//            Client client2 = new Client(30, "oleg martunyk", LocalDate.of(2000, 5, 14), "+380981266882", "1450575333");
//            clientDAO.registerClient(connection, client2);
//
//
//        } catch (SQLException e) {
//            System.out.println("SQL Error: " + e.getMessage());
//            e.printStackTrace();
//        }





        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            CarDAO carDAO = new CarDAO(connection);
            DriverDAO driverDAO = new DriverDAO(connection);
            ClientDAO clientDAO = new ClientDAO(); // Якщо ClientDAO також має конструктор з Connection

            OrderDAO orderDAO = new OrderDAO(connection);

            // Авторизація клієнта
            String inputPhoneNumber = "+380981266882";  // Номер телефону для авторизації
            String inputPasswordHash = "1450575333";  // Хеш паролю

            boolean isAuthorized = clientDAO.authorizeClient(connection, inputPhoneNumber, inputPasswordHash);

            if (isAuthorized) {
                System.out.println("Authorization successful!");

                // Додаємо нове авто
                Car auto = new Car(1, "Toyota", "Corolla", 2020, "Petrol", "Sedan", "White", "ABC-123", 5);
                carDAO.insertAuto(connection, auto);

                // Додаємо нового водія
                Driver driver = new Driver(1, "Ivan Ivanov", LocalDate.of(1985, 1, 15), 10, "0501234567");
                driverDAO.insertDriver(connection, driver);

                // Отримуємо пароль від користувача
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter password: ");
                String clientPassword = scanner.nextLine(); // Читання пароля з консолі

// Створюємо клієнта з введеним паролем
                Client client = new Client(1, "oleg borodach", LocalDate.of(1956, 2, 20), "0507654358", "s3e5651");
                // Використовуйте введений пароль

// Реєстрація клієнта (не забудьте зберегти хешований пароль у базі даних)
                clientDAO.registerClient(connection, client);
                // Додаємо нове замовленняpassword
                Order order = new Order(1, 12.5, 28, 1);
                orderDAO.insertOrder(connection, order);

                // Виводимо всі замовлення
                List<Order> orders = orderDAO.getAllOrders();
                for (Order o : orders) {
                    System.out.println("Order ID: " + o.getId() + ", Distance: " + o.getDistance() +
                            ", Driver ID: " + o.getDriverId() + ", Client ID: " + o.getClientId());
                }

                // Сортуємо авто за дистанцією
                carDAO.sortCarsByDistance(connection);

                // Сортуємо клієнтів за частотою
                clientDAO.sortClientsByFrequency(connection);

                // Пошук водія за ID
                Driver foundDriver = driverDAO.getDriverById(connection, 1);
                if (foundDriver != null) {
                    System.out.println("Found Driver: " + foundDriver.getFullName());
                }

                // Пошук клієнта за ID
                Client foundClient = clientDAO.getClientById(connection, 1);
                if (foundClient != null) {
                    System.out.println("Found Client: " + foundClient.getFullName());
                }

            } else {
                System.out.println("Authorization failed. Incorrect phone number or password.");
            }

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
