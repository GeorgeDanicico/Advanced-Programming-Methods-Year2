package Model.exp;
import Exceptions.DivisionByZeroException;
import Exceptions.VariableTypeException;
import Model.adt.IDict;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;

public class ArithExp extends Exp{
    private final char op;
    private final Exp e1, e2;

    //constructor
    public ArithExp(char _op, Exp _e1, Exp _e2) {
        this.op = _op;
        this.e1 = _e1;
        this.e2 = _e2;
    }

    public IValue eval(IDict<String, IValue> symTable) throws Exception {

        IValue v1 = e1.eval(symTable);

        if (v1.getType().equals(new IntType())) {
            IValue v2 = e2.eval(symTable);
            if (v2.getType().equals(new IntType())) {
                IntValue nr1 = (IntValue)v1;
                IntValue nr2 = (IntValue)v2;

                switch (op) {
                    case '+':
                        return new IntValue(nr1.getValue() + nr2.getValue());
                    case '-':
                        return new IntValue(nr1.getValue() - nr2.getValue());
                    case '*':
                        return new IntValue(nr1.getValue() * nr2.getValue());
                    case '/':
                        if (nr2.getValue() == 0) {
                            throw new DivisionByZeroException("Division by 0.\n");
                        }
                        return new IntValue(nr1.getValue() / nr2.getValue());
                }
            }
            else {
                throw new VariableTypeException("Operand 2 is not an integer.\n");
            }
        } else {
            throw new VariableTypeException("Operand 1 is not an integer.\n");
        }
        return new IntValue();
    }


    public char getOp() {return this.op;}

    public Exp getFirst() {
        return this.e1;
    }

    public Exp getSecond() {
        return this.e2;
    }

    public String toString() { return e1.toString() + " " + op + " " + e2.toString(); }
}
