/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.hogoshi.util;

import ru.hogoshi.util.Easing;

public final class Easings {
    public static final double c1 = 1.70158;
    public static final double c2 = 2.5949095;
    public static final double c3 = 2.70158;
    public static final double c4 = 2.0943951023931953;
    public static final double c5 = 1.3962634015954636;
    public static final Easing NONE = Easings::lambda$static$0;
    public static final Easing QUAD_IN = Easings.powIn(2);
    public static final Easing QUAD_OUT = Easings.powOut(2);
    public static final Easing QUAD_BOTH = Easings.powBoth(2.0);
    public static final Easing CUBIC_IN = Easings.powIn(3);
    public static final Easing CUBIC_OUT = Easings.powOut(3);
    public static final Easing CUBIC_BOTH = Easings.powBoth(3.0);
    public static final Easing QUART_IN = Easings.powIn(4);
    public static final Easing QUART_OUT = Easings.powOut(4);
    public static final Easing QUART_BOTH = Easings.powBoth(4.0);
    public static final Easing QUINT_IN = Easings.powIn(5);
    public static final Easing QUINT_OUT = Easings.powOut(5);
    public static final Easing QUINT_BOTH = Easings.powBoth(5.0);
    public static final Easing SINE_IN = Easings::lambda$static$1;
    public static final Easing SINE_OUT = Easings::lambda$static$2;
    public static final Easing SINE_BOTH = Easings::lambda$static$3;
    public static final Easing CIRC_IN = Easings::lambda$static$4;
    public static final Easing CIRC_OUT = Easings::lambda$static$5;
    public static final Easing CIRC_BOTH = Easings::lambda$static$6;
    public static final Easing ELASTIC_IN = Easings::lambda$static$7;
    public static final Easing ELASTIC_OUT = Easings::lambda$static$8;
    public static final Easing ELASTIC_BOTH = Easings::lambda$static$9;
    public static final Easing EXPO_IN = Easings::lambda$static$10;
    public static final Easing EXPO_OUT = Easings::lambda$static$11;
    public static final Easing EXPO_BOTH = Easings::lambda$static$12;
    public static final Easing BACK_IN = Easings::lambda$static$13;
    public static final Easing BACK_OUT = Easings::lambda$static$14;
    public static final Easing BACK_BOTH = Easings::lambda$static$15;
    public static final Easing BOUNCE_OUT = Easings::lambda$static$16;
    public static final Easing BOUNCE_IN = Easings::lambda$static$17;
    public static final Easing BOUNCE_BOTH = Easings::lambda$static$18;

    private Easings() {
    }

    public static Easing powIn(double d) {
        return arg_0 -> Easings.lambda$powIn$19(d, arg_0);
    }

    public static Easing powIn(int n) {
        return Easings.powIn((double)n);
    }

    public static Easing powOut(double d) {
        return arg_0 -> Easings.lambda$powOut$20(d, arg_0);
    }

    public static Easing powOut(int n) {
        return Easings.powOut((double)n);
    }

    public static Easing powBoth(double d) {
        return arg_0 -> Easings.lambda$powBoth$21(d, arg_0);
    }

    private static double lambda$powBoth$21(double d, double d2) {
        if (d2 < 0.5) {
            return Math.pow(2.0, d - 1.0) * Math.pow(d2, d);
        }
        return 1.0 - Math.pow(-2.0 * d2 + 2.0, d) / 2.0;
    }

    private static double lambda$powOut$20(double d, double d2) {
        return 1.0 - Math.pow(1.0 - d2, d);
    }

    private static double lambda$powIn$19(double d, double d2) {
        return Math.pow(d2, d);
    }

    private static double lambda$static$18(double d) {
        if (d < 0.5) {
            return (1.0 - BOUNCE_OUT.ease(1.0 - 2.0 * d)) / 2.0;
        }
        return (1.0 + BOUNCE_OUT.ease(2.0 * d - 1.0)) / 2.0;
    }

    private static double lambda$static$17(double d) {
        return 1.0 - BOUNCE_OUT.ease(1.0 - d);
    }

