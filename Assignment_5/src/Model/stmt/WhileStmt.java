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

        IValue expEval = exp.eval(symTbl, heapTbl);

        if (expEval.getType().equals(new BoolType())) {
            BoolValue bV = (BoolValue) expEval;
            if (bV.getValue()) {
                IStmt copyWhile = new WhileStmt(exp, stmt);
                stack.push(copyWhile);
                stack.push(stmt);
            }
        } else throw new VariableTypeException("Condition exp is not a boolean;)");

        return null;
    }

    @Override
    public String toString() {
        return "(while (" +exp.toString() + ") " + stmt.toString();
    }
}
