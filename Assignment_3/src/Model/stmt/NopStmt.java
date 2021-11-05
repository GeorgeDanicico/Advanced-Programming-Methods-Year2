package Model.stmt;

import Model.PrgState;

public class NopStmt implements IStmt {

    @Override
    public PrgState execute(PrgState state){
        return state;
    }

    @Override
    public String toString(){
        return "no operation";
    }
}
