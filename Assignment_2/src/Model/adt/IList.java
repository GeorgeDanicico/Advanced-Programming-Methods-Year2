package Model.adt;

import java.util.Iterator;

public interface IList<T> {
    void add(T v);
    T pop();
    String toString();
    T getValue(int position);
    boolean empty();
    void clear();
    int size();
    Iterator<T> getIterator();
}
