package daa;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class BenchmarkRunner {
    private static final int[] SIZES = {100, 1_000, 10_000, 100_000};

    public static void main(String[] args) throws IOException {
        File out = new File("results/results.csv");
        StringBuilder sb = new StringBuilder();
        sb.append("workload,variant,structure,n,time_ms,steps,moves,comparisons\n");

        for (int n : SIZES) {
            int[] baseData = generateData(n);
            sb.append(runW1(n, baseData));
            sb.append(runW2(n, baseData));
            sb.append(runW3(n, baseData, "head"));
            sb.append(runW3(n, baseData, "middle"));
            sb.append(runW4(n, baseData));
        }

        CsvWriter.write(out, sb.toString());
        System.out.println("Wrote " + out.getAbsolutePath());
    }

    private static int[] generateData(int n) {
        Random random = new Random(42);
        int[] data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = random.nextInt(n * 10 + 1);
        }
        return data;
    }

    private static String runW1(int n, int[] baseData) {
        StringBuilder sb = new StringBuilder();
        int[] queries = new int[10_000];
        Random random = new Random(42);
        for (int i = 0; i < queries.length; i++) {
            queries[i] = random.nextInt(n);
        }

        sb.append(benchmarkGet("W1", "-", "DynamicArray", n, baseData, queries, true));
        sb.append(benchmarkGet("W1", "-", "MyLinkedList", n, baseData, queries, false));
        return sb.toString();
    }

    private static String benchmarkGet(String workload, String variant, String structure, int n, int[] baseData,
                                       int[] queries, boolean dynamicArray) {
        long[] times = new long[5];
        long steps = 0, moves = 0, comparisons = 0;

        for (int run = 0; run < 5; run++) {
            Metrics metrics = new Metrics();
            if (dynamicArray) {
                DynamicArray array = new DynamicArray(metrics);
                fill(array, baseData);
                long start = System.nanoTime();
                for (int query : queries) {
                    array.get(query);
                }
                long end = System.nanoTime();
                times[run] = end - start;
            } else {
                MyLinkedList list = new MyLinkedList(metrics);
                fill(list, baseData);
                long start = System.nanoTime();
                for (int query : queries) {
                    list.get(query);
                }
                long end = System.nanoTime();
                times[run] = end - start;
            }
            if (run == 1) {
                steps = metrics.getSteps();
                moves = metrics.getMoves();
                comparisons = metrics.getComparisons();
            }
        }

        long median = median(times);
        return line(workload, variant, structure, n, median, steps, moves, comparisons);
    }

    private static String runW2(int n, int[] baseData) {
        StringBuilder sb = new StringBuilder();
        int[] queries = new int[1_000];
        Random random = new Random(42);

        for (int i = 0; i < 500; i++) {
            queries[i] = baseData[random.nextInt(baseData.length)];
        }
        for (int i = 500; i < 1_000; i++) {
            queries[i] = n * 20 + i;
        }

        sb.append(benchmarkContains("W2", "-", "DynamicArray", n, baseData, queries, true));
        sb.append(benchmarkContains("W2", "-", "MyLinkedList", n, baseData, queries, false));
        return sb.toString();
    }

    private static String benchmarkContains(String workload, String variant, String structure, int n, int[] baseData,
                                            int[] queries, boolean dynamicArray) {
        long[] times = new long[5];
        long steps = 0, moves = 0, comparisons = 0;

        for (int run = 0; run < 5; run++) {
            Metrics metrics = new Metrics();
            if (dynamicArray) {
                DynamicArray array = new DynamicArray(metrics);
                fill(array, baseData);
                long start = System.nanoTime();
                for (int query : queries) {
                    array.contains(query);
                }
                long end = System.nanoTime();
                times[run] = end - start;
            } else {
                MyLinkedList list = new MyLinkedList(metrics);
                fill(list, baseData);
                long start = System.nanoTime();
                for (int query : queries) {
                    list.contains(query);
                }
                long end = System.nanoTime();
                times[run] = end - start;
            }
            if (run == 1) {
                steps = metrics.getSteps();
                moves = metrics.getMoves();
                comparisons = metrics.getComparisons();
            }
        }

        return line(workload, variant, structure, n, median(times), steps, moves, comparisons);
    }

    private static String runW3(int n, int[] baseData, String variant) {
        StringBuilder sb = new StringBuilder();
        int index = "head".equals(variant) ? 0 : n / 2;
        int[] values = new int[1_000];
        Random random = new Random(42);
        for (int i = 0; i < values.length; i++) {
            values[i] = random.nextInt(n * 10 + 1);
        }

        sb.append(benchmarkInsertRemove("W3", variant, "DynamicArray", n, baseData, values, index, true));
        sb.append(benchmarkInsertRemove("W3", variant, "MyLinkedList", n, baseData, values, index, false));
        return sb.toString();
    }

    private static String benchmarkInsertRemove(String workload, String variant, String structure, int n, int[] baseData,
                                                int[] values, int index, boolean dynamicArray) {
        long[] times = new long[5];
        long steps = 0, moves = 0, comparisons = 0;

        for (int run = 0; run < 5; run++) {
            Metrics metrics = new Metrics();
            if (dynamicArray) {
                DynamicArray array = new DynamicArray(metrics);
                fill(array, baseData);
                long start = System.nanoTime();
                for (int value : values) {
                    array.add(index, value);
                }
                for (int i = 0; i < values.length; i++) {
                    array.remove(index);
                }
                long end = System.nanoTime();
                times[run] = end - start;
            } else {
                MyLinkedList list = new MyLinkedList(metrics);
                fill(list, baseData);
                long start = System.nanoTime();
                for (int value : values) {
                    list.add(index, value);
                }
                for (int i = 0; i < values.length; i++) {
                    list.remove(index);
                }
                long end = System.nanoTime();
                times[run] = end - start;
            }
            if (run == 1) {
                steps = metrics.getSteps();
                moves = metrics.getMoves();
                comparisons = metrics.getComparisons();
            }
        }

        return line(workload, variant, structure, n, median(times), steps, moves, comparisons);
    }

    private static String runW4(int n, int[] baseData) {
        long[] times = new long[5];
        long steps = 0, moves = 0, comparisons = 0;

        for (int run = 0; run < 5; run++) {
            Metrics metrics = new Metrics();
            MinHeap heap = new MinHeap(metrics);
            long start = System.nanoTime();
            for (int value : baseData) {
                heap.insert(value);
            }
            int last = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                int current = heap.extractMin();
                if (current < last) {
                    throw new IllegalStateException("Heap output is not sorted");
                }
                last = current;
            }
            long end = System.nanoTime();
            times[run] = end - start;
            if (run == 1) {
                steps = metrics.getSteps();
                moves = metrics.getMoves();
                comparisons = metrics.getComparisons();
            }
        }

        return line("W4", "-", "MinHeap", n, median(times), steps, moves, comparisons);
    }

    private static void fill(DynamicArray array, int[] data) {
        for (int value : data) {
            array.add(value);
        }
    }

    private static void fill(MyLinkedList list, int[] data) {
        for (int value : data) {
            list.add(value);
        }
    }

    private static String line(String workload, String variant, String structure, int n, long nanos,
                               long steps, long moves, long comparisons) {
        double ms = nanos / 1_000_000.0;
        return workload + "," + variant + "," + structure + "," + n + "," + String.format(java.util.Locale.US, "%.3f", ms)
                + "," + steps + "," + moves + "," + comparisons + "\n";
    }

    private static long median(long[] values) {
        Arrays.sort(values);
        return values[values.length / 2];
    }
}
