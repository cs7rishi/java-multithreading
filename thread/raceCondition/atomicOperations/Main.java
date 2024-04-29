package thread.raceCondition.atomicOperations;

import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Metrics metrics = new Metrics();

        BusinessLogic businessLogic1 = new BusinessLogic(metrics);
        BusinessLogic businessLogic2 = new BusinessLogic(metrics);

        MetricPrinter metricPrinter = new MetricPrinter(metrics);

        businessLogic1.start();
        businessLogic2.start();
        metricPrinter.start();
    }

    public static class MetricPrinter extends Thread{
        private Metrics metrics;
        public MetricPrinter(Metrics metrics){
            this.metrics = metrics;
        }

        @Override
        public void run(){
            while(true){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                double currentAverage = metrics.getAverage();
                System.out.println("Current Average is " + currentAverage);
            }
        }
    }
    public static class BusinessLogic extends Thread {
        private Metrics metrics;
        private Random random = new Random();

        public BusinessLogic(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while (true) {
                long start = System.currentTimeMillis();
                try {
                    Thread.sleep(random.nextInt(0, 10));
                } catch (InterruptedException ex) {

                }

                long end = System.currentTimeMillis();
                metrics.addSample(end - start);
            }
        }
    }


    private static class Metrics {
        private volatile long count = 0;
        private volatile double average = 0.0;

        public synchronized void addSample(long sample) {
            double currSum = average * count;
            count++;
            average = (currSum + sample) / count;
        }

        public double getAverage() {
            return this.average;
        }
    }

}
