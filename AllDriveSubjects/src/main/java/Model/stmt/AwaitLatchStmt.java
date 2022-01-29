package Model.stmt;

import Exceptions.UndefinedException;
import Exceptions.VariableTypeException;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.adt.MyLatchTable;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitLatchStmt implements IStmt{
    private final String variableId;
    private static Lock lock = new ReentrantLock();

    public AwaitLatchStmt(String _var) {
        variableId = _var;
    }

    @Override
    public String toString() {
        return "await(" + variableId + ")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        if (!typeEnv.isDefined(variableId) || !typeEnv.lookup(variableId).equals(new IntType())) {
            throw new VariableTypeException("Variable not defined or not int type3");
        }

        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> symTbl = state.getSymTable();
        Heap<Integer, IValue> heapTbl = (Heap<Integer, IValue>) state.getHeapTable();
        IStack<IStmt> stack = state.getStack();
        MyLatchTable<Integer, Integer> latchTbl = (MyLatchTable<Integer, Integer>)state.getLatchTable();

        if (symTbl.isDefined(variableId)) {
            IntValue foundValue = (IntValue) symTbl.lookup(variableId);

            if (latchTbl.isDefined(foundValue.getValue())) {
                lock.lock();
                int latchVal = latchTbl.lookup(foundValue.getValue());

                if (latchVal != 0) {
                    stack.push(this);
                }
                lock.unlock();
            } else throw new UndefinedException("Variable is not defined in the latch table");

        } else throw new UndefinedException("Variable is not defined");

        return null;
    }
}
