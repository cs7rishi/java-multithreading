package thread.lockFreeAlgorithm;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerExample {
    public static void main(String[] args) {
        int initialValue = 0;
        AtomicInteger atomicInteger = new AtomicInteger(initialValue);

        int delta = 5;


        //atomically add any integer
        atomicInteger.addAndGet(delta); // return new value
        atomicInteger.getAndAdd(delta); // return previous value

        //atomically increment the integer by one
        atomicInteger.incrementAndGet(); // return the new value
        atomicInteger.getAndIncrement(); // return the previous value

        //Similarly
        atomicInteger.decrementAndGet();
        atomicInteger.getAndDecrement();



    }
}
