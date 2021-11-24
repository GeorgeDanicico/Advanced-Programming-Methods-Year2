package Model.value;

import Model.types.IType;
import Model.types.RefType;

public class RefValue implements IValue{
    private int address;
    private IType locationType;

    public RefValue(int _address, IType _locationType) {
        address = _address;
        locationType = _locationType;
    }

    public void setAddr(int newAddress) {
        this.address = newAddress;
    }

    public int getAddr() {
        return address;
    }

    public IType getLocType() {
        return this.locationType;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof RefValue) {
            RefValue oval = (RefValue) o;
            return oval.getAddr() == this.address;
        } else return false;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public IValue deepCopy() {
        return new RefValue(address, locationType);
    }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType.toString() + ")";
    }
}
