package View;

import Controller.Controller;
import CustomException.CustomException;
import Model.Car;
import Model.Motorbike;
import Model.Truck;
import Model.Vehicle;
import Repository.Repo;

import java.util.Scanner;

public class Main {

    public static void printMenu() {
        System.out.println("0. Exit");
        System.out.println("1. Add Vehicle");
        System.out.println("2. Remove Vehicle");
        System.out.println("3. Update price of the repair.");
        System.out.println("4. Filter by the price of the repair.");
        System.out.println("5. Print the entire repair list.");
    }

    public static String addCommand(Scanner scanner) {
        System.out.print("Enter type brand repair-price >> ");
        return scanner.nextLine();
    }

    public static String removeCommand(Scanner scanner) {
        System.out.print("Enter index >> ");
        return scanner.nextLine();
    }

    public static String updateCommand(Scanner scanner) {
        System.out.print("Enter index new-repair-price >> ");
        return scanner.nextLine();
    }

    public static String filterCommand(Scanner scanner) {
        System.out.print("Enter price >> ");
        return scanner.nextLine();
    }

    public static void menu(Controller controller) {

        boolean condition = true;
        while(condition) {
            printMenu();
            System.out.print("Enter >> ");
            Scanner scanner = new Scanner(System.in);

            String cmd = scanner.nextLine();
            try {
                switch (cmd) {
                    case "0": {
                        condition = false;
                        break;
                    }
                    case "1": {
                        controller.add(addCommand(scanner).split(" "));
                        break;
                    }
                    case "2": {
                        controller.remove(removeCommand(scanner).split(" "));
                        break;
                    }
                    case "3": {
                        controller.update(updateCommand(scanner).split(" "));
                        break;
                    }
                    case "4": {
                        int index = 0;
                        Vehicle[] allVehicles = controller.filterByPrice(filterCommand(scanner).split(" "));
                        for (Vehicle vehicle : allVehicles) {
                            if (vehicle != null)
                                System.out.println(index++ + ". " + vehicle.toString());
                        }
                        break;
                    }
                    case "5": {
                        int index = 0;
                        Vehicle[] allVehicles = controller.getAll();
                        for (Vehicle vehicle : allVehicles) {
                            if (vehicle != null)
                                System.out.println(index++ + ". " + vehicle.toString());
                        }
                        break;
                    }
                    default:
                        System.out.println("Invalid command entered.");
                }
            } catch (CustomException e) {
                System.out.println(e.getMessage());
                }
            }

        }

    public static void main(String[] args) {
        Car car1 = new Car("BMW", 1200);
        Truck tr1 = new Truck("Man", 2500);
        Car car2 = new Car("Ford", 300);
        Motorbike m = new Motorbike("Yamaha", 250);

        Repo repo = new Repo(10);
        repo.addVehicle(car1);
        repo.addVehicle(tr1);
        repo.addVehicle(car2);
        repo.addVehicle(m);

        Controller controller = new Controller(repo);

        menu(controller);

    }

}
