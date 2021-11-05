package Model.adt;
import Exceptions.StackEmptyException;

import java.util.ArrayList;
import java.util.Stack;

public class MyStack<T> implements IStack<T>{
    private Stack<T> stack;

    public MyStack() {
        stack = new Stack<T>();
    }

    public MyStack(Stack<T> _stack) {
        stack = (Stack<T>) _stack.clone();
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
                result += elem.toString() + "\n";
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
