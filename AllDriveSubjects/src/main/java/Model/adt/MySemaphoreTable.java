package Model.adt;

public class MySemaphoreTable<T1, T2> extends Dict<T1, T2>{
    private Integer freePosition;

    public MySemaphoreTable() {
        super();
        freePosition = 1;
    }

    public synchronized int getCurrentFreeAddress() {
        findNextFreeAddress();
        return freePosition - 1;
    }

    public synchronized void findNextFreeAddress() {
        freePosition += 1;
    }
}
