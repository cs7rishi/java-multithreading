package thread.creation.example;

public class ThreadExceptionHandling {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                throw new RuntimeException("New Worker Exception");
            }
        });

        thread.setName("New Thread");
        //Handling Unchecked Exception at runtime
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(
                    "Critical error happened in new thread " + t.getName() + " the error is " + e.getMessage());
            }
        });
        //Start the thread
        thread.start();

        //Make current thread sleep, so that new thread could finish execution
        Thread.sleep(1000);
    }
}
