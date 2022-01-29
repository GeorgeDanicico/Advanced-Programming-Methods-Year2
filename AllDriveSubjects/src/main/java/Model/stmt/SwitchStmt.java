package Model.stmt;

import Exceptions.VariableTypeException;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.types.IType;
import Model.value.IValue;

public class SwitchStmt implements IStmt{
    private Exp exp1, exp2, exp;
    private IStmt stmt1, stmt2, stmt3;

    public SwitchStmt(Exp _exp, Exp _exp1, Exp _exp2, IStmt _stmt1, IStmt _stmt2, IStmt _stmt3) {
        exp = _exp;
        exp1 = _exp1;
        exp2 = _exp2;
        stmt1 = _stmt1;
        stmt2 = _stmt2;
        stmt3 = _stmt3;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typexp1, typexp2, typeexp;

        typeexp = exp.typecheck(typeEnv);
        typexp1 = exp.typecheck(typeEnv);
        typexp2 = exp.typecheck(typeEnv);

        if (typeexp.equals(typexp1) && typeexp.equals(typexp2)) {
            stmt1.typecheck(typeEnv);
            stmt2.typecheck(typeEnv);
            stmt3.typecheck(typeEnv);
        } else throw new VariableTypeException("Expression types are not matching");

        return typeEnv;
    }

    @Override
    public String toString() {
        return "switch(" + exp.toString() + ")" + "(case " + exp1.toString() + ": " + stmt1.toString() + ") " +
                "(case " + exp2.toString() + ": " + stmt2.toString() + ") " + "(default: " + stmt3.toString() + ") ";
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> symTbl = state.getSymTable();
        Heap<Integer, IValue> heapTbl = (Heap<Integer, IValue>) state.getHeapTable();
        IStack<IStmt> stack = state.getStack();

        IValue expVal = exp.eval(symTbl, heapTbl);
        IValue exp1Val = exp.eval(symTbl, heapTbl);

        if (expVal.equals(exp1Val)) {
            stack.push(stmt1);
        } else {
            IValue exp2Val = exp.eval(symTbl, heapTbl);
            if (exp2Val.equals(expVal)) {
                stack.push(stmt2);
            } else stack.push(stmt3);
        }

        return null;
    }
}
