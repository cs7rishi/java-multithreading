package thread.advanceLocking;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockExample {
    public static  final int HIGHEST_PRICE = 1000;

    public static void main(String[] args) throws InterruptedException {
        InventoryDatabase inventoryDatabase = new InventoryDatabase();

        Random random = new Random();
        for(int i= 0 ; i < 100000; i++){
            inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
        }

        Thread writer = new Thread(()->{
            while(true){
                inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
                inventoryDatabase.removeItem(random.nextInt(HIGHEST_PRICE));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        writer.setDaemon(true);
        writer.start();

        int numberOfThreads = 7;
        List<Thread> readers = new ArrayList<>();

        for(int readerIndex= 0 ; readerIndex < numberOfThreads ; readerIndex++){
            Thread reader = new Thread(()->{
                for(int i = 0 ; i < 100000; i++){
                    int upperBound = random.nextInt(HIGHEST_PRICE);
                    int lowerBound = upperBound > 0 ? random.nextInt((upperBound)) : 0;
                    inventoryDatabase.getNumberOfItemsInPriceRange(lowerBound,upperBound);
                }
            });

            reader.setDaemon(true);
            readers.add(reader);
        }

        long startReaderTime = System.currentTimeMillis();

        for(Thread reader : readers){
            reader.start();
        }

        for(Thread reader : readers){
            reader.join();
        }

        long endReadingTime = System.currentTimeMillis();

        System.out.println(String.format("Reading took %d ms :" , endReadingTime - startReaderTime));
    }

    private static class InventoryDatabase {
        private TreeMap<Integer, Integer> priceToCountMap = new TreeMap<>();
        private ReentrantLock lock = new ReentrantLock();
        private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        private Lock readLock = rwLock.readLock();
        private Lock writeLock = rwLock.writeLock();

        private int getNumberOfItemsInPriceRange(int lowerBound, int upperBound) {
            readLock.lock();
            try {

                Integer fromKey = priceToCountMap.ceilingKey(lowerBound);

                Integer toKey = priceToCountMap.floorKey(upperBound);

                if (fromKey == null || toKey == null) {
                    return 0;
                }

                NavigableMap<Integer, Integer> rangeOfPrices =
                    priceToCountMap.subMap(fromKey, true, toKey, true);

                int sum = 0;
                for (int numberOfItemsForPrice : rangeOfPrices.values()) {
                    sum += numberOfItemsForPrice;
                }

                return sum;
            } finally {
                readLock.unlock();
            }

        }

        private void addItem(int price) {
            writeLock.lock();
            try {
                Integer numberOfItemsForPrice = priceToCountMap.get(price);
                if (numberOfItemsForPrice == null) {
                    priceToCountMap.put(price, 1);
                } else {
                    priceToCountMap.put(price, numberOfItemsForPrice + 1);
                }
            } finally {
                writeLock.unlock();
            }
        }

        private void removeItem(int price) {
            writeLock.lock();
            try {
                Integer numberOfItemsForPrice = priceToCountMap.get(price);
                if (numberOfItemsForPrice == null || numberOfItemsForPrice == 1) {
                    priceToCountMap.remove(price);
                } else {
                    priceToCountMap.put(price, numberOfItemsForPrice - 1);
                }
            } finally {
                writeLock.unlock();
            }
        }
    }
}
