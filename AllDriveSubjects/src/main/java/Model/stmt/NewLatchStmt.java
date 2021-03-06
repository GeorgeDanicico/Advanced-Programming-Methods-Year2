package Model.stmt;

import Exceptions.UndefinedException;
import Exceptions.VariableTypeException;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.adt.MyLatchTable;
import Model.exp.Exp;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLatchStmt implements IStmt{
    private final String variableId;
    private final Exp exp;
    private static Lock lock = new ReentrantLock();

    public NewLatchStmt(String _var, Exp _exp) {
        variableId = _var;
        exp = _exp;
    }
    @Override
    public String toString() {
        return "newLatch(" + variableId + "; " + exp.toString() + ")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        if (!typeEnv.isDefined(variableId)) {
            throw new UndefinedException("Variable is not defined1");
        }

        if (!typeEnv.lookup(variableId).equals(new IntType()) || !exp.typecheck(typeEnv).equals(new IntType())) {
            throw new VariableTypeException("Type should be int");
        }

        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> symTbl = state.getSymTable();
        Heap<Integer, IValue> heapTbl = (Heap<Integer, IValue>) state.getHeapTable();
        IStack<IStmt> stack = state.getStack();
        MyLatchTable<Integer, Integer> latchTbl = (MyLatchTable<Integer, Integer>)state.getLatchTable();

        IntValue val = (IntValue)exp.eval(symTbl, heapTbl);

        IntValue varValue = (IntValue)symTbl.lookup(variableId);
        lock.lock();
        int latchTablePosition = latchTbl.getCurrentFreeAddress();
        latchTbl.findNextFreeAddress();
        latchTbl.add(latchTablePosition, varValue.getValue());
        symTbl.update(variableId, new IntValue(latchTablePosition));
        lock.unlock();

        return null;
    }
}
