package Repository;

import Model.Vehicle;

public interface IRepo {
    public void addVehicle(Vehicle V);
    public void removeVehicle(int index);
    public void updateVehicle(int index, int newPrice);

    public int getLength();
    public Vehicle[] getAll();
}
