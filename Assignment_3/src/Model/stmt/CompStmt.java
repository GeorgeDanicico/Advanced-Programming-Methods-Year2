package Model.stmt;

import Model.PrgState;
import Model.adt.IStack;

public class CompStmt implements IStmt{
    private final IStmt first;
    private final IStmt second;

    public CompStmt(IStmt _first, IStmt _second) {
        first = _first;
        second = _second;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + "; "+ second.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state){
        IStack<IStmt> stk = state.getStack();
        stk.push(second);
        stk.push(first);
        return state;
    }

}
