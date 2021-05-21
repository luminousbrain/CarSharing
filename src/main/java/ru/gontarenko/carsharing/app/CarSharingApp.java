package ru.gontarenko.carsharing.app;

import ru.gontarenko.carsharing.dao.CarDAO;
import ru.gontarenko.carsharing.dao.CarDAOImpl;
import ru.gontarenko.carsharing.dao.CompanyDAO;
import ru.gontarenko.carsharing.dao.CompanyDAOImpl;
import ru.gontarenko.carsharing.entities.Car;
import ru.gontarenko.carsharing.entities.Company;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CarSharingApp {
    private final Scanner scanner = new Scanner(System.in);
    private final CompanyDAO companyDAO;
    private final CarDAO carDAO;

    public CarSharingApp() {
        this.companyDAO = new CompanyDAOImpl();
        this.carDAO = new CarDAOImpl();
    }

    public void run() {
        while (true) {
            System.out.println("\n1. Log in as a manager");
            System.out.println("0. Exit");
            switch (scanner.nextLine()) {
                case "1":
                    managerMenu();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Wrong Input!");
            }
        }
    }

    private void managerMenu() {
        while (true) {
            System.out.println("\n1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");
            switch (scanner.nextLine()) {
                case "1":
                    showCompanyList();
                    break;
                case "2":
                    createNewCompany();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Wrong Input!");
            }
        }
    }

    private void createNewCompany() {
        System.out.println("\nEnter the company name:");
        String companyName = scanner.nextLine();
        if (companyDAO.save(new Company(companyName))) {
            System.out.println("The company was created!");
        } else {
            System.out.println("Error! Company not created!");
        }
    }

    private void showCompanyList() {
        List<Company> list = companyDAO.findAll();
        if (list.isEmpty()) {
            System.out.println("\nThe company list is empty!");
        } else {
            System.out.println("\nChoose a company:");
            list.forEach(System.out::println);
            System.out.println("0. Back");
            Company company;
            try {
                int companyId = Integer.parseInt(scanner.nextLine());
                if (companyId == 0) {
                    return;
                }
                Optional<Company> byId = companyDAO.findById(companyId);
                if (byId.isEmpty()) {
                    throw new Exception("Company not found!");
                }
                company = byId.get();
            } catch (Exception e){
                System.out.println(e.getMessage());
                return;
            }
            companyMenu(company);
        }
    }

    private void companyMenu(Company company) {
        while (true) {
            System.out.println("\n1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");
            switch (scanner.nextLine()) {
                case "1":
                    showCarList(company);
                    break;
                case "2":
                    createCar(company);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Wrong Input!");
            }
        }
    }



    private void showCarList(Company company) {
        List<Car> carList = carDAO.findAllByCompanyId(company.getId());
        if (carList.isEmpty()) {
            System.out.println("The car list is empty!");
        } else {
            System.out.println("Car list:");
            carList.forEach(System.out::println);
        }
    }
    private void createCar(Company company) {
        System.out.println("\nEnter the car name:");
        final String carName = scanner.nextLine();
        if (carDAO.save(new Car(carName, company.getId()))) {
            System.out.println("The car was added!");
        } else {
            System.out.println("Error. Car not added!");
        }
    }
}
