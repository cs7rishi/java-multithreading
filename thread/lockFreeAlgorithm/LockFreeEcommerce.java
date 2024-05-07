package thread.lockFreeAlgorithm;

import java.util.concurrent.atomic.AtomicInteger;

public class LockFreeEcommerce {
    public static void main(String[] args) throws InterruptedException {
        InventoryCounter iv = new InventoryCounter();

        Thread thread1 = new IncrementThread(iv);
        Thread thread2 = new DecrementThread(iv);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(iv.getItems());
    }

    private static class IncrementThread extends Thread {
        InventoryCounter inventoryCounter;

        IncrementThread(InventoryCounter inventoryCounter){
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run(){
            for(int i = 0 ; i < 1000; i++){
                inventoryCounter.increment();
            }
        }
    }

    private static class DecrementThread extends Thread{
        InventoryCounter inventoryCounter;

        DecrementThread(InventoryCounter inventoryCounter){
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run(){
            for(int i = 0 ; i < 1000; i++){
                inventoryCounter.decrement();
            }
        }
    }

    private static class InventoryCounter{
        AtomicInteger counter;

        InventoryCounter(){
            this.counter = new AtomicInteger(0);
        }

        private void increment(){
            this.counter.incrementAndGet();
        }

        private void decrement(){
            this.counter.decrementAndGet();
        }

        private int getItems(){
            return this.counter.get();
        }
    }

}
