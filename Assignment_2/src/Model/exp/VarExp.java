package Model.exp;
import Model.adt.IDict;
import Model.value.IValue;
import Model.value.IntValue;

public class VarExp extends Exp{
    String id;

    public VarExp(String id){
        this.id = id;
    }

    public IValue eval(IDict<String, IValue> symTable) throws Exception {
        return symTable.lookup(id).deepCopy();
    }

    public String toString() {
        return id;
    }

}
