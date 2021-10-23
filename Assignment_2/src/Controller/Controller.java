package Controller;
import Model.PrgState;
import Model.adt.IStack;
import Model.stmt.IStmt;
import Repo.Repo;

public class Controller {

    //constructor
    private Repo repo;


    public Controller(Repo _repo) {
        repo = _repo;
    }

    public void addProgram(PrgState newPrg) {
        repo.addPrg(newPrg);
    }

    public PrgState oneStep(PrgState state) throws Exception {
        IStack<IStmt> stack = state.getStack();
        if (stack.isEmpty())
            throw new Exception("PrgState stack is empty.");
        IStmt currentStmt = stack.pop();
        return currentStmt.execute(state);
    }

    public void allStep() {
        PrgState prg = this.repo.getCrtPrg();
        IStack<IStmt> stack = prg.getStack();
        try {
            System.out.println(prg.toString());
            while (!stack.isEmpty()) {
                oneStep(prg);
                System.out.println(prg.toString());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     }
}
