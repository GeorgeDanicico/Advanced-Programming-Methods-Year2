package Model.stmt;

import Exceptions.ExpressionException;
import Exceptions.FileDescriptorExistsException;
import Exceptions.FileNotExistsException;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
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
    public PrgState execute(PrgState state) throws Exception {

        IDict<StringValue, BufferedReader> fileTable = state.getFileTable();
        IDict<String, IValue> symTbl = state.getSymTable();

        IValue condition = exp.eval(symTbl);

        if (condition.getType().equals(new StringType())) {
            StringValue sv = (StringValue) condition;

            if (!fileTable.isDefined(sv)) {

                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(sv.getValue()));
                } catch (IOException e) {
                    throw new FileNotExistsException(e.getMessage());
                }

                fileTable.add(sv, reader);

            } else throw new FileDescriptorExistsException("File Descriptor exists.");
        } else throw new ExpressionException("The expression is not of type string.");

        return state;
    }
}
