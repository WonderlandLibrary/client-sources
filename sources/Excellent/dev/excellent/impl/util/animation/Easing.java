package dev.excellent.impl.util.animation;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;

import static java.lang.Math.pow;
import static java.lang.Math.sin;

@Getter
public enum Easing {
    LINEAR(x -> x),
    SIGMOID(x -> 1 / (1 + Math.exp(-x))),
    EASE_IN_QUAD(x -> x * x),
    EASE_OUT_QUAD(x -> x * (2 - x)),
    EASE_IN_OUT_QUAD(x -> x < 0.5 ? 2 * x * x : -1 + (4 - 2 * x) * x),
    EASE_IN_CUBIC(x -> x * x * x),
    EASE_OUT_CUBIC(x -> (--x) * x * x + 1),
    EASE_IN_OUT_CUBIC(x -> x < 0.5 ? 4 * x * x * x : (x - 1) * (2 * x - 2) * (2 * x - 2) + 1),
    EASE_IN_QUART(x -> x * x * x * x),
    EASE_OUT_QUART(x -> 1 - (--x) * x * x * x),
    EASE_IN_OUT_QUART(x -> x < 0.5 ? 8 * x * x * x * x : 1 - 8 * (--x) * x * x * x),
    EASE_IN_QUINT(x -> x * x * x * x * x),
    EASE_OUT_QUINT(x -> 1 + (--x) * x * x * x * x),
    EASE_IN_OUT_QUINT(x -> x < 0.5 ? 16 * x * x * x * x * x : 1 + 16 * (--x) * x * x * x * x),
    EASE_IN_SINE(x -> 1 - Math.cos(x * Math.PI / 2)),
    EASE_OUT_SINE(x -> sin(x * Math.PI / 2)),
    EASE_IN_OUT_SINE(x -> 1 - Math.cos(Math.PI * x / 2)),
    EASE_IN_EXPO(x -> x == 0 ? 0 : pow(2, 10 * x - 10)),
    EASE_OUT_EXPO(x -> x == 1 ? 1 : 1 - pow(2, -10 * x)),
    EASE_IN_OUT_EXPO(x -> x == 0 ? 0 : x == 1 ? 1 : x < 0.5 ? pow(2, 20 * x - 10) / 2 : (2 - pow(2, -20 * x + 10)) / 2),
    EASE_IN_CIRC(x -> 1 - Math.sqrt(1 - x * x)),
    EASE_OUT_CIRC(x -> Math.sqrt(1 - (--x) * x)),
    EASE_IN_OUT_CIRC(x -> x < 0.5 ? (1 - Math.sqrt(1 - 4 * x * x)) / 2 : (Math.sqrt(1 - 4 * (x - 1) * x) + 1) / 2),
    EASE_IN_BACK(x -> (1.70158 + 1) * x * x * x - 1.70158 * x * x),
    EASE_OUT_BACK(x -> 1 + (1.70158 + 1) * pow(x - 1, 3) + (1.70158) * pow(x - 1, 2)),
    EASE_IN_OUT_BACK(x -> x < 0.5 ? (Math.pow(2 * x, 2) * (((1.70158 * 1.525) + 1) * 2 * x - (1.70158 * 1.525))) / 2 : (Math.pow(2 * x - 2, 2) * (((1.70158 * 1.525) + 1) * (x * 2 - 2) + (1.70158 * 1.525)) + 2) / 2),
    EASE_IN_ELASTIC(x -> x == 0 ? 0 : x == 1 ? 1 : -Math.pow(2, 10 * x - 10) * Math.sin((x * 10 - 10.75) * ((2 * Math.PI) / 3))),
    EASE_OUT_ELASTIC(x -> x == 0 ? 0 : x == 1 ? 1 : pow(2, -10 * x) * sin((x * 10 - 0.75) * ((2 * Math.PI) / 3)) * 0.5 + 1),
    EASE_IN_OUT_ELASTIC(x -> x == 0 ? 0 : x == 1 ? 1 : x < 0.5 ? -(Math.pow(2, 20 * x - 10) * Math.sin((20 * x - 11.125) * ((2 * Math.PI) / 4.5))) / 2 : (Math.pow(2, -20 * x + 10) * Math.sin((20 * x - 11.125) * ((2 * Math.PI) / 4.5))) / 2 + 1),
    SHRINK_EASING(x -> {
        float easeAmount = 1.3f;
        float shrink = easeAmount + 1;
        return Math.max(0, 1 + shrink * Math.pow(x - 1, 3) + easeAmount * Math.pow(x - 1, 2));
    });

    private final Function<Double, Double> function;

    Easing(final Function<Double, Double> function) {
        this.function = function;
    }

    public double apply(double x) {
        return getFunction().apply(x);
    }

    public float apply(float x) {
        return getFunction().apply((double) x).floatValue();
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(super.toString().toLowerCase().replace("_", " "));
    }
}