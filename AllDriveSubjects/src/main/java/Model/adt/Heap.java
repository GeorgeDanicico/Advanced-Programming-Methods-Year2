package Model.adt;
import java.lang.Integer;

public class Heap<T1, T2> extends Dict<T1, T2>{
    private Integer freePosition;

    public Heap() {
        super();
        freePosition = 1;
    }

    public int getCurrentFreeAddress() { return this.freePosition; }

    public void findNextFreeAddress() { freePosition++; };

}
