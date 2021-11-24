package Model.stmt;

import Model.PrgState;

public interface IStmt {
    String toString();
    PrgState execute(PrgState state) throws Exception;
}
