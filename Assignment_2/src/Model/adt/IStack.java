package Model.adt;
import Exceptions.StackEmptyException;

public interface IStack<T> {

    T pop() throws StackEmptyException;
    void push(T v);
    boolean isEmpty();
    String toString();
}

