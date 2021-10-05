package Repository;

import Model.Vehicle;

public class Repo implements IRepo {
    private Vehicle[] vehicles;
    private int length;
    private int currentIndex;

    public Repo(int length) {
        this.length = length;
        this.currentIndex = 0;
        this.vehicles = new Vehicle[length];
    }

    @Override
    public void addVehicle(Vehicle V) {
        this.vehicles[this.currentIndex++] = V;
    }

    @Override
    public void removeVehicle(int index) {
        int position;
        for (position = index; position < this.length - 1; position ++) {
            this.vehicles[position] = this.vehicles[position + 1];
        }
        this.currentIndex --;
    }

    @Override
    public void updateVehicle(int index, int newPrice) {
        this.vehicles[index].setPrice(newPrice);
    }

    @Override
    public int getLength() {
        return this.currentIndex;
    }


    @Override
    public Vehicle[] getAll() {
        return this.vehicles;
    }
}
