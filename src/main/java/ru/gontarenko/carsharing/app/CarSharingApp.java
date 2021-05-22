package ru.gontarenko.carsharing.app;

import ru.gontarenko.carsharing.dao.CarDAO;
import ru.gontarenko.carsharing.dao.CompanyDAO;
import ru.gontarenko.carsharing.dao.CustomerDAO;
import ru.gontarenko.carsharing.dao.impl.CarDAOImpl;
import ru.gontarenko.carsharing.dao.impl.CompanyDAOImpl;
import ru.gontarenko.carsharing.dao.impl.CustomerDAOImpl;
import ru.gontarenko.carsharing.util.UserInput;

public final class CarSharingApp {
    private final CompanyManager companyManager;
    private final CustomerManager customerManager;

    public CarSharingApp() {
        CompanyDAO companyDAO = new CompanyDAOImpl();
        CarDAO carDAO = new CarDAOImpl();
        CustomerDAO customerDAO = new CustomerDAOImpl();
        this.companyManager = new CompanyManager(companyDAO, carDAO);
        this.customerManager = new CustomerManager(customerDAO, carDAO, companyDAO);
    }

    public void run() {
        while (true) {
            System.out.println("\n1. Log in as a manager");
            System.out.println("2. Log in as a customer");
            System.out.println("3. Create a customer");
            System.out.println("0. Exit");
            switch (UserInput.getChoiceAsString()) {
                case "1":
                    companyManager.logIn();
                    break;
                case "2":
                    customerManager.logIn();
                    break;
                case "3":
                    customerManager.createNew();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("\nWrong input! Try again:");
            }
        }
    }
}
