package daa;

public class MyLinkedList {
    private static class Node {
        int value;
        Node prev;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    private Node head;
    private Node tail;
    private int size;
    private final Metrics metrics;

    public MyLinkedList(Metrics metrics) {
        this.metrics = metrics;
    }

    public int size() {
        return size;
    }

    public void add(int value) {
        Node node = new Node(value);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
            if (metrics != null) {
                metrics.move(2);
            }
        }
        size++;
    }

    public void add(int index, int value) {
        checkAddIndex(index);

        if (index == size) {
            add(value);
            return;
        }

        if (index == 0) {
            Node node = new Node(value);
            node.next = head;
            if (head != null) {
                head.prev = node;
                if (metrics != null) {
                    metrics.move();
                }
            }
            head = node;
            if (tail == null) {
                tail = node;
            }
            if (metrics != null) {
                metrics.move(2);
            }
            size++;
            return;
        }

        Node current = nodeAt(index);
        Node node = new Node(value);
        Node left = current.prev;

        left.next = node;
        node.prev = left;
        node.next = current;
        current.prev = node;

        if (metrics != null) {
            metrics.move(4);
        }
        size++;
    }

    public int remove(int index) {
        checkElementIndex(index);

        if (index == 0) {
            int removed = head.value;
            head = head.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
            if (metrics != null) {
                metrics.move(2);
            }
            size--;
            return removed;
        }

        if (index == size - 1) {
            int removed = tail.value;
            tail = tail.prev;
            if (tail != null) {
                tail.next = null;
            } else {
                head = null;
            }
            if (metrics != null) {
                metrics.move(2);
            }
            size--;
            return removed;
        }

        Node current = nodeAt(index);
        int removed = current.value;
        Node left = current.prev;
        Node right = current.next;

        left.next = right;
        right.prev = left;

        if (metrics != null) {
            metrics.move(2);
        }
        size--;
        return removed;
    }

    public int get(int index) {
        checkElementIndex(index);
        Node node = nodeAt(index);
        return node.value;
    }

    public boolean contains(int value) {
        Node current = head;
        while (current != null) {
            if (metrics != null) {
                metrics.step();
                metrics.compare();
            }
            if (current.value == value) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    private Node nodeAt(int index) {
        Node current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
                if (metrics != null) {
                    metrics.step();
                }
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
                if (metrics != null) {
                    metrics.step();
                }
            }
        }
        if (metrics != null) {
            metrics.step();
        }
        return current;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index=" + index + ", size=" + size);
        }
    }

    private void checkAddIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index=" + index + ", size=" + size);
        }
    }
}
