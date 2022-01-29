package Model.exp;

import Model.types.*;
import Model.value.IValue;
import Exceptions.VariableTypeException;
import Model.adt.Heap;
import Model.adt.IDict;

public class ValueExp extends Exp {
    private final IValue val;

    public ValueExp(IValue _val) {
        this.val = _val;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, Heap<Integer, IValue> heapTable) throws Exception {
        if (val.getType().equals(new IntType()) || val.getType().equals(new BoolType()) || val.getType().equals(new StringType())
         || val.getType() instanceof RefType) {
            return val;
        }
        throw new VariableTypeException("Unknown type specified.\n");
    }

    @Override
    public IType typecheck(IDict<String, IType> typeEnv) throws Exception {
        return val.getType();
    }

    @Override
    public String toString() {
        return this.val.toString();
    }
}
