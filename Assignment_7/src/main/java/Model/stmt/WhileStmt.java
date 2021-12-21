package Model.stmt;

import Exceptions.ExpressionException;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;
import Model.value.IValue;
import Exceptions.VariableTypeException;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.Exp;

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

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {

        IType typeExp = exp.typecheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            stmt.typecheck(typeEnv.deepCopy());
            return typeEnv;
        } else throw new ExpressionException("The condition of while has not the type bool");
    }
}
