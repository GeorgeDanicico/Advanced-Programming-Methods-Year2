package Model.adt;
import Exceptions.StackEmptyException;

import java.util.function.Consumer;

public interface IStack<T> {

    T pop() throws StackEmptyException;
    T top();
    void push(T v);
    boolean isEmpty();
    String toString();
    String toFile();
    void forEach(Consumer<? super T> action);
    IStack<T> deepCopy();
}

