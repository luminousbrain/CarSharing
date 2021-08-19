package ru.gontarenko.carsharing.app;

import ru.gontarenko.carsharing.dao.CarDAO;
import ru.gontarenko.carsharing.entity.Car;
import ru.gontarenko.carsharing.entity.Company;
import ru.gontarenko.carsharing.util.UserInput;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class CompanyService {
    private final CarDAO carDAO;
    private Company currentCompany;

    public CompanyService(CarDAO carDAO) {
        this.carDAO = carDAO;
    }

    public void menu(Company company) {
        currentCompany = company;
        while (true) {
            System.out.println("\n1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");
            switch (UserInput.getChoiceAsString()) {
                case "1":
                    showCarList();
                    break;
                case "2":
                    createCar();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Wrong Input!");
            }
        }
    }

    private void showCarList() {
        List<Car> carList = carDAO.findAllByCompanyId(currentCompany.getId());
        if (carList.isEmpty()) {
            System.out.println("\nThe car list is empty!");
        } else {
            System.out.println("\nCar list:");
            AtomicInteger atomicInteger = new AtomicInteger(1);
            carList.forEach(x -> System.out.println(atomicInteger.getAndIncrement() + ". " + x.getName()));
        }
    }

    private void createCar() {
        System.out.println("\nEnter the car name:");
        String carName = UserInput.getChoiceAsString();
        if (carDAO.save(new Car(carName, currentCompany.getId()))) {
            System.out.println("The car was added!");
        } else {
            System.out.println("Error. Car not added!");
        }
    }
}
