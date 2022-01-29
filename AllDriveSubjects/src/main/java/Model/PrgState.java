package Model;
import Model.adt.IDict;
import Model.adt.IList;
import Model.adt.IStack;
import Model.stmt.IStmt;
import Model.value.IValue;
import Model.value.StringValue;
import Exceptions.StackEmptyException;
import javafx.util.Pair;
import org.javatuples.Triplet;

import java.io.BufferedReader;
import java.util.ArrayList;

public class PrgState {

    private static int threadCount = 0;
    private final int threadId;
    private IStack<IStmt> exeStack;
    private IStack<IDict<String, IValue>> symTable;
    private IDict<String, BufferedReader> fileTable;
    private IList<IValue> out;
    private IDict<Integer, IValue> heapTable;
    private IDict<Integer, Integer> lockTable;
    private IDict<Integer, Integer> latchTable;
    private IDict<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable;
    private IDict<Integer, Pair<Integer, ArrayList<Integer>>> barrierTable;
    private IDict<Integer, Triplet<Integer, ArrayList<Integer>, Integer>> toySemaphoreTable;
    private IDict<String, Pair<ArrayList<String>, IStmt>> procTable;
    private IStmt originalProgram; //optional field, but good to have

    private synchronized static int getThreadId() {
        threadCount ++;
        return threadCount - 1;
    }

    public PrgState(IStack<IStmt> _es,
                    IStack<IDict<String, IValue>> _symtbl,
                    IList<IValue> _out, IStmt _og,
                    IDict<String, BufferedReader> _fileTable,
                    IDict<Integer, IValue> _heap,
                    IDict<Integer, Integer> _lock,
                    IDict<Integer, Integer> _latch,
                    IDict<Integer, Pair<Integer, ArrayList<Integer>>> _sema,
                    IDict<Integer, Pair<Integer, ArrayList<Integer>>> _barrier,
                    IDict<Integer, Triplet<Integer, ArrayList<Integer>, Integer>> _toySema,
                    IDict<String, Pair<ArrayList<String>, IStmt>> _proc

    ) {
        threadId = getThreadId();
        exeStack = _es;
        symTable = _symtbl;
        out = _out;
        originalProgram = _og;
        fileTable = _fileTable;
        heapTable = _heap;
        lockTable = _lock;
        latchTable = _latch;
        semaphoreTable = _sema;
        barrierTable = _barrier;
        toySemaphoreTable = _toySema;
        procTable = _proc;
    }

    public int getId() { return this.threadId; }

    public IList<IValue> getOutput() {
        return out;
    }

    public IStack<IDict<String, IValue>> getSymTableStack() { return this.symTable; }

    public IDict<String, IValue> getSymTable() {
        return this.symTable.top();
    }

    public IDict<Integer, IValue> getHeapTable() { return this.heapTable; }

    public IStack<IStmt> getStack() { return this.exeStack; }

    public IDict<String, BufferedReader> getFileTable() { return this.fileTable; }

    public IDict<Integer, Integer> getLockTable() { return this.lockTable; }

    public IDict<Integer, Integer> getLatchTable() { return this.latchTable; }

    public IDict<Integer, Pair<Integer, ArrayList<Integer>>> getSemaphoreTable() { return this.semaphoreTable; }

    public IDict<Integer, Pair<Integer, ArrayList<Integer>>> getBarrierTable() { return this.barrierTable; }

    public IDict<Integer, Triplet<Integer, ArrayList<Integer>, Integer>> getToySemaphoreTable() { return this.toySemaphoreTable; }

    public IDict<String, Pair<ArrayList<String>, IStmt>> getProcTable() { return this.procTable; }

    public void setOutput(IList<IValue> output) {
        this.out = output;
    }

    public PrgState oneStep() throws Exception{
        if (exeStack.isEmpty()) throw new StackEmptyException("PrgState stack is empty.");

        IStmt crtStmt = exeStack.pop();

        return crtStmt.execute(this);
    }

    public Boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }


    public String toString() {
        return "Thread ID:" + threadId + "\n" + "Stack: " + exeStack.toString() + "\n" + "SymTable: " + symTable.toString() + "\n" +
                "Out: " + out.toString() + "\n" + "Heap: " + heapTable.toString() + '\n';
    }

    public String toFile() {
        return "----------------------------------------------------------------------------\n" +
                "Thread ID:" + threadId + "\n" +
                "ExeStack:" + exeStack.toFile() + "\n" + "\nSymTable: \n" + symTable.toFile() + "\n" +
                "Out: \n" + out.toFile() + "\n" + "FileTable: \n" + fileTable.toFile() + "\n" +
                "Heap: \n" + heapTable.toFile() + "\n";
    }
}