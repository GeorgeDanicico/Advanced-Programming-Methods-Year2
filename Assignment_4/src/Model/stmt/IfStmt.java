package Model.stmt;

import Exceptions.ExpressionException;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.types.BoolType;
import Model.value.BoolValue;
import Model.value.IValue;

public class IfStmt implements IStmt{
    private final Exp exp;
    private final IStmt thenS;
    private final IStmt elseS;

    public IfStmt(Exp _exp, IStmt _thenS, IStmt _elseS) {
        exp = _exp;
        thenS = _thenS;
        elseS = _elseS;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {

        IStack<IStmt> stk = state.getStack();
        IDict<String, IValue> symTbl = state.getSymTable();
        Heap<IValue> heapTbl = (Heap<IValue>) state.getHeapTable();

        IValue condition = exp.eval(symTbl, heapTbl);

        if (condition.getType().equals(new BoolType())) {
            BoolValue bv = (BoolValue) condition;
            if (bv.getValue()) {
                stk.push(thenS);
            }
            else stk.push(elseS);
        } else throw new ExpressionException("Conditional expr is not boolean.");

        return state;
    }

    @Override
    public String toString() {
        return "(IF(" + exp.toString() + ")THEN(" + thenS.toString() + ")ELSE(" +
                elseS.toString();
    }
}
