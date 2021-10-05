package Model;

public class Truck implements Vehicle {
    private int repairPrice;
    private String brand;

    public Truck(String _brand, int _repairPrice) {
        this.brand = _brand;
        this.repairPrice = _repairPrice;
    }

    @Override
    public String getBrand() {
        return this.brand;
    }

    @Override
    public int getPrice() {
        return this.repairPrice;
    }

    @Override
    public void setPrice(int newPrice) {
        this.repairPrice = newPrice;
    }

    @Override
    public boolean checkRepairPrice(int price) {
        return this.repairPrice > price;
    }
    @Override
    public String toString() {

        return "Truck: " + String.format("%15s",this.brand) + " | Repair:" + String.format("%6d",this.repairPrice);
    }
}