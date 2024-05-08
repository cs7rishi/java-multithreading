package thread.lockFreeAlgorithm;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceExample1 {
    public static void main(String[] args) {
        String oldName = "old Name";
        String newName = "new Name";

        AtomicReference<String> nameReference = new AtomicReference<>(oldName);

        nameReference.compareAndSet(oldName, newName);
        nameReference.set("hopa");

        if (nameReference.compareAndSet(oldName, oldName)) {
            System.out.println("New Name: " + nameReference.get());
        } else {

            System.out.println("Nothing changed");
        }
    }
}
