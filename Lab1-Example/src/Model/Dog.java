package Model;

public class Dog implements Animal{
    private final String type;
    private final String sound;

    public Dog(String type, String sound) {
        this.type = type;
        this.sound = sound;
    }

    @Override
    public void animalSound() {
        System.out.println(this.sound);
    }

    @Override
    public String type() {
        return this.type;
    }
}
