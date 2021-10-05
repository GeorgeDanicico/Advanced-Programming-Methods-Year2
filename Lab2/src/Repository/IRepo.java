package Repository;

import Model.Vegetables;

public interface IRepo {
    public void addVegetable(Vegetables veg) throws Exception;
    public void removeVegetable(int index) throws Exception;
    public void updateVegetalbe(int index, Vegetables veg);

    Vegetables[] getAll();

}
