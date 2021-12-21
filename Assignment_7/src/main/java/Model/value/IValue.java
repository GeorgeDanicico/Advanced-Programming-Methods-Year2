package Model.value;

import Model.types.IType;

public interface IValue {
    IType getType();
    IValue deepCopy();
    String toString();
    boolean equals(Object o);
}
