package dev.luvbeeq.animation.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Easings {
    public final double c1 = 1.70158D;
    public final double c2 = 2.5949095D;
    public final double c3 = 2.70158D;
    public final double c4 = 2.0943951023931953D;
    public final double c5 = 1.3962634015954636D;
    public final Easing LINEAR = (value) -> value;
    public final Easing QUAD_IN = powIn(2);
    public final Easing QUAD_OUT = powOut(2);
    public final Easing QUAD_IN_OUT = powIN_OUT(2.0D);
    public final Easing CUBIC_IN = powIn(3);
    public final Easing CUBIC_OUT = powOut(3);
    public final Easing CUBIC_IN_OUT = powIN_OUT(3.0D);
    public final Easing QUART_IN = powIn(4);
    public final Easing QUART_OUT = powOut(4);
    public final Easing QUART_IN_OUT = powIN_OUT(4.0D);
    public final Easing QUINT_IN = powIn(5);
    public final Easing QUINT_OUT = powOut(5);
    public final Easing QUINT_IN_OUT = powIN_OUT(5.0D);
    public final Easing SINE_IN = (value) -> 1.0D - Math.cos(value * Math.PI / 2.0D);
    public final Easing SINE_OUT = (value) -> Math.sin(value * Math.PI / 2.0D);
    public final Easing SINE_IN_OUT = (value) -> -(Math.cos(Math.PI * value) - 1.0D) / 2.0D;
    public final Easing CIRC_IN = (value) -> 1.0D - Math.sqrt(1.0D - Math.pow(value, 2.0D));
    public final Easing CIRC_OUT = (value) -> Math.sqrt(1.0D - Math.pow(value - 1.0D, 2.0D));
    public final Easing CIRC_IN_OUT = (value) -> value < 0.5D ? (1.0D - Math.sqrt(1.0D - Math.pow(2.0D * value, 2.0D))) / 2.0D : (Math.sqrt(1.0D - Math.pow(-2.0D * value + 2.0D, 2.0D)) + 1.0D) / 2.0D;
    public final Easing ELASTIC_IN = (value) -> value != 0.0D && value != 1.0D ? Math.pow(-2.0D, 10.0D * value - 10.0D) * Math.sin((value * 10.0D - 10.75D) * 2.0943951023931953D) : value;
    public final Easing ELASTIC_OUT = (value) -> value != 0.0D && value != 1.0D ? Math.pow(2.0D, -10.0D * value) * Math.sin((value * 10.0D - 0.75D) * 2.0943951023931953D) + 1.0D : value;
    public final Easing ELASTIC_IN_OUT = (value) -> {
        if (value != 0.0D && value != 1.0D) {
            return value < 0.5D ? -(Math.pow(2.0D, 20.0D * value - 10.0D) * Math.sin((20.0D * value - 11.125D) * 1.3962634015954636D)) / 2.0D : Math.pow(2.0D, -20.0D * value + 10.0D) * Math.sin((20.0D * value - 11.125D) * 1.3962634015954636D) / 2.0D + 1.0D;
        } else {
            return value;
        }
    };
    public final Easing EXPO_IN = (value) -> value != 0.0D ? Math.pow(2.0D, 10.0D * value - 10.0D) : value;
    public final Easing EXPO_OUT = (value) -> value != 1.0D ? 1.0D - Math.pow(2.0D, -10.0D * value) : value;
    public final Easing EXPO_IN_OUT = (value) -> {
        if (value != 0.0D && value != 1.0D) {
            return value < 0.5D ? Math.pow(2.0D, 20.0D * value - 10.0D) / 2.0D : (2.0D - Math.pow(2.0D, -20.0D * value + 10.0D)) / 2.0D;
        } else {
            return value;
        }
    };
    public final Easing BACK_IN = (value) -> 2.70158D * Math.pow(value, 3.0D) - 1.70158D * Math.pow(value, 2.0D);
    public final Easing BACK_OUT = (value) -> 1.0D + 2.70158D * Math.pow(value - 1.0D, 3.0D) + 1.70158D * Math.pow(value - 1.0D, 2.0D);
    public final Easing BACK_IN_OUT = (value) -> value < 0.5D ? Math.pow(2.0D * value, 2.0D) * (7.189819D * value - 2.5949095D) / 2.0D : (Math.pow(2.0D * value - 2.0D, 2.0D) * (3.5949095D * (value * 2.0D - 2.0D) + 2.5949095D) + 2.0D) / 2.0D;
    public final Easing BOUNCE_OUT = (x) -> {
        double n1 = 7.5625D;
        double d1 = 2.75D;
        if (x < 1.0D / d1) {
            return n1 * Math.pow(x, 2.0D);
        } else if (x < 2.0D / d1) {
            return n1 * Math.pow(x - 1.5D / d1, 2.0D) + 0.75D;
        } else {
            return x < 2.5D / d1 ? n1 * Math.pow(x - 2.25D / d1, 2.0D) + 0.9375D : n1 * Math.pow(x - 2.625D / d1, 2.0D) + 0.984375D;
        }
    };
    public final Easing BOUNCE_IN = (value) -> 1.0D - BOUNCE_OUT.ease(1.0D - value);
    public final Easing BOUNCE_IN_OUT = (value) -> value < 0.5D ? (1.0D - BOUNCE_OUT.ease(1.0D - 2.0D * value)) / 2.0D : (1.0D + BOUNCE_OUT.ease(2.0D * value - 1.0D)) / 2.0D;


    public Easing powIn(double n) {
        return (value) -> Math.pow(value, n);
    }

    public Easing powIn(int n) {
        return powIn((double) n);
    }

    public Easing powOut(double n) {
        return (value) -> 1.0D - Math.pow(1.0D - value, n);
    }

    public Easing powOut(int n) {
        return powOut((double) n);
    }

    public Easing powIN_OUT(double n) {
        return (value) -> value < 0.5D ? Math.pow(2.0D, n - 1.0D) * Math.pow(value, n) : 1.0D - Math.pow(-2.0D * value + 2.0D, n) / 2.0D;
    }
}