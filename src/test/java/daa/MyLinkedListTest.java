package daa;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyLinkedListTest {
    @Test
    void addGetRemoveWorks() {
        MyLinkedList list = new MyLinkedList(new Metrics());
        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));

        list.add(1, 15);
        assertEquals(15, list.get(1));
        assertEquals(20, list.get(2));

        assertEquals(15, list.remove(1));
        assertEquals(20, list.get(1));
    }

    @Test
    void containsWorks() {
        MyLinkedList list = new MyLinkedList(new Metrics());
        list.add(5);
        list.add(9);
        list.add(13);

        assertTrue(list.contains(9));
        assertFalse(list.contains(100));
    }

    @Test
    void invalidIndexThrows() {
        MyLinkedList list = new MyLinkedList(new Metrics());
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(1, 7));
    }

    @Test
    void headAndTailUpdateCorrectly() {
        MyLinkedList list = new MyLinkedList(new Metrics());
        list.add(1);
        list.add(2);
        list.add(3);

        assertEquals(1, list.remove(0));
        assertEquals(3, list.remove(list.size() - 1));
        assertEquals(2, list.get(0));
    }
}
