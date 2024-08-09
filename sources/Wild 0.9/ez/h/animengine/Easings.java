package ez.h.animengine;

public class Easings
{
    public static float easeOutQubic(final float n) {
        return (float)(1.0 - Math.pow(1.0f - n, 3.0));
    }
    
    public static float easeOutQuad(final float n) {
        return 1.0f - (1.0f - n) * (1.0f - n);
    }
    
    public static float easeInQubic(final float n) {
        return n * n * n;
    }
    
    public static float easeInSine(final float n) {
        return (float)(1.0 - Math.cos(n * 3.141592653589793 / 2.0));
    }
    
    public static float easeOutSine(final float n) {
        return (float)Math.sin(n * 3.141592653589793 / 2.0);
    }
    
    public static float easeOutBack2(final float n) {
        return (float)(1.0 + 2.0915799140930176 * Math.pow(n - 1.0f, 3.0) + 1.0915800333023071 * Math.pow(n - 1.0f, 2.0));
    }
    
    public static float easeInOutQuart(final float n) {
        return (n < 0.5f) ? (8.0f * n * n * n * n) : ((float)(1.0 - Math.pow(-2.0f * n + 2.0f, 4.0) / 2.0));
    }
    
    public static float easeInOutSine(final float n) {
        return (float)(-(Math.cos(3.141592653589793 * n) - 1.0) / 2.0);
    }
    
    public static float easeOutExpo(final float n) {
        return (n == 1.0f) ? 1.0f : ((float)(1.0 - Math.pow(2.0, -10.0f * n)));
    }
    
    public static float easeOutBack(final float n) {
        return (float)(1.0 + 2.201580047607422 * Math.pow(n - 1.0f, 3.0) + 1.2015800476074219 * Math.pow(n - 1.0f, 2.0));
    }
    
    public static float easeInQuad(final float n) {
        return n * n;
    }
    
    public static float easeInOutCubic(final float n) {
        return (n < 0.5) ? (4.0f * n * n * n) : ((float)(1.0 - Math.pow(-2.0f * n + 2.0f, 3.0) / 2.0));
    }
    
    public static float easeInOutQuad(final float n) {
        return (n < 0.5) ? (2.0f * n * n) : ((float)(1.0 - Math.pow(-2.0f * n + 2.0f, 2.0) / 2.0));
    }
}
