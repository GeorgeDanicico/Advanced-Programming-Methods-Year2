package Repo;
import Model.PrgState;

public interface IRepo {
    void addPrg(PrgState newPrg);
    PrgState getCrtPrg() throws Exception;
    void logPrgStateExec() throws Exception;
    }
