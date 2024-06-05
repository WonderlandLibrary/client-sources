package tk.rektsky.Utils.Display;

public class AnimationUtil
{
    static double PI;
    
    public static double easeInSine(final double t) {
        return 1.0 - Math.cos(t * 3.141592653589793 / 2.0);
    }
    
    public static double easeOutSine(final double t) {
        return Math.cos(t * 3.141592653589793 / 2.0);
    }
    
    public static double easeInOutSine(final double t) {
        return -(Math.cos(AnimationUtil.PI * t) - 1.0) / 2.0;
    }
    
    public static double easeInQuad(final double t) {
        return t * t;
    }
    
    public static double easeOutQuad(final double t) {
        return 1.0 - (1.0 - t) * (1.0 - t);
    }
    
    public static double easeInOutQuad(final double t) {
        return (t < 0.5) ? (2.0 * t * t) : (1.0 - Math.pow(-2.0 * t + 2.0, 2.0) / 2.0);
    }
    
    public static double easeInCubic(final double t) {
        return t * t * t;
    }
    
    public static double easeOutCubic(final double t) {
        return 1.0 - Math.pow(1.0 - t, 3.0);
    }
    
    public static double easeInOutCubic(final double t) {
        return (t < 0.5) ? (4.0 * t * t * t) : (1.0 - Math.pow(-2.0 * t + 2.0, 3.0) / 2.0);
    }
    
    public static double easeInQuart(final double t) {
        return t * t * t * t;
    }
    
    public static double easeOutQuart(final double t) {
        return 1.0 - Math.pow(1.0 - t, 4.0);
    }
    
    public static double easeInOutQuart(final double t) {
        return (t < 0.5) ? (8.0 * t * t * t * t) : (1.0 - Math.pow(-2.0 * t + 2.0, 4.0) / 2.0);
    }
    
    public static double easeInQuint(final double t) {
        return t * t * t * t * t;
    }
    
    public static double easeOutQuint(final double t) {
        return 1.0 - Math.pow(1.0 - t, 5.0);
    }
    
    public static double easeInOutQuint(final double t) {
        return (t < 0.5) ? (16.0 * t * t * t * t * t) : (1.0 - Math.pow(-2.0 * t + 2.0, 5.0) / 2.0);
    }
    
    public static double easeInExpo(final double t) {
        return (t == 0.0) ? 0.0 : Math.pow(2.0, 10.0 * t - 10.0);
    }
    
    public static double easeOutExpo(final double t) {
        return (t == 1.0) ? 1.0 : (1.0 - Math.pow(2.0, -10.0 * t));
    }
    
    public static double easeInOutExpo(final double t) {
        return (t == 0.0) ? 0.0 : ((t == 1.0) ? 1.0 : ((t < 0.5) ? (Math.pow(2.0, 20.0 * t - 10.0) / 2.0) : ((2.0 - Math.pow(2.0, -20.0 * t + 10.0)) / 2.0)));
    }
    
    public static double easeInCirc(final double t) {
        return 1.0 - Math.sqrt(1.0 - Math.pow(t, 2.0));
    }
    
    public static double easeOutCirc(final double t) {
        return Math.sqrt(1.0 - Math.pow(t - 1.0, 2.0));
    }
    
    public static double easeInOutCirc(final double t) {
        return (t < 0.5) ? ((1.0 - Math.sqrt(1.0 - Math.pow(2.0 * t, 2.0))) / 2.0) : ((Math.sqrt(1.0 - Math.pow(-2.0 * t + 2.0, 2.0)) + 1.0) / 2.0);
    }
    
    public static double easeInBack(final double t) {
        final double c1 = 1.70158;
        final double c2 = c1 + 1.0;
        return c2 * t * t * t - c1 * t * t;
    }
    
    public static double easeOutBack(final double t) {
        final double c1 = 1.70158;
        final double c2 = c1 + 1.0;
        return 1.0 + c2 * Math.pow(t - 1.0, 3.0) + c1 * Math.pow(t - 1.0, 2.0);
    }
    
    public static double easeInOutBack(final double t) {
        final double c1 = 1.70158;
        final double c2 = c1 * 1.525;
        return (t < 0.5) ? (Math.pow(2.0 * t, 2.0) * ((c2 + 1.0) * 2.0 * t - c2) / 2.0) : ((Math.pow(2.0 * t - 2.0, 2.0) * ((c2 + 1.0) * (t * 2.0 - 2.0) + c2) + 2.0) / 2.0);
    }
    
    public static double easeInElastic(final double t) {
        final double c4 = 2.0943951023931953;
        return (t == 0.0) ? 0.0 : ((t == 1.0) ? 1.0 : (-Math.pow(2.0, 10.0 * t - 10.0) * Math.sin((t * 10.0 - 10.75) * c4)));
    }
    
    public static double easeOutElastic(final double t) {
        final double c4 = 2.0943951023931953;
        return (t == 0.0) ? 0.0 : ((t == 1.0) ? 1.0 : (Math.pow(2.0, -10.0 * t) * Math.sin((t * 10.0 - 0.75) * c4) + 1.0));
    }
    
    public static double easeInOutElastic(final double t) {
        final double c5 = 1.3962634015954636;
        return (t == 0.0) ? 0.0 : ((t == 1.0) ? 1.0 : ((t < 0.5) ? (-(Math.pow(2.0, 20.0 * t - 10.0) * Math.sin((20.0 * t - 11.125) * c5)) / 2.0) : (Math.pow(2.0, -20.0 * t + 10.0) * Math.sin((20.0 * t - 11.125) * c5) / 2.0 + 1.0)));
    }
    
    public static double easeInBounce(final double t) {
        return 1.0 - easeOutBounce(1.0 - t);
    }
    
    public static double easeOutBounce(double t) {
        final double n1 = 7.5625;
        final double d1 = 2.75;
        if (t < 1.0 / d1) {
            return n1 * t * t;
        }
        if (t < 2.0 / d1) {
            return n1 * (t -= 1.5 / d1) * t + 0.75;
        }
        if (t < 2.5 / d1) {
            return n1 * (t -= 2.25 / d1) * t + 0.9375;
        }
        return n1 * (t -= 2.625 / d1) * t + 0.984375;
    }
    
    public static double easeInOutBounce(final double t) {
        return (t < 0.5) ? ((1.0 - easeOutBounce(1.0 - 2.0 * t)) / 2.0) : ((1.0 + easeOutBounce(2.0 * t - 1.0)) / 2.0);
    }
    
    static {
        AnimationUtil.PI = 3.141592653589793;
    }
}
