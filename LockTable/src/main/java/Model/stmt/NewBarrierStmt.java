package Model.stmt;

import Exceptions.ExpressionException;
import Exceptions.UndefinedException;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.MyBarrierTable;
import Model.exp.Exp;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewBarrier implements IStmt{
    private final String variableId;
    private final Exp exp;
    private static Lock lock = new ReentrantLock();

    public NewBarrier(String _var, Exp _exp) {
        variableId = _var;
        exp = _exp;
    }

    @Override
    public String toString() {
        return "newBarrier(" + variableId + "; " + exp.toString() + ")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        if (!typeEnv.isDefined(variableId)) {
            throw new UndefinedException("Variable is not defined");
        }

        if (!typeEnv.lookup(variableId).equals(new IntType())) {
            throw new UndefinedException("Variable is not defined");
        }

        if (!exp.typecheck(typeEnv).equals(new IntType())) {
            throw new ExpressionException("Expression type is not int");
        }

        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> symTbl = state.getSymTable();
        Heap<Integer, IValue> heapTbl = (Heap<Integer, IValue>) state.getHeapTable();
        MyBarrierTable<Integer, Pair<Integer, ArrayList<Integer>>> barrierTable = (MyBarrierTable<Integer, Pair<Integer, ArrayList<Integer>>>) state.getSemaphoreTable();

        if (symTbl.isDefined(variableId)) {
            IValue val = exp.eval(symTbl, heapTbl);

            if (val.getType().equals(new IntType())) {

                lock.lock();
                int expVal = ((IntValue)val).getValue();
                int barrierIndex = barrierTable.getCurrentFreeAddress();
                barrierTable.add(barrierIndex, new Pair<Integer, ArrayList<Integer>>(expVal, new ArrayList<Integer>()));
                symTbl.update(variableId, new IntValue(barrierIndex));

                lock.unlock();
            } else throw new ExpressionException("Expression is not int");

        } else throw new UndefinedException("Variable is not defined");

        return null;
    }
}
