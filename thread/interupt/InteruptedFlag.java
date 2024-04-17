package thread.interupt;

import java.math.BigInteger;

public class InteruptedFlag {

    public static void main(String[] args) {
        Thread computationThread =
            new Thread(new BigComputation(new BigInteger("20000"), new BigInteger("1000000000")));

        computationThread.start();
        computationThread.interrupt();
    }

    private static class BigComputation implements Runnable {
        private BigInteger base;
        private BigInteger power;

        public BigComputation(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base + " power " + power + " : " + calculate(base, power));
        }

        private BigInteger calculate(BigInteger base, BigInteger power) {
            BigInteger ans = base;
            for (BigInteger i = BigInteger.ONE; i.compareTo(power) == -1; i = i.add(BigInteger.ONE)) {
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("Computation Thread Interrupted");
                    return BigInteger.ZERO;
                }
                ans = ans.multiply(base);
            }

            return ans;
        }
    }
}
