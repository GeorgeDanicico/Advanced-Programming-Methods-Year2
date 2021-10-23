package Model.stmt;


import Model.PrgState;
import Model.adt.IList;
import Model.adt.MyList;
import Model.exp.Exp;
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
    public PrgState execute(PrgState state) throws Exception{
        IList<IValue> output = state.getOutput();
        output.add(expression.eval(state.getSymTable()));
        state.setOutput(output);
        return state;
    }


}
