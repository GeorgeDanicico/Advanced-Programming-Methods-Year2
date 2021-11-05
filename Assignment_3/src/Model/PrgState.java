package Model;
import Model.adt.IDict;
import Model.adt.IList;
import Model.adt.IStack;
import Model.adt.MyList;
import Model.stmt.IStmt;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.BufferedReader;

public class PrgState {

    private IStack<IStmt> exeStack;
    private IDict<String, IValue> symTable;
    private IDict<StringValue, BufferedReader> fileTable;
    private IList<IValue> out;
    private IStmt originalProgram; //optional field, but good to have

    public PrgState(IStack<IStmt> _es, IDict<String, IValue> _symtbl, IList<IValue> _out, IStmt _og,
                    IDict<StringValue, BufferedReader> _fileTable) {
        exeStack = _es;
        symTable = _symtbl;
        out = _out;
        originalProgram = _og;
        fileTable = _fileTable;
    }

    public IList<IValue> getOutput() {
        return out;
    }

    public IDict<String, IValue> getSymTable() {
        return this.symTable;
    }

    public IStack<IStmt> getStack() { return this.exeStack; }

    public IDict<StringValue, BufferedReader> getFileTable() { return this.fileTable; }

    public void setOutput(IList<IValue> output) {
        this.out = output;
    }

    public String toString() {
        return "Stack: " + exeStack.toString() + "\n" + "SymTable: " + symTable.toString() + "\n" +
                "Out: " + out.toString() + "\n";
    }

    public String toFile() {
        return "ExeStack: \n" + exeStack.toString() + "\n" + "SymTable: \n" + symTable.toString() + "\n" +
                "Out: \n" + out.toString() + "\n" + "FileTable: \n" + fileTable.toString() + "\n\n";
    }
}