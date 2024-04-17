package thread.joins;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Coordination {
    public static void main(String[] args) throws InterruptedException {

        List<Long> inputList = List.of(100000L, 56L, 33L,20L);
        //2 could not finish the execution being the first thread

        List<FactorialThread> threads = new ArrayList<>();
        for(Long input : inputList){
            threads.add(new FactorialThread(input));
        }

        //starting threads concurrently
        //Daemon
        //If daemon thread is not set, the long running thread will prevent
        // termination after the .join() time limit

        for(Thread thread: threads){
            thread.setDaemon(true);
            thread.start();
        }

        //join threads
        for(Thread thread: threads){
            thread.join(400);
        }

        for(FactorialThread thread : threads){
            if(thread.isFinished()){
                System.out.println("Factorial of " + thread.input + " : " + thread.getResult());
            }else{
                System.out.println("Factorial of " + thread.input + " : Pending" );
            }
        }
    }

    private static class FactorialThread extends Thread{
        private final long input;
        private boolean isFinished;
        private BigInteger result;

        FactorialThread(long input){
            this.input = input;
            this.isFinished = false;
            result = BigInteger.ZERO;
        }

        @Override
        public void run(){
            this.result = calculate(input);
            this.isFinished = true;
        }

        private BigInteger calculate(long input){
            BigInteger result =BigInteger.ONE;

            for(long i = input ; i > 0 ; i--){
                result = result.multiply(BigInteger.valueOf(i));
            }

            return result;
        }

        private boolean isFinished(){
            return isFinished;
        }

        private BigInteger getResult(){
            return result;
        }
    }
}
