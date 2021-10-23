package Model.exp;

import Exceptions.VariableTypeException;
import Model.adt.IDict;
import Model.types.BoolType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;

public class ValueExp extends Exp {
    private final IValue val;

    public ValueExp(IValue _val) {
        this.val = _val;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable) throws Exception {
        if (val.getType().equals(new IntType()) || val.getType().equals(new BoolType())) {
            return val;
        }
        throw new VariableTypeException("Unknown type specified.\n");
    }

    @Override
    public String toString() {
        return this.val.toString();
    }
}
