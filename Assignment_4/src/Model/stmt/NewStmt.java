package Model.stmt;

import Exceptions.UndefinedException;
import Exceptions.VariableTypeException;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.RefType;
import Model.value.IValue;
import Model.value.RefValue;

public class NewStmt implements IStmt{
    private final String variableName;
    private final Exp exp;

    public NewStmt(String _variableName, Exp _exp) {
        variableName = _variableName;
        exp = _exp;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> symTbl = state.getSymTable();
        Heap<Integer, IValue> heapTbl = (Heap<Integer, IValue>) state.getHeapTable();

        if (symTbl.isDefined(variableName)) {
            IValue val = symTbl.lookup(variableName);
            if (val.getType() instanceof RefType) {
                IValue cond = exp.eval(symTbl, heapTbl);
                RefValue refVal = (RefValue) val;
                if (cond.getType().equals(refVal.getLocType())) {
                    int pos = heapTbl.add(cond);
                    RefValue copyRef = (RefValue) refVal.deepCopy();
                    copyRef.setAddr(pos);
                    symTbl.update(variableName, copyRef);

                } else throw new VariableTypeException("The types of the variables are different.");
            } else throw new VariableTypeException("Variable type must be RefType!");
        } else throw new UndefinedException("Variable undefined!");

        return state;
    }

    @Override
    public String toString() {
        return "(new " + variableName + " " + exp.toString() + ")";
    }
}
