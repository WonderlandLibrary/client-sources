package net.augustus.utils.skid.lorious.anims;


public final class Easings {
    public static final double c1 = 1.70158;
    public static final double c2 = 2.5949095;
    public static final double c3 = 2.70158;
    public static final double c4 = 2.0943951023931953;
    public static final double c5 = 1.3962634015954636;
    public static final Easing NONE = value -> value;
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
    public static final Easing SINE_IN = value -> 1.0 - Math.cos((double)(value * Math.PI / 2.0));
    public static final Easing SINE_OUT = value -> Math.sin((double)(value * Math.PI / 2.0));
    public static final Easing SINE_BOTH = value -> -(Math.cos((double)(Math.PI * value)) - 1.0) / 2.0;
    public static final Easing CIRC_IN = value -> 1.0 - Math.sqrt((double)(1.0 - Math.pow((double)value, (double)2.0)));
    public static final Easing CIRC_OUT = value -> Math.sqrt((double)(1.0 - Math.pow((double)(value - 1.0), (double)2.0)));
    public static final Easing CIRC_BOTH = value -> value < 0.5 ? (1.0 - Math.sqrt((double)(1.0 - Math.pow((double)(2.0 * value), (double)2.0)))) / 2.0 : (Math.sqrt((double)(1.0 - Math.pow((double)(-2.0 * value + 2.0), (double)2.0))) + 1.0) / 2.0;
    public static final Easing ELASTIC_IN = value -> value != 0.0 && value != 1.0 ? Math.pow((double)-2.0, (double)(10.0 * value - 10.0)) * Math.sin((double)((value * 10.0 - 10.75) * 2.0943951023931953)) : value;
    public static final Easing ELASTIC_OUT = value -> value != 0.0 && value != 1.0 ? Math.pow((double)2.0, (double)(-10.0 * value)) * Math.sin((double)((value * 10.0 - 0.75) * 2.0943951023931953)) + 1.0 : value;
    public static final Easing ELASTIC_BOTH = value -> {
        if (value != 0.0 && value != 1.0) {
            return value < 0.5 ? -(Math.pow((double)2.0, (double)(20.0 * value - 10.0)) * Math.sin((double)((20.0 * value - 11.125) * 1.3962634015954636))) / 2.0 : Math.pow((double)2.0, (double)(-20.0 * value + 10.0)) * Math.sin((double)((20.0 * value - 11.125) * 1.3962634015954636)) / 2.0 + 1.0;
        }
        return value;
    };
    public static final Easing EXPO_IN = value -> value != 0.0 ? Math.pow((double)2.0, (double)(10.0 * value - 10.0)) : value;
    public static final Easing EXPO_OUT = value -> value != 1.0 ? 1.0 - Math.pow((double)2.0, (double)(-10.0 * value)) : value;
    public static final Easing EXPO_BOTH = value -> {
        if (value != 0.0 && value != 1.0) {
            return value < 0.5 ? Math.pow((double)2.0, (double)(20.0 * value - 10.0)) / 2.0 : (2.0 - Math.pow((double)2.0, (double)(-20.0 * value + 10.0))) / 2.0;
        }
        return value;
    };
    public static final Easing BACK_IN = value -> 2.70158 * Math.pow((double)value, (double)3.0) - 1.70158 * Math.pow((double)value, (double)2.0);
    public static final Easing BACK_OUT = value -> 1.0 + 2.70158 * Math.pow((double)(value - 1.0), (double)3.0) + 1.70158 * Math.pow((double)(value - 1.0), (double)2.0);
    public static final Easing BACK_BOTH = value -> value < 0.5 ? Math.pow((double)(2.0 * value), (double)2.0) * (7.189819 * value - 2.5949095) / 2.0 : (Math.pow((double)(2.0 * value - 2.0), (double)2.0) * (3.5949095 * (value * 2.0 - 2.0) + 2.5949095) + 2.0) / 2.0;
    public static final Easing BOUNCE_OUT = x -> {
        double n1 = 7.5625;
        double d1 = 2.75;
        if (x < 1.0 / d1) {
            return n1 * Math.pow((double)x, (double)2.0);
        }
        if (x < 2.0 / d1) {
            return n1 * Math.pow((double)(x - 1.5 / d1), (double)2.0) + 0.75;
        }
        return x < 2.5 / d1 ? n1 * Math.pow((double)(x - 2.25 / d1), (double)2.0) + 0.9375 : n1 * Math.pow((double)(x - 2.625 / d1), (double)2.0) + 0.984375;
    };
    public static final Easing BOUNCE_IN = value -> 1.0 - BOUNCE_OUT.ease(1.0 - value);
    public static final Easing BOUNCE_BOTH = value -> value < 0.5 ? (1.0 - BOUNCE_OUT.ease(1.0 - 2.0 * value)) / 2.0 : (1.0 + BOUNCE_OUT.ease(2.0 * value - 1.0)) / 2.0;

