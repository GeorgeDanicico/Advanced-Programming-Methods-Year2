package Repository;

import Model.Vegetables;

public class Repo implements IRepo{
    private Vegetables[] list;
    private int currentIndex;
    private int length;

    public Repo(int length) {
        this.length = length;
        this.currentIndex = 0;

        this.list = new Vegetables[length];
    }


    @Override
    public void addVegetable(Vegetables veg) throws Exception{
        if (this.currentIndex >= this.length)
            throw new Exception("Repository is full.");
        this.list[this.currentIndex++] = veg;
    }

    @Override
    public void removeVegetable(int index) throws Exception {
        if (this.currentIndex == 0) {
            throw new Exception("Nothing to remove.");
        }

        if (this.currentIndex < index || index < 0) {
            throw new Exception("Wrong index.");
        }

        this.currentIndex--;
    }

    @Override
    public void updateVegetalbe(int index, Vegetables veg) {

    }

    @Override
    public Vegetables[] getAll() {
        return this.list;
    }
}
