package Model.adt;

import Model.value.IValue;

public class Heap<T1> extends Dict<Integer, T1>{
    private int freePosition;

    public Heap() {
        super();
        freePosition = 1;
    }

    public int add(T1 value) {
        if (!this.isDefined(freePosition)) {
            int copy_freePos = freePosition;
            this.dictionary.put(freePosition, value);
            // increase the free position
            getNextFreeAddress();

            return copy_freePos;
        }
        return 0;
    }

    public void getNextFreeAddress() {
        freePosition++;
    }

}
