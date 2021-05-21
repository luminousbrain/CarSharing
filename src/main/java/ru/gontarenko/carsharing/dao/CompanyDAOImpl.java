package ru.gontarenko.carsharing.dao;

import ru.gontarenko.carsharing.entities.Company;
import ru.gontarenko.carsharing.util.H2Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyDAOImpl implements CompanyDAO {
    private final String createTable = "CREATE TABLE IF NOT EXISTS COMPANY (" +
            "id INT NOT NULL AUTO_INCREMENT," +
            "name VARCHAR(255) UNIQUE NOT NULL," +
            "PRIMARY KEY (id)" +
            ");";

    private final String selectAllCompany = "SELECT * FROM COMPANY;";
    private final String selectById = "SELECT * FROM COMPANY WHERE id = ?;";
    private final String insertCompany = "INSERT INTO COMPANY(name) VALUES (?);";

    public CompanyDAOImpl() {
        // init table
        try (Connection connection = H2Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(createTable)) {
//            connection.setAutoCommit(true);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Company> findAll() {
        List<Company> list = new ArrayList<>();
        try (Connection connection = H2Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectAllCompany)
        ) {
//            connection.setAutoCommit(true);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(new Company(
                            resultSet.getInt("id"),
                            resultSet.getString("name")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean save(Company company) {
        try (Connection connection = H2Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertCompany)) {
//            connection.setAutoCommit(true);
            preparedStatement.setString(1, company.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
//            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Optional<Company> findById(int companyId) {
        try (Connection connection = H2Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectById)) {
//            connection.setAutoCommit(true);
            preparedStatement.setInt(1, companyId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(
                            new Company(
                                    resultSet.getInt("id"),
                                    resultSet.getString("name")
                            ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
