package Week7;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class IteratorList {
    public static void nonSyncIterator(Collection<Integer> elements, int number){
        for(Integer ele : elements){
            System.out.println(ele + "  " + number);
        }
    }

    public static void syncIterator(Collection<Integer> elements, int number){
        synchronized (elements) {
            for(Integer ele : elements){
                System.out.println(ele + "  " + number); // when I remove ln here it runs for linkedList... why?... but now its not working....
            }
        }
    }
    public static void main(String[] args) {
        List<Integer> OrgElements = new ArrayList<>();
        // List<Integer> elements = new LinkedList<>();
        // Collection<Integer> OrgElements = new Vector<>();
        Collection<Integer> elements = Collections.synchronizedCollection(OrgElements);
        // List<Integer> elements = Collections.synchronizedList(OrgElements);

        Thread[] threads = new Thread[2];

        for(int i = 0; i < 2; i++){
            int finali = i;
            threads[i] = new Thread(() -> {
                for(int j = 0; j < 10; j++){
                    syncIterator(elements, finali);
                }
            });
        }

        for (Thread thread : threads) thread.start();

        for(int i = 0; i < 100_000; i++){
            elements.add(i);
        }

        try{
            for (Thread thread : threads) thread.join();
        }
        catch(InterruptedException e) {}
    }
}
