package Model.stmt;

import Exceptions.FileNotExistsException;
import Exceptions.UndefinedException;
import Exceptions.VariableDefinedException;
import Exceptions.VariableTypeException;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IntType;
import Model.types.StringType;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.StringValue;

import java.io.BufferedReader;

public class ReadFileStmt implements IStmt{
    private final String var_name;
    private final Exp exp;

    public ReadFileStmt(Exp _exp, String _var_name) {
        var_name = _var_name;
        exp = _exp;
    }

    @Override
    public String toString() {
        return "(read_file' " + this.exp +"')";
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {

        IDict<String, IValue> symTbl = state.getSymTable();
        IDict<StringValue, BufferedReader>  fileTable = state.getFileTable();
        Heap<Integer, IValue> heapTbl = (Heap<Integer, IValue>) state.getHeapTable();

        if (symTbl.isDefined(var_name)) {
            IValue v = symTbl.lookup(var_name);

            if (v.getType().equals(new IntType())) {
                IValue condition = exp.eval(symTbl, heapTbl);

                if (condition.getType().equals(new StringType())) {

                    StringValue sv = (StringValue) condition;

                    if (fileTable.isDefined(sv)) {
                        BufferedReader reader = fileTable.lookup(sv);
                        String line = reader.readLine();

                        if (line == null) {
                            symTbl.update(var_name, new IntValue(0));
                        } else {
                            symTbl.update(var_name, new IntValue(Integer.parseInt(line)));
                        }

                    } else throw new FileNotExistsException("");

                } else throw new VariableTypeException("Variable type is not string.");

            } else throw new VariableTypeException("Variable type is not int");

        } else throw new UndefinedException("Variable undefined.");

        return state;
    }
}
