package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.*;
import java.util.Random;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    /** Testing construction timing **/
    Random r = new Random(500);
    @Test
    public void testConstructor() {
        ArrayHeapMinPQ<Integer> ahmpq = new ArrayHeapMinPQ<>();
    }

    @Test
    public void testConstructionTiming() {
        int[] Ns = new int[]{31250, 62500, 125000, 250000, 500000, 1000000, 2000000, 4000000, 8000000, 16000000};
        for (int N : Ns) { addN(N);}
    }

    @Test
    public void testNaiveConstruction() {
        int[] Ns = new int[]{31250, 62500, 125000, 250000, 500000, 1000000, 2000000};
        for (int N : Ns) { naiveAddN(N);}
    }


    private ArrayHeapMinPQ addN(int N) {
        Stopwatch ahmpqsw = new Stopwatch();
        ArrayHeapMinPQ<Double> ahmpq = new ArrayHeapMinPQ<>();
        for (int i = 0; i < N; i++) {
            ahmpq.add(r.nextDouble() * 1000, r.nextDouble() * 1000);
        }
        double ahmpqTime = ahmpqsw.elapsedTime();
  //      System.out.println("Time for ArrayHeapMinPQ construction and " + N + " items added: " + ahmpqTime);

        return ahmpq;
    }

    private NaiveMinPQ naiveAddN(int N) {
        Stopwatch ahmpqsw = new Stopwatch();
        NaiveMinPQ<Double> nmpq = new NaiveMinPQ<>();
        for (int i = 0; i < N; i++) {
            nmpq.add(r.nextDouble() * 1000, r.nextDouble() * 1000);
        }
        double nmpqTime = ahmpqsw.elapsedTime();
  //      System.out.println("Time for Naive construction and " + N + " items added: " + nmpqTime);

        return nmpq;
    }

    /** Testing accuracy for add and contains **/

    @Test
    public void testAddAccuracy() {
        int[] Ns = new int[]{31250, 62500, 125000, 250000, 500000, 1000000, 2000000};
        for (int N : Ns) {addAccuracy(N);}
    }

    private void addAccuracy(int N) {
        Double[][] itemsAndPriorities = createNItems(500000);
        Double[] items = itemsAndPriorities[0]; Double[] priorities = itemsAndPriorities[1];
        ArrayHeapMinPQ<Double> ahmpq = new ArrayHeapMinPQ<>();

        for (int i = 0; i < 500000; i++) {
            ahmpq.add(items[i], priorities[i]);
        }

        Stopwatch ahmpqsw = new Stopwatch();
        for (int i = 0; i < N; i++) {
            if (i < items.length) assertTrue(ahmpq.contains(items[i]));
            else assertFalse(ahmpq.contains(r.nextDouble() * 100000));
        }
        double nmpqTime = ahmpqsw.elapsedTime();
        System.out.println("Time for contains for " + N + " items: " + nmpqTime);
    }

    private Double[][] createNItems(int N) {
        Double[] items = new Double[N];
        Double[] priorities = new Double[N];

        for (int i = 0; i < N; i++) {
            items[i] = r.nextDouble() * 1000;
            priorities[i] = r.nextDouble() * 1000;
        }
        return new Double[][]{items, priorities};
    }

    /** Testing timing for removeSmallest **/
    @Test
    public void testRemoveTiming() {
        int[] Ns = new int[]{31250, 62500, 125000, 250000, 500000, 1000000, 2000000};
        for (int N : Ns) {testRemoveTimingOnce(N);}
    }

    private void testRemoveTimingOnce(int N) {
        ArrayHeapMinPQ<Double> added = addN(N);
        Stopwatch ahmpqsw = new Stopwatch();
        for (int i = 0; i < 10000; i++) {
            added.removeSmallest();
        }
        double nmpqTime = ahmpqsw.elapsedTime();
        System.out.println("Time for remove 10000 times for PQ of " + N + " items: " + nmpqTime);
    }

    /** Testing accuracy for removeSmallest **/
    @Test
    public void testRemoveAccuracy() {
        int[] Ns = new int[]{100, 200, 400, 800, 1600, 3200, 6400, 12800, 25600, 51200, 102400};
        for (int N : Ns) {testRemoveNTimes(N);}
    }

    private void testRemoveNTimes(int N) {
        Double[][] itemsAndPriorities = createNItems(102400);
        Double[] items = itemsAndPriorities[0]; Double[] priorities = itemsAndPriorities[1];
        ArrayHeapMinPQ<Double> ahmpq = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Double> nmpq = new NaiveMinPQ<>();

        for (int i = 0; i < 102400; i++) {
            ahmpq.add(items[i], priorities[i]);
            nmpq.add(items[i], priorities[i]);
        }

        for (int i = 0; i < N; i++) {
            assertEquals(ahmpq.removeSmallest(), nmpq.removeSmallest());
        }
        System.out.println("Ok for " + N);
    }

    /** Testing accuracy for getSmallest **/
    @Test
    public void testGetSmallest() {
        int[] Ns = new int[]{100, 200, 400, 800, 1600, 3200, 6400, 12800, 25600, 51200, 102400};
        for (int N : Ns) {testSmallestNTimes(N);}
    }

    private void testSmallestNTimes(int N) {
        Double[][] itemsAndPriorities = createNItems(102400);
        Double[] items = itemsAndPriorities[0]; Double[] priorities = itemsAndPriorities[1];
        ArrayHeapMinPQ<Double> ahmpq = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Double> nmpq = new NaiveMinPQ<>();

        for (int i = 0; i < 102400; i++) {
            ahmpq.add(items[i], priorities[i]);
            nmpq.add(items[i], priorities[i]);
        }

        for (int i = 0; i < N; i++) {
            assertEquals(ahmpq.getSmallest(), nmpq.getSmallest());
            ahmpq.removeSmallest(); nmpq.removeSmallest();
        }
        System.out.println("Ok for " + N);
    }

    /** Testing timing for getSmallest **/

    @Test
    public void getSmallestTiming() {
        int[] Ns = new int[]{31250, 62500, 125000, 250000, 500000, 1000000, 2000000};
        for (int N : Ns) {
            getSmallestTimingOnce(N);
            getSmallestNaive(N/20);
        }
    }

    private void getSmallestTimingOnce(int N) {
        ArrayHeapMinPQ<Double> added = addN(N);
        Stopwatch ahmpqsw = new Stopwatch();
        for (int i = 0; i < 10000; i++) {
            added.getSmallest();
        }
        double nmpqTime = ahmpqsw.elapsedTime();
        System.out.println("Time for getSmallest 10000 times for PQ of " + N + " items: " + nmpqTime);
    }

    private void getSmallestNaive(int N) {
        NaiveMinPQ<Double> added = naiveAddN(N);
        Stopwatch ahmpqsw = new Stopwatch();
        for (int i = 0; i < 10000; i++) {
            added.getSmallest();
        }
        double nmpqTime = ahmpqsw.elapsedTime();
        System.out.println("Time for getSmallest 10000 times for Naive PQ of " + N + " items: " + nmpqTime);
    }

    /** Testing accuracy for changePriority **/

  //  @Test
