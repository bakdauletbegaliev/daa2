package daa;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DynamicArrayTest {
    @Test
    void addGetRemoveWorks() {
        DynamicArray array = new DynamicArray(new Metrics());
        array.add(10);
        array.add(20);
        array.add(30);

        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));

        array.add(1, 15);
        assertEquals(15, array.get(1));
        assertEquals(20, array.get(2));

        assertEquals(15, array.remove(1));
        assertEquals(20, array.get(1));
    }

    @Test
    void containsWorks() {
        DynamicArray array = new DynamicArray(new Metrics());
        array.add(5);
        array.add(9);
        array.add(13);

        assertTrue(array.contains(9));
        assertFalse(array.contains(100));
    }

    @Test
    void invalidIndexThrows() {
        DynamicArray array = new DynamicArray(new Metrics());
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(0));
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(1, 7));
    }

    @Test
    void growsWhenFull() {
        DynamicArray array = new DynamicArray(new Metrics(), 1);
        for (int i = 0; i < 20; i++) {
            array.add(i);
        }
        assertEquals(20, array.size());
        assertEquals(19, array.get(19));
    }
}
