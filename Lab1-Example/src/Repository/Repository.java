package Repository;
import Exceptions.ImplementedException;
import Model.Animal;
import Model.Dog;

public class Repository {
    private Animal[] animals;
    private int total;

    public Repository() {
        this.animals = new Animal[20];
        this.total = 0;
    }

    public int length() {
        return this.total;
    }

    public void addAnimal(String type, String sound) throws ImplementedException {
        if (type.equals("Dog")) {
            Animal animal = new Dog(type, sound);
            this.animals[total++] = animal;
        }
        else throw new ImplementedException("The animal does not exist.");
    }

    public Animal[] getAnimals() {
        return this.animals;
    }
}
