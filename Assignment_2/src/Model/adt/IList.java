package Model.adt;

import Exceptions.ListException;

import java.util.Iterator;

public interface IList<T> {
    void add(T v);
    T pop() throws Exception;
    String toString();
    T getValue(int position) throws Exception;
    boolean empty();
    void clear();
    int size();
    Iterator<T> getIterator();
}
