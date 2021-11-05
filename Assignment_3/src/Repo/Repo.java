package Repo;
import Model.PrgState;
import Model.adt.IList;
import Model.adt.MyList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Iterator;

public class Repo implements IRepo {

    private IList<PrgState> myPrgStates;
    private String logFilePath;

    public Repo(String _logFilePath) {
        logFilePath = _logFilePath;
        myPrgStates = new MyList<PrgState>();
    }

    @Override
    public PrgState getCrtPrg() throws Exception {
        return myPrgStates.peek();
    }

    @Override
    public void addPrg(PrgState newPrg) {
        myPrgStates.add(newPrg);
    }

    @Override
    public void logPrgStateExec() throws Exception {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));

        for(int i = 0; i < myPrgStates.size(); i++) {
            logFile.write(myPrgStates.get(i).toFile());
        }
        logFile.flush();
        logFile.close();
    }
}
