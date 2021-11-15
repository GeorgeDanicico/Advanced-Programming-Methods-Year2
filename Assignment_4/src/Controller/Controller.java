package Controller;
import Exceptions.StackEmptyException;
import Model.PrgState;
import Model.adt.IStack;
import Model.stmt.IStmt;
import Repo.Repo;
import Repo.IRepo;

public class Controller {

    //constructor
    private Repo repo;


    public Controller(IRepo _repo) {
        repo = (Repo)_repo;
    }

    public void addProgram(PrgState newPrg) {
        repo.addPrg(newPrg);
    }

    public PrgState oneStep(PrgState state) throws Exception {
        IStack<IStmt> stack = state.getStack();
        if (stack.isEmpty())
            throw new StackEmptyException("PrgState stack is empty.");
        IStmt currentStmt = stack.pop();
        return currentStmt.execute(state);
    }

    public void allStep() throws Exception{
            PrgState prg = this.repo.getCrtPrg();
            IStack<IStmt> stack = prg.getStack();
            repo.logPrgStateExec();
            while (!stack.isEmpty()) {
                oneStep(prg);
                repo.logPrgStateExec();
            }
     }
}
