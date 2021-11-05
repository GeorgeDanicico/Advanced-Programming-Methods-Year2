package Model.value;

import Model.types.IType;
import Model.types.StringType;

public class StringValue implements IValue{
    private final String value;

    public StringValue() {
        value = "";
    }

    public StringValue(String _value) {
        value = _value;
    }

    public String getValue() { return this.value; }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(this.value);
    }

    @Override
    public String toString() {
        return "'" + this.value + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof StringValue) {
            StringValue o_value = (StringValue) o;
            return o_value.getValue().equals(this.value);
        }
        return false;
    }

    public boolean equals(StringValue s) {
        return this.value.equals(s.getValue());
    }
}
