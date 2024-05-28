package arsenic.utils.timer;

import java.util.function.UnaryOperator;

public enum TickMode {

    SINE(input -> (float) ((Math.sin(Math.PI * (input + 3/2f)) + 1)/2)),
    LINEAR(input -> input),
    ROOT(input -> (float) Math.sqrt(input)),
    SQR(input -> input * input);

    private final UnaryOperator<Float> i;

    TickMode(UnaryOperator<Float> i) {
        this.i = i;
    }

    public float toSmoothPercent(float f) {
        return i.apply(f);
    }

}
