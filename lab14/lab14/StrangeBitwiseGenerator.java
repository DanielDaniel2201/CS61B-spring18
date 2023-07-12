package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {

        private final int period;
        private int state;
        private int weirdState;

        public StrangeBitwiseGenerator(int period) {
            state = 0;
            this.period = period;
        }
        @Override
        public double next() {
            state += 1;
            weirdState = state & (state >> 7) % period;
            return normalize(weirdState);
        }

        private double normalize(int state) {
            double x = state % period;
            return 2 * x / period - 1;
        }
    }