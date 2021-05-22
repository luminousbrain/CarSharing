package ru.gontarenko.carsharing.dao.impl;

import ru.gontarenko.carsharing.dao.CustomerDAO;
import ru.gontarenko.carsharing.entities.Customer;
import ru.gontarenko.carsharing.util.H2Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class CustomerDAOImpl implements CustomerDAO {
    private final String createTable = "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
            "id INT NOT NULL AUTO_INCREMENT," +
            "name VARCHAR(255) UNIQUE NOT NULL," +
            "rented_car_id INT DEFAULT NULL," +
            "PRIMARY KEY (id)," +
            "FOREIGN KEY (rented_car_id) REFERENCES CAR(id)" +
            ");";
    private final String selectAll = "SELECT * FROM CUSTOMER;";
    private final String insert = "INSERT INTO CUSTOMER(name, rented_car_id) VALUES (?, ?);";
    private final String update = "UPDATE CUSTOMER SET name = ?, rented_car_id = ? WHERE id = ?;";

    public CustomerDAOImpl() {
        try (Connection connection = H2Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(createTable)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean save(Customer customer) {
        try (Connection connection = H2Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setNull(2,Types.INTEGER);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void update(Customer customer) {
        try (Connection connection = H2Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(update)) {
            preparedStatement.setString(1, customer.getName());
            if (customer.getRentedCarId() == null) {
                preparedStatement.setNull(2, Types.INTEGER);
            } else {
                preparedStatement.setInt(2, customer.getRentedCarId());
            }
            preparedStatement.setInt(3, customer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customerList = new ArrayList<>();
        try (Connection connection = H2Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectAll)
        ) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Integer rented_car_id = resultSet.getInt("rented_car_id");
                    if (resultSet.wasNull()) {
                        rented_car_id = null;
                    }
                    customerList.add(new Customer(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            rented_car_id
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }
}