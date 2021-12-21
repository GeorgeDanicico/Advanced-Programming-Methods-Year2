package Model.stmt;

import Model.types.IType;
import Model.types.StringType;
import Model.value.IValue;
import Model.value.StringValue;
import Exceptions.UndefinedException;
import Exceptions.VariableTypeException;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.exp.Exp;

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
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typeExp = exp.typecheck(typeEnv);
        if (typeExp.equals(new StringType())) {
            return typeEnv;
        } else throw new VariableTypeException("CloseFile stmt: invalid expression type.");
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {

        IDict<String, IValue> symTbl = state.getSymTable();
        IDict<String, BufferedReader>  fileTable = state.getFileTable();
        Heap<Integer, IValue> heapTbl = (Heap<Integer, IValue>) state.getHeapTable();

        IValue expEval = exp.eval(symTbl, heapTbl);

        if (expEval.getType().equals(new StringType())){
            StringValue sv = (StringValue) expEval;

            if (fileTable.isDefined(sv.getValue())) {
                BufferedReader reader = fileTable.lookup(sv.getValue());
                reader.close();
                fileTable.remove(sv.getValue());

            } else throw new UndefinedException("Unknown entry for file.");

        } else throw new VariableTypeException("The type must be string.");

        return null;
    }
}
