package Model;

public class Tomato implements Vegetables{
    private float weight;

    public Tomato(float weight) {
        this.weight = weight;
    }

    @Override
    public float getWeight() {
        return this.weight;
    }

    @Override
    public boolean checkWeight() {
        return this.weight > 0.2;
    }

    @Override
    public String toString() {
        return "Tomato's weight: " + this.weight;
    }
}
