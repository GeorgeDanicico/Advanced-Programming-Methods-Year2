package Model.stmt;

import Exceptions.ExpressionException;
import Exceptions.FileDescriptorExistsException;
import Exceptions.FileNotExistsException;
import Exceptions.VariableTypeException;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IType;
import Model.types.IntType;
import Model.types.StringType;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStmt implements IStmt{
    private Exp exp;

    public OpenRFileStmt(Exp _exp) {
        exp = _exp;
    }

    @Override
    public String toString() {
        return "(open_file '" + this.exp +"')";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typeExp = exp.typecheck(typeEnv);
        if (typeExp.equals(new StringType())) {
            return typeEnv;
        } else throw new VariableTypeException("OpenFile stmt: invalid expression type.");
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {

        IDict<StringValue, BufferedReader> fileTable = state.getFileTable();
        IDict<String, IValue> symTbl = state.getSymTable();
        Heap<Integer, IValue> heapTbl = (Heap<Integer, IValue>) state.getHeapTable();

        IValue expEval = exp.eval(symTbl, heapTbl);

        if (expEval.getType().equals(new StringType())) {
            StringValue sv = (StringValue) expEval;

            if (!fileTable.isDefined(sv)) {

                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(sv.getValue()));
                } catch (IOException e) {
                    throw new FileNotExistsException("An error occured with the file descriptor.");
                }

                fileTable.add(sv, reader);

            } else throw new FileDescriptorExistsException("File Descriptor exists.");
        } else throw new ExpressionException("The expression is not of type string.");

        return null;
    }
}
