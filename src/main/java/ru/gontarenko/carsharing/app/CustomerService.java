package ru.gontarenko.carsharing.app;

import ru.gontarenko.carsharing.dao.CarDAO;
import ru.gontarenko.carsharing.dao.CompanyDAO;
import ru.gontarenko.carsharing.dao.CustomerDAO;
import ru.gontarenko.carsharing.entities.Car;
import ru.gontarenko.carsharing.entities.Company;
import ru.gontarenko.carsharing.entities.Customer;
import ru.gontarenko.carsharing.util.UserInput;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerService {
    private final CarDAO carDAO;
    private final CustomerDAO customerDAO;
    private final CompanyDAO companyDAO;
    private Customer currentCustomer;

    public CustomerService(CarDAO carDAO, CustomerDAO customerDAO, CompanyDAO companyDAO) {
        this.carDAO = carDAO;
        this.customerDAO = customerDAO;
        this.companyDAO = companyDAO;
    }

    public void menu(Customer customer) {
        currentCustomer = customer;
        while (true) {
            System.out.println("\n1. Rent a car");
            System.out.println("2. Return a rented car");
            System.out.println("3. My rented car");
            System.out.println("0. Back");
            switch (UserInput.getChoiceAsString()) {
                case "1":
                    rentCar();
                    break;
                case "2":
                    returnRentedCar();
                    break;
                case "3":
                    showRentedCar();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Wrong input!");
            }
        }
    }

    private void rentCar() {
        if (currentCustomer.getRentedCarId() == null) {
            List<Company> companyList = companyDAO.findAll();
            if (companyList.isEmpty()) {
                System.out.println("\nThe company list is empty!");
            } else {
                System.out.println("\nChoose a company:");
                AtomicInteger i = new AtomicInteger(1);
                companyList.forEach(x -> System.out.println(i.getAndIncrement() + ". " + x.getName()));
                System.out.println("0. Back");

                Company company;
                try {
                    int userInput = UserInput.getChoiceAsInteger();
                    if (userInput == 0) {
                        return;
                    }
                    company = companyList.get(userInput - 1);

                    List<Car> carList = carDAO.findAllNotRentedCarByCompanyId(company.getId());
                    if (!carList.isEmpty()) {
                        System.out.println("\nChoose a car:");
                        AtomicInteger i2 = new AtomicInteger(1);
                        carList.forEach(x -> System.out.println(i2.getAndIncrement() + ". " + x.getName()));
                        System.out.println("0. Back");

                        userInput = UserInput.getChoiceAsInteger();
                        if (userInput == 0) {
                            return;
                        }
                        Car car = carList.get(userInput - 1);
                        currentCustomer.setRentedCarId(car.getId());
                        customerDAO.update(currentCustomer);
                        System.out.println(String.format("\nYou rented '%s'", car.getName()));
                    } else {
                        System.out.println(String.format("\nNo available cars in the '%s' company", company.getName()));
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\nWrong Input");
                }
            }
        } else {
            System.out.println("\nYou've already rented a car!");
        }
    }

    private void returnRentedCar() {
        if (currentCustomer.getRentedCarId() == null) {
            System.out.println("\nYou didn't rent a car!");
        } else {
            currentCustomer.setRentedCarId(null);
            customerDAO.update(currentCustomer);
            System.out.println("\nYou've returned a rented car!");
        }
    }

    private void showRentedCar() {
        if (currentCustomer.getRentedCarId() != null) {
            Optional<Car> byId = carDAO.findById(currentCustomer.getRentedCarId());
            Car car = byId.get();
            System.out.println("\nYour rented car:");
            System.out.println(car.getName());
            System.out.println("Company:");
            Optional<Company> company = companyDAO.findById(car.getCompanyId());
            company.ifPresent(x -> System.out.println(x.getName()));
        } else {
            System.out.println("\nYou didn't rent a car!");
        }
    }
}
