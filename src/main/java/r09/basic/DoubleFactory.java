package r09.basic;

public class DoubleFactory implements BasicFactory<Double> {
    private double current;
    private final double step;

    public DoubleFactory(double start, double step) {
        this.current = start;
        this.step = step;
    }

    @Override
    public Double create() {
        double result = current;
        current += step;
        return result;
    }
}