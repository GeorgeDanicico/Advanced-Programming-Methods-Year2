package Repo;
import Model.PrgState;
import Model.adt.IList;
import Model.adt.MyList;

public class Repo implements IRepo {

    private IList<PrgState> myPrgStates;

    public Repo() {
        myPrgStates = new MyList<PrgState>();
    }

    @Override
    public PrgState getCrtPrg() throws Exception {
        return myPrgStates.pop();
    }

    @Override
    public void addPrg(PrgState newPrg) {
        myPrgStates.add(newPrg);
    }
}
