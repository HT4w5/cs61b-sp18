package synthesizer;

import org.junit.Test;

import edu.princeton.cs.algs4.StdRandom;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Iterator;

/**
 * Tests the ArrayRingBuffer class.
 * 
 * @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void testARBInit() {
        ArrayRingBuffer<Integer> arb0 = new ArrayRingBuffer<>(10);
        assertEquals("ARB initial capacity incorrect", 10, arb0.capacity);
        assertEquals("ARB initial fillCount incorrect", 0, arb0.fillCount);
    }

    @Test
    public void testARBEnqueueDequeue() {
        ArrayRingBuffer<Integer> arb0 = new ArrayRingBuffer<>(10);
        LinkedList<Integer> ll0 = new LinkedList<>();

        for (int i = 0; i < 10; ++i) {
            double ran = StdRandom.uniform();
            if (ran > 0.5) {
                arb0.enqueue(1);
                ll0.addLast(1);
            } else {
                arb0.enqueue(0);
                ll0.addLast(0);
            }
        }

        for (int i = 0; i < 10; ++i) {
            assertEquals("ARB Enqueue / Dequeue error",
                    ll0.removeFirst().intValue(), arb0.dequeue().intValue());
        }
    }

    @Test
    public void testARBOverflow() {
        ArrayRingBuffer<Integer> arb0 = new ArrayRingBuffer<>(10);
        for (int i = 0; i < 10; ++i) {
            arb0.enqueue(i);
        }

        boolean thrown = false;

        try {
            arb0.enqueue(1);
        } catch (RuntimeException e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    @Test
    public void testARBUnderflow() {
        ArrayRingBuffer<Integer> arb0 = new ArrayRingBuffer<>(10);

        boolean thrown = false;

        try {
            arb0.dequeue();
        } catch (RuntimeException e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    @Test
    public void testARBIterator() {
        ArrayRingBuffer<Integer> arb0 = new ArrayRingBuffer<>(20);

        for (int i = 0; i < 10; ++i) {
            arb0.enqueue(i);
        }

        for (int i = 0; i < 5; ++i) {
            arb0.dequeue();
        }

        int i = 5;
        for (int x : arb0) {
            assertEquals(i, x);
            ++i;
        }
    }

    @Test
    public void testARBIteratorException() {
        ArrayRingBuffer<Integer> arb0 = new ArrayRingBuffer<>(20);

        arb0.enqueue(0);
        arb0.enqueue(1);

        Iterator<Integer> seer = arb0.iterator();
        assertTrue(seer.hasNext());
        assertEquals(0, seer.next().intValue());
        assertTrue(seer.hasNext());
        assertEquals(1, seer.next().intValue());
        assertFalse(seer.hasNext());

        boolean thrown = false;

        try {
            seer.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    @Test
    public void testARBNestedIterator() {
        ArrayRingBuffer<Integer> arb0 = new ArrayRingBuffer<>(20);
        int[] arr0 = new int[10];

        for (int i = 0; i < 10; ++i) {
            arb0.enqueue(i);
            arr0[i] = i;
        }

        int i, j;
        i = 0;
        for (int x : arb0) {
            j = 0;
            for (int y : arb0) {
                assertTrue(x == arr0[i] && y == arr0[j]);
                ++j;
            }
            ++i;
        }
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
}
