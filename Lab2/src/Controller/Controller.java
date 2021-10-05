package Controller;

import Model.Vegetables;
import Repository.Repo;

public class Controller {

    private Repo repo;

    public Controller(int length) {
        this.repo = new Repo(length);
    }

    public Controller(Repo _r) {
        this.repo = _r;
    }

    public void add(Vegetables veg) throws Exception{
        this.repo.addVegetable(veg);
    }

    public void remove(int index) throws Exception {
        this.repo.removeVegetable(index);
    }

    public Vegetables[] filterVegetables() throws Exception {
        Vegetables[] result = new Vegetables[20];

        return result;
    }
}
