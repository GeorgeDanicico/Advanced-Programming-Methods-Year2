package Model.stmt;

import Exceptions.AssignmentException;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.types.IType;
import Model.value.IValue;

public class AssignStmt implements IStmt{

    private final String id;
    private final Exp expression;

    public AssignStmt(String id, Exp exp){
        this.id = id;
        this.expression = exp;
    }

    @Override
    public String toString(){
        return this.id + "=" + this.expression.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws Exception{
        IStack<IStmt> stk = state.getStack();
        IDict<String, IValue> symTbl = state.getSymTable();
        Heap<IValue> heapTbl = (Heap<IValue>) state.getHeapTable();

        if (symTbl.isDefined(id)) {
            IValue val = expression.eval(symTbl, heapTbl);
            IType typId = symTbl.lookup(id).getType();
            if (val.getType().equals(typId)) {
                symTbl.update(id, val);
            } else throw new AssignmentException("Declared type of variable" + id + "and type of the " +
                    "assigned expression do not match");
        } else throw new AssignmentException("The used variable" + id + "was not declared before.");

        return state;

    }
}
