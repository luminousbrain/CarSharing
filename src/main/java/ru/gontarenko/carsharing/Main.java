package ru.gontarenko.carsharing;

import ru.gontarenko.carsharing.app.CarSharingApp;
import ru.gontarenko.carsharing.util.H2Database;

public class Main {
    public static void main(String[] args) {
        if (args.length == 2 && args[0].equals("-databaseFileName")) {
            H2Database.setDbFileName(args[1]);
        } else {
            H2Database.setDbFileName(null);
        }
        CarSharingApp carSharingApp = new CarSharingApp();
        carSharingApp.run();
    }
}
