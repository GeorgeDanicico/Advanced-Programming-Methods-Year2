
package View;
import Controller.Controller;
import Repository.Repository;

public class Test {
    public static void main(String[] args) {

        Repository repo = new Repository();
        Controller controller = new Controller(repo);

        System.out.println(controller.add("Dog", "Bark"));
        System.out.println(controller.add("Beer", "Bark"));

        System.out.println("The number of animals is: " + repo.length());
    }
}

