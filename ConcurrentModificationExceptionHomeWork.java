package Week7;

import java.util.ArrayList;
// import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentModificationExceptionHomeWork {
    public static void main(String[] args) {
        List<Integer> original = new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        // List<Integer> result = Collections.synchronizedList(new ArrayList<>());

        int THREAD_COUNT = 2;
        int ELEMS_PER_THREAD = 100_000;
        int TotalElements = ELEMS_PER_THREAD * THREAD_COUNT;
        ExecutorService es = Executors.newFixedThreadPool(THREAD_COUNT);

        for(int i = 1; i < TotalElements; i++){
            original.add(i);
        }

        // for(int i = 0; i < 2; i++){
        //     es.submit(()->{
        //         for(int j = 0; j < ELEMS_PER_THREAD; j++){
        //             // synchronized (es) {
        //                 int firstElement = original.remove(0);
        //                 result.add(firstElement);
        //             // }
        //         }
        //     });
        // }


        // final Object lock = new Object();


        es.submit(()->{
            // synchronized (es) {
                    for(int j = 0; j < ELEMS_PER_THREAD; j++){
                        if (!original.isEmpty()) {
                            original.remove(0);
                        }
                    }
            // }
        });

        
        es.submit(()->{
                // synchronized (es) {
                try {
                    for (Integer value : original) {
                        result.add(value);
                    }
                } catch (ConcurrentModificationException e) {
                    e.printStackTrace();
                }
                // }
        });
           
        es.shutdown();

        try {
            es.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // TODO: handle exception
        }

        for(int i = 0; i < 100; i++){
            System.out.println(result.get(i));
        }
    }
}
