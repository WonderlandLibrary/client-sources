// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.anime;

public interface Easing
{
    public static final Easing LINEAR = (t, b, c, d) -> c * t / d + b;
    public static final Easing QUAD_IN = (t, b, c, d) -> {
        t /= d;
        return c * n * t + b;
    };
    public static final Easing QUAD_OUT = (t, b, c, d) -> {
        t /= d;
        return o * o2 * (t - 2.0f) + b;
    };
    public static final Easing QUAD_IN_OUT = (t, b, c, d) -> {
        t /= d / 2.0f;
        if (o3 < 1.0f) {
            return c / 2.0f * t * t + b;
        }
        else {
            --t;
            return n2 * (n3 * (t - 2.0f) - 1.0f) + b;
        }
    };
    public static final Easing CUBIC_IN = (t, b, c, d) -> {
        t /= d;
        return c * n4 * t * t + b;
    };
    public static final Easing CUBIC_OUT = (t, b, c, d) -> {
        t = t / d - 1.0f;
        return c * (o4 * t * t + 1.0f) + b;
    };
    public static final Easing CUBIC_IN_OUT = (t, b, c, d) -> {
        t /= d / 2.0f;
        if (o5 < 1.0f) {
            return c / 2.0f * t * t * t + b;
        }
        else {
            t -= 2.0f;
            return o6 * (o7 * t * t + 2.0f) + b;
        }
    };
    public static final Easing QUARTIC_IN = (t, b, c, d) -> {
        t /= d;
        return c * n5 * t * t * t + b;
    };
    public static final Easing QUARTIC_OUT = (t, b, c, d) -> {
        t = t / d - 1.0f;
        return o8 * (o9 * t * t * t - 1.0f) + b;
    };
    public static final Easing QUARTIC_IN_OUT = (t, b, c, d) -> {
        t /= d / 2.0f;
        if (o10 < 1.0f) {
            return c / 2.0f * t * t * t * t + b;
        }
        else {
            t -= 2.0f;
            return o11 * (o12 * t * t * t - 2.0f) + b;
        }
    };
    public static final Easing QUINTIC_IN = (t, b, c, d) -> {
        t /= d;
        return c * n6 * t * t * t * t + b;
    };
    public static final Easing QUINTIC_OUT = (t, b, c, d) -> {
        t = t / d - 1.0f;
        return c * (o13 * t * t * t * t + 1.0f) + b;
    };
    public static final Easing QUINTIC_IN_OUT = (t, b, c, d) -> {
        t /= d / 2.0f;
        if (o14 < 1.0f) {
            return c / 2.0f * t * t * t * t * t + b;
        }
        else {
            t -= 2.0f;
            return o15 * (o16 * t * t * t * t + 2.0f) + b;
        }
    };
    public static final Easing SINE_IN = (t, b, c, d) -> -c * (float)Math.cos(t / d * 1.5707963267948966) + c + b;
    public static final Easing SINE_OUT = (t, b, c, d) -> c * (float)Math.sin(t / d * 1.5707963267948966) + b;
    public static final Easing SINE_IN_OUT = (t, b, c, d) -> -c / 2.0f * ((float)Math.cos(3.141592653589793 * t / d) - 1.0f) + b;
    public static final Easing EXPO_IN = (t, b, c, d) -> (t == 0.0f) ? b : (c * (float)Math.pow(2.0, 10.0f * (t / d - 1.0f)) + b);
    public static final Easing EXPO_OUT = (t, b, c, d) -> (t == d) ? (b + c) : (c * (-(float)Math.pow(2.0, -10.0f * t / d) + 1.0f) + b);
    public static final Easing EXPO_IN_OUT = (t, b, c, d) -> {
        if (t == 0.0f) {
            return b;
        }
        else if (t == d) {
            return b + c;
        }
        else {
            t /= d / 2.0f;
            if (o17 < 1.0f) {
                return c / 2.0f * (float)Math.pow(2.0, 10.0f * (t - 1.0f)) + b;
            }
            else {
                --t;
                return n7 * (-(float)Math.pow(a, (double)(o18 * o19)) + 2.0f) + b;
            }
        }
    };
    public static final Easing CIRC_IN = (t, b, c, d) -> {
        t /= d;
        return n8 * ((float)Math.sqrt((double)(o20 - o21 * t)) - 1.0f) + b;
    };
    public static final Easing CIRC_OUT = (t, b, c, d) -> {
        t = t / d - 1.0f;
        return c * (float)Math.sqrt((double)(o22 - o23 * t)) + b;
    };
    public static final Easing CIRC_IN_OUT = (t, b, c, d) -> {
        t /= d / 2.0f;
        if (o24 < 1.0f) {
            return -c / 2.0f * ((float)Math.sqrt(1.0f - t * t) - 1.0f) + b;
        }
        else {
            t -= 2.0f;
            return n9 * ((float)Math.sqrt((double)(o25 - o26 * t)) + 1.0f) + b;
        }
    };
    public static final Elastic ELASTIC_IN = new ElasticIn();
    public static final Elastic ELASTIC_OUT = new ElasticOut();
    public static final Elastic ELASTIC_IN_OUT = new ElasticInOut();
    public static final Back BACK_IN = new BackIn();
    public static final Back BACK_OUT = new BackOut();
    public static final Back BACK_IN_OUT = new BackInOut();
    public static final Easing BOUNCE_OUT = (t, b, c, d) -> {
        t /= d;
        if (o27 < 0.36363637f) {
            return c * (7.5625f * t * t) + b;
        }
        else if (t < 0.72727275f) {
            t -= 0.54545456f;
            return c * (o28 * o29 * t + 0.75f) + b;
        }
        else if (t < 0.90909094f) {
            t -= 0.8181818f;
            return c * (o30 * o31 * t + 0.9375f) + b;
        }
        else {
            t -= 0.95454544f;
            return c * (o32 * o33 * t + 0.984375f) + b;
        }
    };
    public static final Easing BOUNCE_IN = (t, b, c, d) -> c - Easing.BOUNCE_OUT.ease(d - t, 0.0f, c, d) + b;
    public static final Easing BOUNCE_IN_OUT = (t, b, c, d) -> {
        if (t < d / 2.0f) {
            return Easing.BOUNCE_IN.ease(t * 2.0f, 0.0f, c, d) * 0.5f + b;
        }
        else {
            return Easing.BOUNCE_OUT.ease(t * 2.0f - d, 0.0f, c, d) * 0.5f + c * 0.5f + b;
        }
    };
    
