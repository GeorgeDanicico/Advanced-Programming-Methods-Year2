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

public class AcquireToyStmt implements IStmt{
    private final String variableId;
    private static Lock lock = new ReentrantLock();

    public AcquireToyStmt(String _var) {
        variableId = _var;
    }

    @Override
    public String toString() {
        return "acquireToy(" + variableId + ")";
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
                int N1 = entry.getValue0();
                int N2 = entry.getValue2();
                ArrayList<Integer> list = entry.getValue1();
                int NL = list.size();
                if (N1 - N2 > NL) {
                    if (!list.contains(state.getId())) {
                        list.add(state.getId());
                    }
                } else {
                    stack.push(this);
                }

                lock.unlock();
            } else throw new UndefinedException("Semaphore index is not defined");
        } else throw new UndefinedException("Variable is not defined");


        return null;
    }
}
