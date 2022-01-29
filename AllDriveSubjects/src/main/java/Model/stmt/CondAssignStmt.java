package Model.stmt;

import Exceptions.ExpressionException;
import Exceptions.VariableTypeException;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;
import Model.value.IValue;

public class CondAssignStmt implements IStmt{
    private final String id;
    private final Exp exp1;
    private final Exp exp2;
    private final Exp exp3;

    public CondAssignStmt(String _id, Exp _exp1, Exp _exp2, Exp _exp3) {
        id = _id;
        exp1 = _exp1;
        exp2 = _exp2;
        exp3 = _exp3;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typ1, typ2, typ3, variableType;
        typ1 = exp1.typecheck(typeEnv);

        if (typ1.equals(new BoolType())) {
            variableType = typeEnv.lookup(id);

            typ2 = exp2.typecheck(typeEnv);
            typ3 = exp3.typecheck(typeEnv);
            if (!variableType.equals(typ2) || !variableType.equals(typ3)) {
                throw new VariableTypeException("Variable types does not match");
            }

        } else throw new ExpressionException("Invalid expression");

        return typeEnv;
    }

    @Override
    public String toString() {
        return "(" + id + " = " + exp1.toString() + " ? " + exp2.toString() + " : " + exp3.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> symTbl = state.getSymTable();
        Heap<Integer, IValue> heapTbl = (Heap<Integer, IValue>) state.getHeapTable();
        IStack<IStmt> stack = state.getStack();

        // all the checking has been done through the  typechecker => no validations are required.

        BoolValue val = (BoolValue) exp1.eval(symTbl, heapTbl);

        if (val.getValue()) {
            IValue newValue = exp2.eval(symTbl, heapTbl);
            symTbl.update(id, newValue);
        } else {
            IValue newValue = exp3.eval(symTbl, heapTbl);
            symTbl.update(id, newValue);
        }

        return null;
    }
}