    float ease(final float p0, final float p1, final float p2, final float p3);
    
    default static {
        final float n;
        final Object o;
        final Object o2;
        final Object o3;
        final float n2;
        final float n3;
        final float n4;
        final Object o4;
        final Object o5;
        final Object o6;
        final Object o7;
        final float n5;
        final Object o8;
        final Object o9;
        final Object o10;
        final Object o11;
        final Object o12;
        final float n6;
        final Object o13;
        final Object o14;
        final Object o15;
        final Object o16;
        final Object o17;
        final float n7;
        final double a;
        final Object o18;
        final Object o19;
        final float n8;
        final Object o20;
        final Object o21;
        final Object o22;
        final Object o23;
        final Object o24;
        final float n9;
        final Object o25;
        final Object o26;
        final Object o27;
        final Object o28;
        final Object o29;
        final Object o30;
        final Object o31;
        final Object o32;
        final Object o33;
    }
    
    public abstract static class Elastic implements Easing
    {
        private float amplitude;
        private float period;
        
        public Elastic(final float amplitude, final float period) {
            this.amplitude = amplitude;
            this.period = period;
        }
        
        public Elastic() {
            this(-1.0f, 0.0f);
        }
        
        public float getPeriod() {
            return this.period;
        }
        
        public void setPeriod(final float period) {
            this.period = period;
        }
        
        public float getAmplitude() {
            return this.amplitude;
        }
        
        public void setAmplitude(final float amplitude) {
            this.amplitude = amplitude;
        }
    }
    
    public static class ElasticIn extends Elastic
    {
        public ElasticIn(final float amplitude, final float period) {
            super(amplitude, period);
        }
        
        public ElasticIn() {
        }
        
