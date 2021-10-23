package Model;
import Model.adt.IDict;
import Model.adt.IList;
import Model.adt.IStack;
import Model.adt.MyList;
import Model.stmt.IStmt;
import Model.value.IValue;

public class PrgState {

    private IStack<IStmt> exeStack;
    private IDict<String, IValue> symTable;
    private IList<IValue> out;
    private IStmt originalProgram; //optional field, but good to have

    public PrgState(IStack<IStmt> _es, IDict<String, IValue> _symtbl, IList<IValue> _out, IStmt _og) {
        exeStack = _es;
        symTable = _symtbl;
        out = _out;
        originalProgram = _og;
    }

    public IList<IValue> getOutput() {
        return out;
    }

    public IDict<String, IValue> getSymTable() {
        return this.symTable;
    }

    public IStack<IStmt> getStack() { return this.exeStack; }

    public void setOutput(IList<IValue> output) {
        this.out = output;
    }

    public String toString() {
        return "Stack: " + exeStack.toString() + "\n" + "SymTable: " + symTable.toString() + "\n" +
                "Out: " + out.toString() + "\n";
    }
}