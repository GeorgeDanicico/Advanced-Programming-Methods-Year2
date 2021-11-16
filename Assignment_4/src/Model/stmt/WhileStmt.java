package Model.stmt;

import Exceptions.VariableTypeException;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.types.BoolType;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.BufferedReader;

public class WhileStmt implements IStmt{
    private Exp exp;
    private IStmt stmt;

    public WhileStmt(Exp _exp, IStmt _stmt) {
        exp = _exp;
        stmt = _stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> symTbl = state.getSymTable();
        Heap<Integer, IValue> heapTbl = (Heap<Integer, IValue>) state.getHeapTable();
        IStack<IStmt> stack = state.getStack();

        IValue val = exp.eval(symTbl, heapTbl);

        if (val.getType().equals(new BoolType())) {
            BoolValue bV = (BoolValue) val;
            if (bV.getValue()) {
                IStmt copyWhile = new WhileStmt(exp, stmt);
                stack.push(copyWhile);
                stack.push(stmt);
            }
        } else throw new VariableTypeException("Condition exp is not a boolean;)");

        return state;
    }

    @Override
    public String toString() {
        return "(while (" +exp.toString() + ") " + stmt.toString();
    }
}
