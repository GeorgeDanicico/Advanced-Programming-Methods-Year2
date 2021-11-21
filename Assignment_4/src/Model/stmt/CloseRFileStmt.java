package Model.stmt;

import Exceptions.UndefinedException;
import Exceptions.VariableTypeException;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.StringType;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.BufferedReader;

public class CloseRFileStmt implements IStmt{
    private final Exp exp;

    public CloseRFileStmt(Exp _exp) {
        exp = _exp;
    }

    @Override
    public String toString() {
        return "(close file '" + this.exp +"')";
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {

        IDict<String, IValue> symTbl = state.getSymTable();
        IDict<StringValue, BufferedReader>  fileTable = state.getFileTable();
        Heap<Integer, IValue> heapTbl = (Heap<Integer, IValue>) state.getHeapTable();

        IValue expEval = exp.eval(symTbl, heapTbl);

        if (expEval.getType().equals(new StringType())){
            StringValue sv = (StringValue) expEval;

            if (fileTable.isDefined(sv)) {
                BufferedReader reader = fileTable.lookup(sv);
                reader.close();
                fileTable.remove(sv);

            } else throw new UndefinedException("Unknown entry for file.");

        } else throw new VariableTypeException("The type must be string.");

        return state;
    }
}
