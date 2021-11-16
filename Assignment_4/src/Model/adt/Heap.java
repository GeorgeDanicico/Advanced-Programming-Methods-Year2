package Model.adt;
import java.lang.Integer;

public class Heap<T1, T2> extends Dict<T1, T2>{
    private Integer freePosition;

    public Heap() {
        super();
        freePosition = 1;
    }

    public int add(T2 value) {
        if (!this.isDefined((T1)freePosition)) {
            int copy_freePos = freePosition;
            this.dictionary.put((T1)freePosition, value);
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
