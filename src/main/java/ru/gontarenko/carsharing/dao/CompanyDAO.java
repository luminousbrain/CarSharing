package ru.gontarenko.carsharing.dao;

import ru.gontarenko.carsharing.entities.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyDAO {
    boolean save(Company company);
    Optional<Company> findById(int companyId);
    List<Company> findAll();
}
