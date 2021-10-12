package Controller;

import CustomException.CustomException;
import Model.Car;
import Model.Motorbike;
import Model.Truck;
import Model.Vehicle;
import Repository.IRepo;
import Repository.Repo;
import Validations.Validate;

import java.util.Arrays;
import java.util.Locale;

public class Controller {
    private IRepo repo;

    public Controller(int length) {
        this.repo = new Repo(length);
    }

    public Controller(Repo _repo) {
        this.repo = _repo;
    }

    public void add(String[] args) throws CustomException {
        // validations

        if (Validate.enoughParams(args, 3) && Validate.validCarType(args[0]) && Validate.isNumber(args[2])) {
            switch (args[0].toLowerCase(Locale.ROOT)) {
                case "car" -> {
                    Car car = new Car(args[1], Integer.parseInt(args[2]));
                    this.repo.addVehicle(car);
                }
                case "truck" -> {
                    Truck t = new Truck(args[1], Integer.parseInt(args[2]));
                    this.repo.addVehicle(t);
                }
                case "motorbike" -> {
                    Motorbike m = new Motorbike(args[1], Integer.parseInt(args[2]));
                    this.repo.addVehicle(m);
                }
                default -> {
                }
            }
        }
    }

    public void remove(String[] args) throws CustomException{
        // validation
        if (Validate.enoughParams(args, 1) && Validate.isNumber(args[0])) {
            int index = Integer.parseInt(args[0]);
            if (index >= this.repo.getLength())
                throw new CustomException("Index out of bounds.");
            this.repo.removeVehicle(index);
        }

    }

    public void update(String[] args) throws CustomException{
        // validation
        if (Validate.enoughParams(args, 2) && Validate.isNumber(args[0]) && Validate.isNumber(args[1])) {
            int index = Integer.parseInt(args[0]);
            if (index >= this.repo.getLength())
                throw new CustomException("Index out of bounds.");

            int newPrice = Integer.parseInt(args[1]);

            this.repo.updateVehicle(index, newPrice);
        }
    }

    public Vehicle[] filterByPrice(String[] args) throws CustomException{

        if (Validate.enoughParams(args, 1) && Validate.isNumber(args[0])) {
            Vehicle[] result = new Vehicle[this.repo.getLength()];

            int price = Integer.parseInt(args[0]);
            int index = 0;
            for (Vehicle vehicle : this.repo.getAll()) {
                if (vehicle != null && vehicle.getPrice() > price) {
                    result[index++] = vehicle;
                }
            }
            return result;
        }
        return null;
    }

    public Vehicle[] getAll() {
        return this.repo.getAll();
    }
}
