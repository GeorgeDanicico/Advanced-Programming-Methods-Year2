package Model.types;

import Model.value.IValue;
import Model.value.RefValue;

public class RefType implements IType{
    private final IType inner;

    public RefType(IType _inner) {
        this.inner = _inner;
    }

    @Override
    public IValue defaultValue() {
        return new RefValue(0, inner);
    }

    public IType getInner() {
        return this.inner;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof RefType) {
            return inner.equals(((RefType) o).getInner());
        } else {
            return false;
        }
    }

    @Override
    public IType deepCopy() {
        return new RefType(this.inner);
    }

    @Override
    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }

}
