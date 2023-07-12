package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {

    private int period;
    private final double factor;
    private int state;
    private int counter = 0;
    private int sumSoFar = 0;

    public AcceleratingSawToothGenerator(int period, double factor) {
        this.period = period;
        this.factor = factor;
        state = 0;
    }

    @Override
    public double next() {
        System.out.println(period);

        state += 1;
        counter += 1;
        double y = normalize(state);

        if (counter == period) {
            counter = 0;
            sumSoFar += period;
            period = (int) Math.round(period * factor);
        }

        return y;
    }

    private double normalize(int state) {
        double x = state - sumSoFar;
        return 2 * x / period - 1;
    }
}
