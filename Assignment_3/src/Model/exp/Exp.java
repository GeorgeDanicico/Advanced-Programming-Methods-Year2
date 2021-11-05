package Model.exp;
import Model.adt.IDict;
import Model.value.IValue;

public abstract class Exp {

    public abstract IValue eval(IDict<String, IValue> symTable) throws Exception;
    public abstract String toString();
}
