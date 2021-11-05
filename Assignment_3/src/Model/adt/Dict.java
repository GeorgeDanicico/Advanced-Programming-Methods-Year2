package Model.adt;
import Exceptions.UndefinedException;
import Exceptions.VariableDefinedException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Dict<T1,T2> implements IDict<T1,T2> {
    Map<T1, T2> dictionary;

    public Dict() {
        dictionary = new HashMap<T1,T2>();
    }

    @Override
    public void add(T1 v1, T2 v2) throws Exception{
        if (!isDefined(v1)) {
            dictionary.put(v1, v2);
        } else throw new VariableDefinedException("Variable already defined");
    }

    @Override
    public void update(T1 v1, T2 v2) throws Exception{
        if (isDefined(v1)) {
            dictionary.replace(v1, v2);
        } else throw new UndefinedException("Variable not defined");
    }

    @Override
    public void remove(T1 v1) throws Exception{
        if (isDefined(v1)) {
            dictionary.remove(v1);
        } else throw new UndefinedException("Variable not defined");
    }

    @Override
    public T2 lookup(T1 id) throws Exception {
        if (isDefined(id)) {
            return dictionary.get(id);
        } else throw new UndefinedException("Variable not defined.");
    }

    @Override
    public boolean isDefined(T1 id) {
        return dictionary.containsKey(id);
    }

    @Override
    public String toString() {
        return dictionary.toString();
    }

    @Override
    public Collection<T2> values()
    {
        return dictionary.values();
    }

}
