package daa;

public class MinHeap {
    private int[] data;
    private int size;
    private final Metrics metrics;

    public MinHeap(Metrics metrics) {
        this(metrics, 16);
    }

    public MinHeap(Metrics metrics, int initialCapacity) {
        if (initialCapacity < 1) {
            initialCapacity = 1;
        }
        this.data = new int[initialCapacity];
        this.metrics = metrics;
    }

    public int size() {
        return size;
    }

    public void insert(int value) {
        ensureCapacity(size + 1);
        data[size] = value;
        if (metrics != null) {
            metrics.move();
        }
        bubbleUp(size);
        size++;
    }

    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        if (metrics != null) {
            metrics.step();
        }
        return data[0];
    }

    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        int result = data[0];
        data[0] = data[size - 1];
        if (metrics != null) {
            metrics.move();
        }
        size--;

        if (size > 0) {
            bubbleDown(0);
        }
        return result;
    }

    private void bubbleUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (metrics != null) {
                metrics.compare();
            }
            if (data[index] >= data[parent]) {
                break;
            }

            swap(index, parent);
            index = parent;
        }
    }

    private void bubbleDown(int index) {
        while (true) {
            int left = index * 2 + 1;
            int right = left + 1;
            int smallest = index;

            if (left < size) {
                if (metrics != null) {
                    metrics.compare();
                }
                if (data[left] < data[smallest]) {
                    smallest = left;
                }
            }

            if (right < size) {
                if (metrics != null) {
                    metrics.compare();
                }
                if (data[right] < data[smallest]) {
                    smallest = right;
                }
            }

            if (smallest == index) {
                break;
            }

            swap(index, smallest);
            index = smallest;
        }
    }

    private void swap(int i, int j) {
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
        if (metrics != null) {
            metrics.move(3);
        }
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
}