    private static double lambda$static$16(double d) {
        double d2 = 7.5625;
        double d3 = 2.75;
        if (d < 1.0 / d3) {
            return d2 * Math.pow(d, 2.0);
        }
        if (d < 2.0 / d3) {
            return d2 * Math.pow(d - 1.5 / d3, 2.0) + 0.75;
        }
        if (d < 2.5 / d3) {
            return d2 * Math.pow(d - 2.25 / d3, 2.0) + 0.9375;
        }
        return d2 * Math.pow(d - 2.625 / d3, 2.0) + 0.984375;
    }

    private static double lambda$static$15(double d) {
        if (d < 0.5) {
            return Math.pow(2.0 * d, 2.0) * (7.189819 * d - 2.5949095) / 2.0;
        }
        return (Math.pow(2.0 * d - 2.0, 2.0) * (3.5949095 * (d * 2.0 - 2.0) + 2.5949095) + 2.0) / 2.0;
    }

    private static double lambda$static$14(double d) {
        return 1.0 + 2.70158 * Math.pow(d - 1.0, 3.0) + 1.70158 * Math.pow(d - 1.0, 2.0);
    }

    private static double lambda$static$13(double d) {
        return 2.70158 * Math.pow(d, 3.0) - 1.70158 * Math.pow(d, 2.0);
    }

    private static double lambda$static$12(double d) {
        if (d == 0.0 || d == 1.0) {
            return d;
        }
        if (d < 0.5) {
            return Math.pow(2.0, 20.0 * d - 10.0) / 2.0;
        }
        return (2.0 - Math.pow(2.0, -20.0 * d + 10.0)) / 2.0;
    }

    private static double lambda$static$11(double d) {
        if (d != 1.0) {
            return 1.0 - Math.pow(2.0, -10.0 * d);
        }
        return d;
    }

    private static double lambda$static$10(double d) {
        if (d != 0.0) {
            return Math.pow(2.0, 10.0 * d - 10.0);
        }
        return d;
    }

    private static double lambda$static$9(double d) {
        if (d == 0.0 || d == 1.0) {
            return d;
        }
        if (d < 0.5) {
            return -(Math.pow(2.0, 20.0 * d - 10.0) * Math.sin((20.0 * d - 11.125) * 1.3962634015954636)) / 2.0;
        }
        return Math.pow(2.0, -20.0 * d + 10.0) * Math.sin((20.0 * d - 11.125) * 1.3962634015954636) / 2.0 + 1.0;
    }

    private static double lambda$static$8(double d) {
        if (d == 0.0 || d == 1.0) {
            return d;
        }
        return Math.pow(2.0, -10.0 * d) * Math.sin((d * 10.0 - 0.75) * 2.0943951023931953) + 1.0;
    }

    private static double lambda$static$7(double d) {
        if (d == 0.0 || d == 1.0) {
            return d;
        }
        return Math.pow(-2.0, 10.0 * d - 10.0) * Math.sin((d * 10.0 - 10.75) * 2.0943951023931953);
    }

    private static double lambda$static$6(double d) {
        if (d < 0.5) {
            return (1.0 - Math.sqrt(1.0 - Math.pow(2.0 * d, 2.0))) / 2.0;
        }
        return (Math.sqrt(1.0 - Math.pow(-2.0 * d + 2.0, 2.0)) + 1.0) / 2.0;
    }

    private static double lambda$static$5(double d) {
        return Math.sqrt(1.0 - Math.pow(d - 1.0, 2.0));
    }

    private static double lambda$static$4(double d) {
        return 1.0 - Math.sqrt(1.0 - Math.pow(d, 2.0));
    }

    private static double lambda$static$3(double d) {
        return -(Math.cos(Math.PI * d) - 1.0) / 2.0;
    }

    private static double lambda$static$2(double d) {
        return Math.sin(d * Math.PI / 2.0);
    }

    private static double lambda$static$1(double d) {
        return 1.0 - Math.cos(d * Math.PI / 2.0);
    }

    private static double lambda$static$0(double d) {
        return d;
    }
}

