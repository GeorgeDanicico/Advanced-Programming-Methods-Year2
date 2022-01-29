package Model.stmt;

import Exceptions.ExpressionException;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.IValue;

public class RepeatUntilStmt implements IStmt{
    private Exp exp;
    private IStmt stmt;

    public RepeatUntilStmt(Exp _exp, IStmt _stmt) {
        exp = _exp;
        stmt = _stmt;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType type1;

        type1 = exp.typecheck(typeEnv);

        if (type1.equals(new BoolType())) {
            stmt.typecheck(typeEnv);
        } else throw new ExpressionException("Expression type must be boolean");

        return typeEnv;
    }

    @Override
    public String toString() {
        return "repeat " + stmt.toString() + " until " + exp.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> symTbl = state.getSymTable();
        Heap<Integer, IValue> heapTbl = (Heap<Integer, IValue>) state.getHeapTable();
        IStack<IStmt> stack = state.getStack();

        IStmt repeatStmt = new CompStmt(stmt, new WhileStmt(exp, stmt, false));

        stack.push(repeatStmt);
        return null;
    }
}