//    public void testChangePriority() {
//        changePriorityNTimes(10000);
//    }
//
//    private void changePriorityNTimes(int N) {
//        Double[][] itemsAndPriorities = createNItems(N);
//        Double[] items = itemsAndPriorities[0]; Double[] priorities = itemsAndPriorities[1];
//        ArrayHeapMinPQ<Double> ahmpq = new ArrayHeapMinPQ<>();
//        NaiveMinPQ<Double> nmpq = new NaiveMinPQ<>();
//        HashSet<Double> alreadyAdded = new HashSet<>();
//
//
//        for (int i = 0; i < N; i++) {
//            ahmpq.add(items[i], priorities[i]);
//            nmpq.add(items[i], priorities[i]);
//            alreadyAdded.add(items[i]);
//        }
//
//
//        for (int i = 0; i < N; i++) {
//            int index = r.nextInt(alreadyAdded.size());
//            Iterator<Double> iter = alreadyAdded.iterator();
//            for (int j = 0; j < index; j++) {
//                iter.next();
//            }
//            Double item = iter.next();
//            Double newPriority = r.nextDouble();
//            Double oldPriority = (ahmpq.gethm().get(item)).getPriority();
//            int oldIndex = (ahmpq.gethm().get(item)).getIndex();
//            ahmpq.changePriority(item, newPriority);
//            nmpq.changePriority(item, newPriority);
//            System.out.println(oldIndex + " " + (ahmpq.gethm().get(item)).getIndex());
//            assertFalse((ahmpq.gethm().get(item)).getPriority() == oldPriority);
//            assertTrue(newPriority == (ahmpq.gethm().get(item)).getPriority());
//            assertTrue((ahmpq.gethm().get(item)).getPriority() == nmpq.getPriority(item));
//        }
//    }

    /** Testing timing for changePriority **/

    @Test
    public void changePriorityTiming() {
        int[] Ns = new int[]{31250, 62500, 125000, 250000, 500000, 1000000, 2000000};
        for (int N : Ns) {changePriorityTimingOnce(N);}
    }


    private void changePriorityTimingOnce(int N) {
        int numCalls = 10000;
        Double[][] itemsAndPriorities = createNItems(N);
        Double[] items = itemsAndPriorities[0]; Double[] priorities = itemsAndPriorities[1];
        ArrayHeapMinPQ<Double> ahmpq = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Double> nmpq = new NaiveMinPQ<>();

        for (int i = 0; i < N; i++) {
            ahmpq.add(items[i], priorities[i]);
            nmpq.add(items[i], priorities[i]);
        }

        Double newPriority;

        Stopwatch ahmpqsw = new Stopwatch();
        for (int i = 0; i < numCalls; i++) {
            newPriority = - (double) i - 1;
            ahmpq.changePriority(items[i], newPriority);
        }
        double nmpqTime = ahmpqsw.elapsedTime();
        System.out.println("Time for changePriority 10000 calls " + N + " items: " + nmpqTime);
    }
    /** Testing accuracy for all in random orders **/

    @Test
    public void testAll() {
        int[] Ns = new int[]{100, 200, 400, 800, 1600, 3200, 6400, 12800, 25600, 51200, 102400};
        for (int N : Ns) {testNRandomOperations(N);}
    }

    private void testNRandomOperations(int N) {
        int numItems = 5000;

        Double[][] itemsAndPriorities = createNItems(numItems);
        Double[] items = itemsAndPriorities[0]; Double[] priorities = itemsAndPriorities[1];
        ArrayHeapMinPQ<Double> ahmpq = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Double> nmpq = new NaiveMinPQ<>();
        HashSet<Double> alreadyAdded = new HashSet<>();

        for (int i = 0; i < numItems; i++) {
            ahmpq.add(items[i], priorities[i]);
            nmpq.add(items[i], priorities[i]);
            alreadyAdded.add(items[i]);
        }
        System.out.println("New");
        int chooseFunc;
        for (int i = 0; i < N; i ++) {
            chooseFunc = r.nextInt(6);
           // System.out.println(chooseFunc);
            if (chooseFunc == 0) {
                add(ahmpq, nmpq, alreadyAdded, items);
            } else if (chooseFunc == 1) {
                removeSmallest(ahmpq, nmpq, alreadyAdded);
            } else if (chooseFunc == 2) {
                contains(ahmpq, nmpq);
            } else if (chooseFunc == 3) {
                getSmallest(ahmpq, nmpq);
            } else if (chooseFunc == 4) {
                size(ahmpq, nmpq);
            } else if (chooseFunc == 5) {
                changePriority(ahmpq, nmpq, alreadyAdded);
            }
        }
        System.out.println("Ok for " + N + ". Congratulations!");
    }

    private void add(ArrayHeapMinPQ a, NaiveMinPQ n, HashSet alreadyAdded, Double[] items) {
        Double item = (r.nextDouble() * 1000) - 500;
        Double priority = (r.nextDouble() * 1000) - 500;

        if (!alreadyAdded.contains(item)) {
            a.add(item, priority);
            n.add(item, priority);
            alreadyAdded.add(item);
        }
    }

    private void removeSmallest(ArrayHeapMinPQ<Double> a, NaiveMinPQ<Double> n, HashSet<Double> items) {
        if (a.size() != 0) {
            Double item = a.getSmallest();
            assertEquals(a.removeSmallest(), n.removeSmallest());
            items.remove(item);
        }
    }

    private void contains(ArrayHeapMinPQ a, NaiveMinPQ n) {
        Double toCheck = (r.nextDouble() * 1000) - 500;
        assertEquals(a.contains(toCheck), n.contains(toCheck));
    }

    private void getSmallest(ArrayHeapMinPQ a, NaiveMinPQ n) {
        if (a.size() != 0) {
            assertEquals(n.getSmallest(), a.getSmallest());
        }
    }

    private void size(ArrayHeapMinPQ a, NaiveMinPQ n) {
        assertEquals(a.size(), n.size());
    }

    private void changePriority(ArrayHeapMinPQ a, NaiveMinPQ n, HashSet<Double> set) {
        // @source: for picking random element from HashSet
        // https://stackoverflow.com/questions/124671/picking-a-random-element-from-a-set
        if (set.size() > 0) {
            int index = r.nextInt(set.size());
            Iterator<Double> iter = set.iterator();
            for (int i = 0; i < index; i++) {
                iter.next();
            }

            Double item = iter.next();
            Double newPriority = (r.nextDouble() * 1000) - 500;
            a.changePriority(item, newPriority);
            n.changePriority(item, newPriority);
        }
    } //323.91261356878806
    @Test
    public void testAddRemoveSmallest() {
        ArrayHeapMinPQ<Integer> ahmpq = new ArrayHeapMinPQ<>();
        ahmpq.add(2, 2);
        ahmpq.add(12, 12);
        ahmpq.add(1, 1);
        ahmpq.add(6, 6);
        ahmpq.add(13, 13);
        ahmpq.add(3, 3);
        ahmpq.add(8, 8);
        ahmpq.add(5, 5);
        ahmpq.add(10, 10);
        ahmpq.add(7, 7);
        ahmpq.add(9, 9);
        ahmpq.add(0, 0);
        int size = ahmpq.size();
        for(int i = 0; i < size; i++)
            ahmpq.removeSmallest();
    }
    @Test
    public void testAddContains() {
        ArrayHeapMinPQ<Integer> ahmpq = new ArrayHeapMinPQ<>();
        ahmpq.add(2, 2);
        ahmpq.add(12, 12);
        ahmpq.add(1, 1);
        ahmpq.add(6, 6);
        ahmpq.add(13, 13);
        ahmpq.add(3, 3);
        ahmpq.add(8, 8);
        ahmpq.add(5, 5);
        ahmpq.add(10, 10);
        ahmpq.add(7, 7);
        ahmpq.add(9, 9);
        ahmpq.add(0, 0);
        ahmpq.add(13, 13); // should error here
        ahmpq.add(14, 14);
        ahmpq.add(15, 15);
        ahmpq.add(16, 16);
        ahmpq.add(-1, -1);
        ahmpq.add(0, 1);
        assertTrue(ahmpq.contains(2));
        assertTrue(ahmpq.contains(12));
        assertTrue(ahmpq.contains(1));
        assertTrue(ahmpq.contains(6));
        assertTrue(ahmpq.contains(13));
        assertTrue(ahmpq.contains(3));
        assertTrue(ahmpq.contains(8));
        assertTrue(ahmpq.contains(5));
        assertTrue(ahmpq.contains(10));
        assertTrue(ahmpq.contains(7));
        assertTrue(ahmpq.contains(9));
        assertTrue(ahmpq.contains(0));
        assertFalse(ahmpq.contains(null));
        assertFalse(ahmpq.contains(19));
        assertFalse(ahmpq.contains(432));
        assertFalse(ahmpq.contains(-2));
    }
    @Test
    public void testChangePrioritySmall(){
        ArrayHeapMinPQ<Integer> ahmpq = new ArrayHeapMinPQ<>();
        ahmpq.add(2, 2);
        ahmpq.add(12, 12);
        ahmpq.changePriority(2, 19);
        assertTrue(ahmpq.removeSmallest() == 12);
        assertTrue(ahmpq.size() == 1);
        assertTrue(ahmpq.getSmallest() == 2);
    }
    @Test
    public void oneConstructorTimingTest() {
        Stopwatch ahmpqsw = new Stopwatch();
        ArrayHeapMinPQ<Integer> ahmpq = new ArrayHeapMinPQ<>();
        double ahmpqTime = ahmpqsw.elapsedTime();
        System.out.println("Time for ArrayHeapMinPQ construction: " + ahmpqTime);
        Stopwatch nmpqsw = new Stopwatch();
        NaiveMinPQ<Integer> nmpq = new NaiveMinPQ<>();
        double nmpqTime = nmpqsw.elapsedTime();
        System.out.println("Time for NaiveMinPQ construction: " + nmpqTime);
    }
    @Test
    public void testRemoveWithNMPQ() {
        removeNTimes(100000);
    }
    @Test
    public void testRemoveTiming() {
        removeTiming30kTimes(31250);
        removeTiming30kTimes(62500);
        removeTiming30kTimes(125000);
        removeTiming30kTimes(250000);
        removeTiming30kTimes(500000);
        removeTiming30kTimes(1000000);
        removeTiming30kTimes(2000000);
    }
    @Test
    public void removeTimingNaive() {
        naiveRemoveTiming30k(31250);
        naiveRemoveTiming30k(62500);
        naiveRemoveTiming30k(125000);
        naiveRemoveTiming30k(250000);
        naiveRemoveTiming30k(500000);
    }
    private void naiveRemoveTiming30k(int N) {
        NaiveMinPQ<Double> nmpq = new NaiveMinPQ<>();
        HashSet<Double> alreadyAdded = new HashSet<>();
        for (int i = 0; i < N; i ++) {
            while(true) {
                Double nextItem = r.nextDouble();
                if (!alreadyAdded.contains(nextItem)) {
                    nmpq.add(r.nextDouble() * 1000, r.nextDouble() * 1000);
                    break;
                }
            }
        }
        Stopwatch ahmpqsw = new Stopwatch();
        for (int i = 0; i < 30000; i++) {
            nmpq.removeSmallest();
        }
        double ahmpqTime = ahmpqsw.elapsedTime();
        System.out.println("Remove 30000 items from NMPQ of size " + N + ": " + ahmpqTime);
    }
    private void removeTiming30kTimes(int N) {
        ArrayHeapMinPQ<Double> ahmpq = new ArrayHeapMinPQ<>();
        HashSet<Double> alreadyAdded = new HashSet<>();
        for (int i = 0; i < N; i ++) {
            while(true) {
                Double nextItem = r.nextDouble();
                if (!alreadyAdded.contains(nextItem)) {
                    ahmpq.add(r.nextDouble() * 1000, r.nextDouble() * 1000);
                    break;
                }
            }
        }
        Stopwatch ahmpqsw = new Stopwatch();
        for (int i = 0; i < 30000; i++) {
            ahmpq.removeSmallest();
        }
        double ahmpqTime = ahmpqsw.elapsedTime();
        System.out.println("Remove 30000 items from AHMPQ of size " + N + ": " + ahmpqTime);
    }
    private void addNTimes(int N) {
        ArrayHeapMinPQ<Double> ahmpq = new ArrayHeapMinPQ<>();
        HashSet<Double> alreadyAdded = new HashSet<>();
        Stopwatch ahmpqsw = new Stopwatch();
        for (int i = 0; i < N; i ++) {
            while(true) {
                Double nextItem = r.nextDouble();
                if (!alreadyAdded.contains(nextItem)) {
                    ahmpq.add(r.nextDouble() * 1000, r.nextDouble() * 1000);
                    break;
                }
            }
        }
        double ahmpqTime = ahmpqsw.elapsedTime();
        System.out.println("Time to add " + N + " items to ArrayHeapMinPQ: " + ahmpqTime);
    }
    @Test
    public void testAddTiming() {
        addNTimes(31250);
        addNTimes(62500);
        addNTimes(125000);
        addNTimes(250000);
        addNTimes(500000);
        addNTimes(1000000);
        addNTimes(2000000);
    }
    public void removeNTimes(int N) {
        ArrayHeapMinPQ<Double> ahmpq = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Double> nmpq = new NaiveMinPQ<>();
        HashSet<Double> alreadyAdded = new HashSet<>();
        for (int i = 0; i < N; i ++) {
            while(true) {
                Double nextItem = r.nextDouble();
                if (!alreadyAdded.contains(nextItem)) {
                    Double item = r.nextDouble() * 1000;
                    Double priority = r.nextDouble() * 1000;
                    ahmpq.add(item, priority);
                    nmpq.add(item, priority);
                    alreadyAdded.add(nextItem);
                    break;
                }
            }
        }
        for (int i = 0; i < N; i++) {
            assertEquals(ahmpq.removeSmallest(), nmpq.removeSmallest());
        }
    }
    @Test
    public void testContains() {
        containsHelper(3);
    }
}
