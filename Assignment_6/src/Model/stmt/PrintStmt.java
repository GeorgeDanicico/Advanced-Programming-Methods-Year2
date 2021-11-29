package Model.stmt;


import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.IList;
import Model.adt.MyList;
import Model.exp.Exp;
import Model.types.IType;
import Model.value.IValue;

public class PrintStmt implements IStmt{

    private final Exp expression;

    public PrintStmt(Exp exp){
        this.expression = exp;
    }

    @Override
    public String toString() {
        return "(" + this.expression.toString() + ")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        expression.typecheck(typeEnv);

        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception{
        IList<IValue> output = state.getOutput();
        Heap<Integer, IValue> heapTbl = (Heap<Integer, IValue>) state.getHeapTable();

        output.add(expression.eval(state.getSymTable(), heapTbl));
        state.setOutput(output);
        return null;
    }


}
