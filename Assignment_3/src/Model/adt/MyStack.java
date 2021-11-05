package Model.adt;
import Exceptions.StackEmptyException;

import java.util.Stack;

public class MyStack<T> implements IStack<T>{
    private Stack<T> stack;

    public MyStack() {
        stack = new Stack<T>();
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

    public String toString() {
        return stack.toString();
    }

    public String toFile() {
        return "";
    }
}
