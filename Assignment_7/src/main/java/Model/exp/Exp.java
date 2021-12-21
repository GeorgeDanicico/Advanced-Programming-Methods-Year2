package Model.exp;
import Model.types.IType;
import Model.value.IValue;
import Model.adt.Heap;
import Model.adt.IDict;

public abstract class Exp {

    public abstract IValue eval(IDict<String, IValue> symTable, Heap<Integer, IValue> heapTable) throws Exception;
    public abstract IType typecheck(IDict<String, IType> typeEnv) throws Exception;
    public abstract String toString();
}
