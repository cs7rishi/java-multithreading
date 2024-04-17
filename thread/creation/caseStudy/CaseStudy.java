package thread.creation.caseStudy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CaseStudy {
    private static int MAX_PASSWORD = 99999;
    public static void main(String[] args) {
        Random random = new Random();

        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));
        List<Thread> threads = new ArrayList<>();
        threads.add(new AscendingHacker(vault));
        threads.add(new DescendingHacker(vault));
        threads.add(new PoliceThread());

        for(Thread t : threads){
            t.start();
        }
    }

    private static class Vault {
        int password;
        Vault(int password){
            this.password = password;
        }

        boolean isCorrectPassword(int guess){
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return this.password == guess;
        }
    }

    private static abstract class HackerThread extends Thread{
        protected Vault vault;

        HackerThread(Vault vault){
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(MAX_PRIORITY);
        }

        @Override
        public void start(){
            System.out.println("Starting thread " + this.getName());
            super.start();
        }
    }

    private static class AscendingHacker extends HackerThread{

        AscendingHacker(Vault vault) {
            super(vault);
        }

        @Override
        public void run(){
            for(int guess = 0 ; guess < MAX_PASSWORD ; guess++){
                if(vault.isCorrectPassword(guess)){
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class DescendingHacker extends HackerThread{
        DescendingHacker(Vault vault){
            super(vault);
        }

        @Override
        public void run(){
            for(int guess = MAX_PASSWORD ; guess >= 0 ; guess--){
                if(vault.isCorrectPassword(guess)){
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class PoliceThread extends Thread{
        @Override
        public void run(){
            for(int i = 100 ; i > 0; i--){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(i);
            }
            System.out.println("Game over for you hackers");
            System.exit(0);
        }
    }
}
