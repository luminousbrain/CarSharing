package ru.gontarenko.carsharing.app;

import ru.gontarenko.carsharing.dao.CarDAO;
import ru.gontarenko.carsharing.dao.CompanyDAO;
import ru.gontarenko.carsharing.entities.Company;
import ru.gontarenko.carsharing.util.UserInput;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class CompanyManager {
    private final CompanyDAO companyDAO;
    private final CompanyService companyService;

    public CompanyManager(CompanyDAO companyDAO, CarDAO carDAO) {
        this.companyService = new CompanyService(carDAO);
        this.companyDAO = companyDAO;
    }

    public void logIn() {
        while (true) {
            System.out.println("\n1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");
            switch (UserInput.getChoiceAsString()) {
                case "1":
                    showCompanyList();
                    break;
                case "2":
                    createNewCompany();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("\nWrong input! Try again:");
            }
        }
    }

    private void createNewCompany() {
        System.out.println("\nEnter the company name:");
        String companyName = UserInput.getChoiceAsString();
        if (companyDAO.save(new Company(companyName))) {
            System.out.println("The company was created!");
        } else {
            System.out.println("Error! Company not created!");
        }
    }

    private void showCompanyList() {
        List<Company> companyList = companyDAO.findAll();
        if (companyList.isEmpty()) {
            System.out.println("\nThe company list is empty!");
        } else {
            System.out.println("\nChoose a company:");
            AtomicInteger i = new AtomicInteger(1);
            companyList.forEach(x -> System.out.println(i.getAndIncrement() + ". " + x.getName()));
            System.out.println("0. Back");
            int userInput = UserInput.getChoiceAsInteger();
            if (userInput == 0) {
                return;
            }
            Company company;
            try {
                company = companyList.get(userInput - 1);
            } catch (Exception e){
                System.out.println("Company not found!");
                return;
            }
            companyService.menu(company);
        }
    }
}