    public static Easing powIn(double n) {
        return value -> Math.pow((double)value, (double)n);
    }

    public static Easing powIn(int n) {
        return Easings.powIn((double)n);
    }

    public static Easing powOut(double n) {
        return value -> 1.0 - Math.pow((double)(1.0 - value), (double)n);
    }

    public static Easing powOut(int n) {
        return Easings.powOut((double)n);
    }

    public static Easing powBoth(double n) {
        return value -> value < 0.5 ? Math.pow((double)2.0, (double)(n - 1.0)) * Math.pow((double)value, (double)n) : 1.0 - Math.pow((double)(-2.0 * value + 2.0), (double)n) / 2.0;
    }

    public static Easing getEasingByName(String name) {
        if (name.equalsIgnoreCase("default") || name.equalsIgnoreCase("none")) {
            return NONE;
        }
        if (name.equalsIgnoreCase("sine in")) {
            return SINE_IN;
        }
        if (name.equalsIgnoreCase("sine out")) {
            return SINE_OUT;
        }
        if (name.equalsIgnoreCase("sine both")) {
            return SINE_BOTH;
        }
        if (name.equalsIgnoreCase("quad in")) {
            return QUAD_IN;
        }
        if (name.equalsIgnoreCase("quad out")) {
            return QUAD_OUT;
        }
        if (name.equalsIgnoreCase("quad both")) {
            return QUAD_BOTH;
        }
        if (name.equalsIgnoreCase("cubic in")) {
            return CUBIC_IN;
        }
        if (name.equalsIgnoreCase("cubic out")) {
            return CUBIC_OUT;
        }
        if (name.equalsIgnoreCase("cubic both")) {
            return CUBIC_BOTH;
        }
        if (name.equalsIgnoreCase("quart in")) {
            return QUART_IN;
        }
        if (name.equalsIgnoreCase("quart out")) {
            return QUART_OUT;
        }
        if (name.equalsIgnoreCase("quart both")) {
            return QUART_BOTH;
        }
        if (name.equalsIgnoreCase("quint in")) {
            return QUINT_IN;
        }
        if (name.equalsIgnoreCase("quint out")) {
            return QUINT_OUT;
        }
        if (name.equalsIgnoreCase("quint both")) {
            return QUINT_BOTH;
        }
        if (name.equalsIgnoreCase("expo in")) {
            return EXPO_IN;
        }
        if (name.equalsIgnoreCase("expo out")) {
            return EXPO_OUT;
        }
        if (name.equalsIgnoreCase("expo both")) {
            return EXPO_BOTH;
        }
        if (name.equalsIgnoreCase("circ in")) {
            return CIRC_IN;
        }
        if (name.equalsIgnoreCase("circ out")) {
            return CIRC_OUT;
        }
        if (name.equalsIgnoreCase("circ both")) {
            return CIRC_BOTH;
        }
        if (name.equalsIgnoreCase("back in")) {
            return BACK_IN;
        }
        if (name.equalsIgnoreCase("back out")) {
            return BACK_OUT;
        }
        if (name.equalsIgnoreCase("back both")) {
            return BACK_BOTH;
        }
        if (name.equalsIgnoreCase("elastic in")) {
            return ELASTIC_IN;
        }
        if (name.equalsIgnoreCase("elastic out")) {
            return ELASTIC_OUT;
        }
        if (name.equalsIgnoreCase("elastic both")) {
            return ELASTIC_BOTH;
        }
        if (name.equalsIgnoreCase("bounce in")) {
            return BOUNCE_IN;
        }
        if (name.equalsIgnoreCase("bounce out")) {
            return BOUNCE_OUT;
        }
        return BOUNCE_BOTH;
    }
}
