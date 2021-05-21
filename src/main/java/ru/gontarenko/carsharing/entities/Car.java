package ru.gontarenko.carsharing.entities;

public class Car {
    private int id;
    private String name;
    private Integer companyId;

    public Car(String name, Integer companyId) {
        this.name = name;
        this.companyId = companyId;
    }

    public Car(int id, String name, Integer companyId) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return id + ". " + name;
    }
}
