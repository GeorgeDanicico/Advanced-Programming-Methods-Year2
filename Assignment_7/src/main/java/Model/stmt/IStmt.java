package Model.stmt;

import Model.types.IType;
import Model.PrgState;
import Model.adt.IDict;

public interface IStmt {
    String toString();
    IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception;
    PrgState execute(PrgState state) throws Exception;
}
