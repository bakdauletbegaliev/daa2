package daa;

public class DynamicArray {
    private int[] data;
    private int size;
    private final Metrics metrics;

    public DynamicArray(Metrics metrics) {
        this(metrics, 10);
    }

    public DynamicArray(Metrics metrics, int initialCapacity) {
        if (initialCapacity < 1) {
            initialCapacity = 1;
        }
        this.data = new int[initialCapacity];
        this.metrics = metrics;
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public void add(int value) {
        ensureCapacity(size + 1);
        data[size] = value;
        if (metrics != null) {
            metrics.move();
        }
        size++;
    }

    public void add(int index, int value) {
        checkAddIndex(index);
        ensureCapacity(size + 1);

        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
            if (metrics != null) {
                metrics.step();
                metrics.move();
            }
        }
        data[index] = value;
        if (metrics != null) {
            metrics.move();
        }
        size++;
    }

    public int remove(int index) {
        checkElementIndex(index);
        int removed = data[index];
        if (metrics != null) {
            metrics.step();
        }

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
            if (metrics != null) {
                metrics.step();
                metrics.move();
            }
        }
        size--;
        return removed;
    }

    public int get(int index) {
        checkElementIndex(index);
        if (metrics != null) {
            metrics.step();
        }
        return data[index];
    }

    public boolean contains(int value) {
        for (int i = 0; i < size; i++) {
            if (metrics != null) {
                metrics.step();
                metrics.compare();
            }
            if (data[i] == value) {
                return true;
            }
        }
        return false;
    }

    private void ensureCapacity(int needed) {
        if (needed <= data.length) {
            return;
        }
        int newCapacity = data.length * 2;
        while (newCapacity < needed) {
            newCapacity *= 2;
        }
        int[] newData = new int[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
            if (metrics != null) {
                metrics.step();
                metrics.move();
            }
        }
        data = newData;
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
