package Model.adt;
import Model.stmt.CompStmt;
import Model.stmt.IStmt;
import Exceptions.StackEmptyException;

import java.util.Stack;

public class MyStack<T> implements IStack<T>{
    private Stack<T> stack;

    public MyStack() {
        stack = new Stack<T>();
    }

    public MyStack(Stack<T> _stack) {
        stack = (Stack<T>) _stack.clone();
    }

    private String inOrderTraversal(IStmt stmt) {

        if (stmt instanceof CompStmt) {
            String result = "";
            IStmt left_branch = ((CompStmt) stmt).getFirst();
            IStmt right_branch = ((CompStmt) stmt).getSecond();

            if (left_branch instanceof CompStmt) {
                result += inOrderTraversal(left_branch);
            } else if (left_branch != null) result += '\n' + left_branch.toString();

            if (right_branch instanceof CompStmt) {
                result += inOrderTraversal(right_branch);
            } else if (right_branch != null) result += '\n' + right_branch.toString();

            return result;

        } else return '\n' + stmt.toString();
    }

    @Override
    public T pop() throws StackEmptyException {
        if (this.isEmpty()) {
            throw new StackEmptyException("Stack is empty.");
        }
        return this.stack.pop();
    }

    @Override
    public void push(T v) {
        this.stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return this.stack.empty();
    }
    @Override
    public String toString() {
        return stack.toString();
    }

    @Override
    public String toFile() {
        MyStack<T> copyStack = (MyStack<T>) this.deepCopy();
        String result = "";

        try {
            while (!copyStack.isEmpty()) {
                T elem = copyStack.pop();
                if (elem instanceof IStmt)
                    result += inOrderTraversal((IStmt) elem);
            }
        } catch (Exception e) {

        }
        return result;
    }

    @Override
    public IStack<T> deepCopy() {
        return new MyStack<T>(this.stack);
    }
}
