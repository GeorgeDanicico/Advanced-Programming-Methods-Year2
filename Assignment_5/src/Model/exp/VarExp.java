package Model.exp;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.value.IValue;
import Model.value.IntValue;

public class VarExp extends Exp{
    String id;

    public VarExp(String id){
        this.id = id;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, Heap<Integer, IValue> heapTable) throws Exception {
        return symTable.lookup(id);
    }

    public String toString() {
        return id;
    }

}
