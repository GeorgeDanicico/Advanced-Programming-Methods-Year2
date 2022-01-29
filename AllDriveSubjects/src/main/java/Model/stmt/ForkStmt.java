package Model.stmt;

import Model.types.IType;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.adt.MyStack;
import Model.value.IValue;

public class ForkStmt implements IStmt{
    private final IStmt stmt;

    public ForkStmt(IStmt _stmt) {
        stmt = _stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {

        IStack<IStmt> newStack = new MyStack<>();
        newStack.push(stmt);
        IStack<IDict<String, IValue>> symTableStackCopy = new MyStack<>();

        state.getSymTableStack().forEach(symTable -> symTableStackCopy.push(symTable.deepCopy()));

        return new PrgState(
                newStack, symTableStackCopy, state.getOutput(),
                stmt, state.getFileTable(), state.getHeapTable(), state.getLockTable(),
                state.getLatchTable(), state.getSemaphoreTable(), state.getBarrierTable(),
                state.getToySemaphoreTable(), state.getProcTable()
        );
    }

    @Override
    public String toString() {
        return "fork(" + stmt.toString() + ")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        stmt.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }
}
