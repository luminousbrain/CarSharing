package ru.gontarenko.carsharing.app;

import ru.gontarenko.carsharing.dao.CarDAO;
import ru.gontarenko.carsharing.dao.CompanyDAO;
import ru.gontarenko.carsharing.dao.CustomerDAO;
import ru.gontarenko.carsharing.entities.Customer;
import ru.gontarenko.carsharing.util.UserInput;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerManager {
    private final CustomerDAO customerDAO;
    private final CustomerService customerService;

    public CustomerManager(CustomerDAO customerDAO, CarDAO carDAO, CompanyDAO companyDAO) {
        this.customerDAO = customerDAO;
        this.customerService = new CustomerService(carDAO, customerDAO, companyDAO);
    }

    public void logIn() {
        List<Customer> customerList = customerDAO.findAll();
        if (customerList.isEmpty()) {
            System.out.println("\nThe customer list is empty!");
        } else {
            System.out.println("\nChoose a customer:");
            AtomicInteger i = new AtomicInteger(1);
            customerList.forEach(x -> System.out.println(i.getAndIncrement() + ". " + x.getName()));
            System.out.println("0. Back");
            int userInput = UserInput.getChoiceAsInteger();
            Customer customer;
            try {
                customer = customerList.get(userInput - 1);
            } catch (Exception e){
                System.out.println("Customer not found!");
                return;
            }
            customerService.menu(customer);
        }
    }

    public void createNew() {
        System.out.println("\nEnter the customer name:");
        final String name = UserInput.getChoiceAsString();
        if (customerDAO.save(new Customer(name))) {
            System.out.println("The customer was added!");
        } else {
            System.out.println("Error! Customer not added!");
        }
    }
}
