package Model.adt;

import Exceptions.ListException;

import java.util.Iterator;
import java.util.List;

public interface IList<T> {
    void add(T v);
    T pop() throws Exception;
    T peek() throws Exception;
    String toString();
    String toFile();
    T getValue(int position) throws Exception;
    boolean empty();
    void clear();
    int size();
    List<T> getAll();
    T get(int index) throws Exception;
    Iterator<T> getIterator();
}
