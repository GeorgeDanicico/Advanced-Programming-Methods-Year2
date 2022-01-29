package Model.stmt;

import Exceptions.ExpressionException;
import Exceptions.UndefinedException;
import Exceptions.VariableTypeException;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.adt.MySemaphoreTable;
import Model.exp.Exp;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReleaseToyStmt implements IStmt{
    private final String variableId;
    private static Lock lock = new ReentrantLock();

    public ReleaseToyStmt(String _var) {
        variableId = _var;
    }

    @Override
    public String toString() {
        return "releaseToy(" + variableId + ")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {

        if (!typeEnv.isDefined(variableId)) {
            throw new UndefinedException("Variable is not defined");
        }

        if (!typeEnv.lookup(variableId).equals(new IntType())) {
            throw new VariableTypeException("Variable is not of type int");
        }

        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> symTbl = state.getSymTable();
        Heap<Integer, IValue> heapTbl = (Heap<Integer, IValue>) state.getHeapTable();
        IStack<IStmt> stack = state.getStack();
        MySemaphoreTable<Integer, Triplet<Integer, ArrayList<Integer>, Integer>> toySemaphoreTbl =
                (MySemaphoreTable<Integer, Triplet<Integer, ArrayList<Integer>, Integer>>) state.getToySemaphoreTable();

        if (symTbl.isDefined(variableId)) {
            int foundIndex = ((IntValue)symTbl.lookup(variableId)).getValue();
            if (toySemaphoreTbl.isDefined(foundIndex)) {
                lock.lock();
                Triplet<Integer, ArrayList<Integer>, Integer> entry = toySemaphoreTbl.lookup(foundIndex);
                ArrayList<Integer> list = entry.getValue1();
                if (list.contains(state.getId())) {
                    list.remove(Integer.valueOf(state.getId()));
                }

                lock.unlock();
            } else throw new UndefinedException("Semaphore index is not defined");
        } else throw new UndefinedException("Variable is not defined");


        return null;
    }
}
