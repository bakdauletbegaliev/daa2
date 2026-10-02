package daa;

public class Metrics {
    private long steps;
    private long moves;
    private long comparisons;

    public void reset() {
        steps = 0;
        moves = 0;
        comparisons = 0;
    }

    public void step() {
        steps++;
    }

    public void step(long n) {
        steps += n;
    }

    public void move() {
        moves++;
    }

    public void move(long n) {
        moves += n;
    }

    public void compare() {
        comparisons++;
    }

    public void compare(long n) {
        comparisons += n;
    }

    public long getSteps() {
        return steps;
    }

    public long getMoves() {
        return moves;
    }

    public long getComparisons() {
        return comparisons;
    }
}
