package Model.stmt;

import Model.types.IType;
import Model.PrgState;
import Model.adt.IDict;

public class NopStmt implements IStmt {

    @Override
    public PrgState execute(PrgState state){
        return null;
    }

    @Override
    public String toString(){
        return "no operation";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        return typeEnv;
    }
}
