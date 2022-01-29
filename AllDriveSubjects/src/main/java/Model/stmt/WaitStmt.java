package Model.stmt;

import Exceptions.ExpressionException;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.IList;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.exp.ValueExp;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;

public class WaitStmt implements IStmt{
    private final Exp exp;

    public WaitStmt(Exp _exp) {
        exp = _exp;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType type = exp.typecheck(typeEnv);

        if (!type.equals(new IntType())) {
            throw new ExpressionException("Expression value should have been int");
        }

        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> symTbl = state.getSymTable();
        Heap<Integer, IValue> heapTbl = (Heap<Integer, IValue>) state.getHeapTable();
        IStack<IStmt> stack = state.getStack();
        IList<IValue> output = state.getOutput();

        IntValue value = (IntValue)exp.eval(symTbl, heapTbl);

        if (value.getValue() > 0) {
            output.add(value);
            stack.push(new WaitStmt(new ValueExp(new IntValue(value.getValue() - 1))));
        }

        return null;
    }
}
