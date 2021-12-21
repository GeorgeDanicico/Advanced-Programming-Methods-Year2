package Model.stmt;

import Exceptions.ExpressionException;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.Exp;

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
        Heap<Integer, IValue> heapTbl = (Heap<Integer, IValue>) state.getHeapTable();

        IValue expEval = exp.eval(symTbl, heapTbl);

        if (expEval.getType().equals(new BoolType())) {
            BoolValue bv = (BoolValue) expEval;
            if (bv.getValue()) {
                stk.push(thenS);
            }
            else stk.push(elseS);
        } else throw new ExpressionException("Conditional expr is not boolean.");

        return null;
    }

    @Override
    public String toString() {
        return "(IF(" + exp.toString() + ")THEN(" + thenS.toString() + ")ELSE(" +
                elseS.toString();
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typeExp = exp.typecheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.deepCopy());
            elseS.typecheck(typeEnv.deepCopy());
            return typeEnv;
        } else throw new ExpressionException("The condition of IF has not the type bool");
    }
}