        @Override
        public float ease(float t, final float b, final float c, final float d) {
            float a = this.getAmplitude();
            float p = this.getPeriod();
            if (t == 0.0f) {
                return b;
            }
            if ((t /= d) == 1.0f) {
                return b + c;
            }
            if (p == 0.0f) {
                p = d * 0.3f;
            }
            float s = 0.0f;
            if (a < Math.abs(c)) {
                a = c;
                s = p / 4.0f;
            }
            else {
                s = p / 6.2831855f * (float)Math.asin(c / a);
            }
            return -(a * (float)Math.pow(2.0, 10.0f * --t) * (float)Math.sin((t * d - s) * 6.283185307179586 / p)) + b;
        }
    }
    
    public static class ElasticOut extends Elastic
    {
        public ElasticOut(final float amplitude, final float period) {
            super(amplitude, period);
        }
        
        public ElasticOut() {
        }
        
        @Override
        public float ease(float t, final float b, final float c, final float d) {
            float a = this.getAmplitude();
            float p = this.getPeriod();
            if (t == 0.0f) {
                return b;
            }
            if ((t /= d) == 1.0f) {
                return b + c;
            }
            if (p == 0.0f) {
                p = d * 0.3f;
            }
            float s = 0.0f;
            if (a < Math.abs(c)) {
                a = c;
                s = p / 4.0f;
            }
            else {
                s = p / 6.2831855f * (float)Math.asin(c / a);
            }
            return a * (float)Math.pow(2.0, -10.0f * t) * (float)Math.sin((t * d - s) * 6.283185307179586 / p) + c + b;
        }
    }
    
    public static class ElasticInOut extends Elastic
    {
        public ElasticInOut(final float amplitude, final float period) {
            super(amplitude, period);
        }
        
        public ElasticInOut() {
        }
        
        @Override
        public float ease(float t, final float b, final float c, final float d) {
            float a = this.getAmplitude();
            float p = this.getPeriod();
            if (t == 0.0f) {
                return b;
            }
            if ((t /= d / 2.0f) == 2.0f) {
                return b + c;
            }
            if (p == 0.0f) {
                p = d * 0.45000002f;
            }
            float s = 0.0f;
            if (a < Math.abs(c)) {
                a = c;
                s = p / 4.0f;
            }
            else {
                s = p / 6.2831855f * (float)Math.asin(c / a);
            }
            if (t < 1.0f) {
                return -0.5f * (a * (float)Math.pow(2.0, 10.0f * --t) * (float)Math.sin((t * d - s) * 6.283185307179586 / p)) + b;
            }
            return a * (float)Math.pow(2.0, -10.0f * --t) * (float)Math.sin((t * d - s) * 6.283185307179586 / p) * 0.5f + c + b;
        }
    }
    
    public abstract static class Back implements Easing
    {
        public static final float DEFAULT_OVERSHOOT = 1.70158f;
        private float overshoot;
        
        public Back() {
            this(1.70158f);
        }
        
        public Back(final float overshoot) {
            this.overshoot = overshoot;
        }
        
        public float getOvershoot() {
            return this.overshoot;
        }
        
        public void setOvershoot(final float overshoot) {
            this.overshoot = overshoot;
        }
    }
    
    public static class BackIn extends Back
    {
        public BackIn() {
        }
        
        public BackIn(final float overshoot) {
            super(overshoot);
        }
        
        @Override
        public float ease(float t, final float b, final float c, final float d) {
            final float s = this.getOvershoot();
            return c * (t /= d) * t * ((s + 1.0f) * t - s) + b;
        }
    }
    
    public static class BackOut extends Back
    {
        public BackOut() {
        }
        
        public BackOut(final float overshoot) {
            super(overshoot);
        }
        
        @Override
        public float ease(float t, final float b, final float c, final float d) {
            final float s = this.getOvershoot();
            return c * ((t = t / d - 1.0f) * t * ((s + 1.0f) * t + s) + 1.0f) + b;
        }
    }
    
    public static class BackInOut extends Back
    {
        public BackInOut() {
        }
        
        public BackInOut(final float overshoot) {
            super(overshoot);
        }
        
        @Override
        public float ease(float t, final float b, final float c, final float d) {
            float s = this.getOvershoot();
            if ((t /= d / 2.0f) < 1.0f) {
                return c / 2.0f * (t * t * (((s *= (float)1.525) + 1.0f) * t - s)) + b;
            }
            return c / 2.0f * ((t -= 2.0f) * t * (((s *= (float)1.525) + 1.0f) * t + s) + 2.0f) + b;
        }
    }
}
