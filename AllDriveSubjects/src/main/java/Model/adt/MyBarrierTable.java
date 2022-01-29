package Model.adt;

public class MyBarrierTable<T1, T2> extends Dict<T1, T2>{
    private Integer freePosition;

    public MyBarrierTable() {
        super();
        freePosition = 1;
    }

    public synchronized int getCurrentFreeAddress() {
        findNextFreeAddress();
        return freePosition - 1;
    }

    private synchronized void findNextFreeAddress() {
        freePosition += 1;
    }
}
