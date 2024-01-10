package r09.basic;

public class IntegerFactory implements BasicFactory<Integer> {
    private int current;
    private final int step;

    public IntegerFactory(int start, int step) {
        this.current = start;
        this.step = step;
    }

    public Integer create() {
        int result = current;
        current += step;
        return current;
    }
}