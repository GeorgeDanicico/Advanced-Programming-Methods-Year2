package Repo;
import Model.PrgState;
import Model.adt.IList;
import Model.adt.MyList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Repo implements IRepo {

    private IList<PrgState> myPrgStates;
    private String logFilePath;

    public Repo(String _logFilePath) {
        logFilePath = _logFilePath;
        myPrgStates = new MyList<PrgState>();
    }

    @Override
    public IList<PrgState> getPrgList() {
        return this.myPrgStates;
    }

    @Override
    public void setPrgList(IList<PrgState> newPrgList) {
        myPrgStates = newPrgList;
    }

    @Override
    public void addPrg(PrgState newPrg) {
        myPrgStates.add(newPrg);
    }

    @Override
    public void logPrgStateExec(PrgState state) throws Exception {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));

        logFile.write(state.toFile());
        logFile.flush();
        logFile.close();
    }
}
