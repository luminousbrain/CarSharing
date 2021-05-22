package ru.gontarenko.carsharing.dao.impl;

import ru.gontarenko.carsharing.dao.CarDAO;
import ru.gontarenko.carsharing.entities.Car;
import ru.gontarenko.carsharing.util.H2Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CarDAOImpl implements CarDAO {
    private final String createTable = "CREATE TABLE IF NOT EXISTS CAR (" +
            "id INT AUTO_INCREMENT," +
            "name VARCHAR(255) NOT NULL UNIQUE," +
            "company_id INT NOT NULL," +
            "PRIMARY KEY (id)," +
            "FOREIGN KEY (company_id) REFERENCES COMPANY(id)" +
            ");";
    private final String insertNewCar = "INSERT INTO CAR(name, company_id) VALUES (?, ?);";
    private final String selectAll = "SELECT * FROM CAR;";
    private final String selectById = "SELECT * FROM CAR WHERE id = ?;";
    private final String selectAllByCompanyId = "SELECT * FROM CAR WHERE company_id = ?;";
    private final String notRentedCarByCompanyId = "SELECT CAR.id as id, CAR.name as name, CAR.company_id as company_id FROM CAR " +
            "    LEFT JOIN CUSTOMER ON CAR.id = CUSTOMER.rented_car_id " +
            "WHERE company_id = ? AND CUSTOMER.rented_car_id is null;";

    public CarDAOImpl() {
        try (Connection connection = H2Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(createTable)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean save(Car car) {
        try (Connection connection = H2Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertNewCar)) {
            preparedStatement.setString(1, car.getName());
            preparedStatement.setInt(2, car.getCompanyId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Optional<Car> findById(int rentedCarId) {
        try (Connection connection = H2Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectById)
        ) {
            preparedStatement.setInt(1, rentedCarId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new Car(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("company_id")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Car> findAll() {
        List<Car> carList = new ArrayList<>();
        try (Connection connection = H2Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectAll)
        ) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    carList.add(new Car(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("company_id")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carList;
    }

    @Override
    public List<Car> findAllByCompanyId(int id) {
        return getCars(id, selectAllByCompanyId);
    }

    @Override
    public List<Car> findAllNotRentedCarByCompanyId(int companyId) {
        return getCars(companyId, notRentedCarByCompanyId);
    }

    private List<Car> getCars(int id, String selectAllByCompanyId) {
        List<Car> carList = new ArrayList<>();
        try (Connection connection = H2Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectAllByCompanyId)
        ) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    carList.add(new Car(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("company_id")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carList;
    }
}