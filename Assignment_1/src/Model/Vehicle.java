package Model;

public interface Vehicle {
    public String getBrand();
    public int getPrice();
    public void setPrice(int newPrice);
    public boolean checkRepairPrice(int price);
    public String toString();
}
