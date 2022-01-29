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

public class NewToySemaphoreStmt implements IStmt{
    private final String variableId;
    private final Exp exp1, exp2;
    private static Lock lock = new ReentrantLock();

    public NewToySemaphoreStmt(String _var, Exp _exp1, Exp _exp2) {
        variableId = _var;
        exp1 = _exp1;
        exp2 = _exp2;
    }

    @Override
    public String toString() {
        return "newToySemaphore(" + variableId + "; " + exp1.toString() + "; "
                + exp2.toString() + ")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {

        if (!typeEnv.isDefined(variableId)) {
            throw new UndefinedException("Variable is not defined");
        }

        if (!typeEnv.lookup(variableId).equals(new IntType())) {
            throw new VariableTypeException("Variable is not of type int");
        }

        if (!exp1.typecheck(typeEnv).equals(new IntType()) || !exp2.typecheck(typeEnv).equals(new IntType())) {
            throw new ExpressionException("Expressions type is not int");
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
            IValue number1 = exp1.eval(symTbl, heapTbl);
            IValue number2 = exp2.eval(symTbl, heapTbl);

            if (number1.getType().equals(new IntType()) && number2.getType().equals(new IntType())) {
                lock.lock();
                int freePosition = toySemaphoreTbl.getCurrentFreeAddress();
                toySemaphoreTbl.add(freePosition, new Triplet<>(
                                ((IntValue)number1).getValue(), new ArrayList<>(), ((IntValue)number2).getValue()
                ));
                symTbl.update(variableId, new IntValue(freePosition));
                lock.unlock();
            } else throw new ExpressionException("Expression type is not int");
        } else throw new UndefinedException("Variable is not defined");


        return null;
    }
}
