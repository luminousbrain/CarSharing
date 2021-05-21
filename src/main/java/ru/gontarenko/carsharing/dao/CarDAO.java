package ru.gontarenko.carsharing.dao;

import ru.gontarenko.carsharing.entities.Car;

import java.util.List;

public interface CarDAO {
    List<Car> findAll();
    boolean save(Car car);
    List<Car> findAllByCompanyId(int id);
}
