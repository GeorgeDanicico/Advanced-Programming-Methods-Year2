package Model.exp;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.value.IValue;

public abstract class Exp {

    public abstract IValue eval(IDict<String, IValue> symTable, Heap<Integer, IValue> heapTable) throws Exception;
    public abstract String toString();
}
