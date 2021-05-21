package ru.gontarenko.carsharing.dao;

import ru.gontarenko.carsharing.entities.Company;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CompanyDAO {
    List<Company> findAll();
    boolean save(Company company);
    Optional<Company> findById(int companyId);
}
