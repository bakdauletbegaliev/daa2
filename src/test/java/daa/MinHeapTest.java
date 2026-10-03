package daa;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MinHeapTest {
    @Test
    void insertAndExtractSorted() {
        MinHeap heap = new MinHeap(new Metrics());
        int[] values = {5, 1, 9, 2, 7, 3};

        for (int v : values) {
            heap.insert(v);
        }

        int last = Integer.MIN_VALUE;
        for (int i = 0; i < values.length; i++) {
            int current = heap.extractMin();
            assertTrue(current >= last);
            last = current;
        }
    }

    @Test
    void peekMinWorks() {
        MinHeap heap = new MinHeap(new Metrics());
        heap.insert(4);
        heap.insert(1);
        heap.insert(3);

        assertEquals(1, heap.peekMin());
        assertEquals(1, heap.extractMin());
    }

    @Test
    void emptyHeapThrows() {
        MinHeap heap = new MinHeap(new Metrics());
        assertThrows(IllegalStateException.class, heap::peekMin);
        assertThrows(IllegalStateException.class, heap::extractMin);
    }
}
