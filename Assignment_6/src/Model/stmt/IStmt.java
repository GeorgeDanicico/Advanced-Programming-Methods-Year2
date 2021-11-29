package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.types.IType;

public interface IStmt {
    String toString();
    IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception;
    PrgState execute(PrgState state) throws Exception;
}
