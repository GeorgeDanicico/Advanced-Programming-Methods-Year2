package Model.adt;

public class MyLockTable<T1, T2> extends Dict<T1, T2>{
    private Integer freePosition;

    public MyLockTable() {
        super();
        freePosition = 1;
    }

    public synchronized int getCurrentFreeAddress() {
        return freePosition;
    }

    public synchronized void findNextFreeAddress() {
        freePosition += 1;
    }
}
