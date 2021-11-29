package Model.exp;

import Exceptions.HeapException;
import Exceptions.VariableTypeException;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.types.IType;
import Model.types.RefType;
import Model.value.IValue;
import Model.value.RefValue;

public class ReadHeapExp extends Exp{
    private final Exp exp;

    public ReadHeapExp(Exp _exp) {
        this.exp = _exp;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, Heap<Integer, IValue> heapTable) throws Exception {

        IValue expEval = exp.eval(symTable, heapTable);

        if (expEval.getType() instanceof RefType) {
            int address = ((RefValue) expEval).getAddr();
            if (heapTable.isDefined(address)) {
                return heapTable.lookup(address);
            } else throw new HeapException("Uninitialized address memory");

        } else throw new VariableTypeException("The variable must be of type RefValue");
    }

    @Override
    public IType typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typ = exp.typecheck(typeEnv);

        if (typ instanceof RefType) {
            RefType reft = (RefType) typ;
            return reft.getInner();
        } else throw new VariableTypeException("the RH argument is not a ref type.");
    }

    @Override
    public String toString() {
        return "rH(" + exp.toString() + ")";
    }
}
