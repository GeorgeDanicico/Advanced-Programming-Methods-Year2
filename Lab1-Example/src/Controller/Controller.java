package Controller;

import Exceptions.ImplementedException;
import Repository.Repository;

public class Controller {
    Repository repo;

    public Controller(Repository repo) {
        this.repo = repo;
    }

    public boolean add(String type, String sound) {
        try {
            this.repo.addAnimal(type, sound);
            return true;
        } catch (ImplementedException e) {
            return false;
        }
    }
}
