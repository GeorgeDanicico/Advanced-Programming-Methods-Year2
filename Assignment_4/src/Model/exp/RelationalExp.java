package Model.exp;

import Exceptions.DivisionByZeroException;
import Exceptions.VariableTypeException;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.types.IntType;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.IntValue;

public class RelationalExp extends Exp{
    private final String operator;
    private final Exp e1, e2;

    public RelationalExp(Exp _e1, Exp _e2, String _operator) {
        e1 = _e1;
        e2 = _e2;
        operator = _operator;
    }

    @Override
    public String toString() {
        return "(" + e1.toString() + " " + operator + " " + e2.toString() + ")";
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, Heap<IValue> heapTable) throws Exception {
        IValue v1 = e1.eval(symTable, heapTable);

        if (v1.getType().equals(new IntType())) {
            IValue v2 = e2.eval(symTable, heapTable);
            if (v2.getType().equals(new IntType())) {
                IntValue nr1 = (IntValue)v1;
                IntValue nr2 = (IntValue)v2;

                switch (operator) {
                    case ">":
                        return new BoolValue(nr1.getValue() > nr2.getValue());
                    case "<":
                        return new BoolValue(nr1.getValue() < nr2.getValue());
                    case "<=":
                        return new BoolValue(nr1.getValue() <= nr2.getValue());
                    case ">=":
                        return new BoolValue(nr1.getValue() >= nr2.getValue());
                    case "==":
                        return new BoolValue(nr1.getValue() == nr2.getValue());
                    case "!=" :
                        return new BoolValue(nr1.getValue() != nr2.getValue());

                }
            }
            else {
                throw new VariableTypeException("Operand 2 is not an integer.\n");
            }
        } else {
            throw new VariableTypeException("Operand 1 is not an integer.\n");
        }
        return new BoolValue();
    }
}
