package thread.creation;

public class ThreadCreation {
    public static void main(String[] args) throws InterruptedException {
        //Scheduling the new thread take times by the OS
        //Happend Asynchronously by the OS
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                //Code will run in new thread
                System.out.println("We are in the new thread " + Thread.currentThread().getName());
                System.out.println(
                    "Current Thread priority is " + Thread.currentThread().getPriority());
            }
        });

        //To set the Name of the Thread
        thread.setName("New Worker Thread");
        //To set the Priority of the Thread
        thread.setPriority(Thread.MAX_PRIORITY);

        System.out.println("We are in thread: " + Thread.currentThread()
            .getName() + " before starting the new thread");
        thread.start();
        //this will instruct the JVM to start the new thread
        //and pass it to the operating system

        System.out.println("We are in thread: " + Thread.currentThread()
            .getName() + " after starting the new thread");

        //instruct the OS to not schedule this thread
        //until this time has passed
        Thread.sleep(10000);

    }
}
