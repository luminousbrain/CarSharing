package ru.gontarenko.carsharing.dao;

import ru.gontarenko.carsharing.entities.Car;

import java.util.List;
import java.util.Optional;

public interface CarDAO {
    boolean save(Car car);
    Optional<Car> findById(int rentedCarId);
    List<Car> findAll();
    List<Car> findAllByCompanyId(int id);
    List<Car> findAllNotRentedCarByCompanyId(int companyId);
}
