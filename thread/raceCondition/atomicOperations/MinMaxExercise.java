package thread.raceCondition.atomicOperations;

public class MinMaxExercise {

    volatile long minNo;
    volatile long maxNo;

    public MinMaxExercise() {
        minNo = Long.MAX_VALUE;
        maxNo = Long.MIN_VALUE;
    }

    public void addSample(long newSample) {
        synchronized (this) {
            if (minNo == Long.MAX_VALUE) {
                minNo = newSample;
            }
            if (maxNo == Long.MIN_VALUE) {
                maxNo = newSample;
            }

            minNo = Math.min(minNo, newSample);
            maxNo = Math.max(maxNo, newSample);
        }
    }

    public long getMin() {
        return minNo;
    }

    public long getMax() {
        return maxNo;
    }
}
