package ez.h.animengine;

public final class Anim
{
    public boolean running;
    public boolean reverse;
    public float timeline;
    public Type type;
    final MathUtil utils;
    public float value;
    public float speed;
    public float endValue;
    
    public void stop() {
        this.running = false;
    }
    
    public void calculateNext() {
        System.out.println(this.timeline);
        Animengine.setTickDelay(500L);
        this.timeline += (float)0.01;
        switch (Anim$1.$SwitchMap$ez$h$animengine$Anim$Type[this.type.ordinal()]) {
            case 1: {
                this.value = this.utils.lerp(this.value, this.endValue, this.speed);
                break;
            }
            case 2: {
                this.value = Easings.easeInSine(this.timeline);
                break;
            }
            case 3: {
                this.value = Easings.easeOutSine(this.timeline);
                break;
            }
            case 4: {
                this.value = Easings.easeInOutSine(this.timeline);
                break;
            }
            case 5: {
                this.value = Easings.easeInQuad(this.timeline);
                break;
            }
            case 6: {
                this.value = Easings.easeOutQuad(this.timeline);
                break;
            }
            case 7: {
                this.value = Easings.easeInOutQuad(this.timeline);
                break;
            }
            case 8: {
                this.value = Easings.easeInQubic(this.timeline);
                break;
            }
            case 9: {
                this.value = Easings.easeOutQubic(this.timeline);
                break;
            }
            case 10: {
                this.value = Easings.easeOutBack(this.timeline);
                break;
            }
        }
    }
    
    public void resetAnimation(final boolean b) {
        this.value = 0.0f;
        if (b) {
            this.stop();
        }
    }
    
    public Anim(final float value, final float endValue, final float n, final Type type) {
        this.utils = new MathUtil();
        this.running = true;
        this.value = value;
        this.endValue = endValue;
        this.speed = this.utils.clamp(n, 0.0f, 1.0f);
        this.type = type;
        this.timeline = 0.0f;
        this.running = false;
        Animengine.registerAnims(this);
    }
    
    public void start() {
        this.running = true;
    }
    
    static final class MathUtil
    {
        public float lerp(final float n, final float n2, final float n3) {
            return n + (n2 - n) * n3;
        }
        
        public float clamp(final float n, final float n2, final float n3) {
            if (n <= n2) {
                return n2;
            }
            if (n >= n3) {
                return n3;
            }
            return n;
        }
    }
    
    public enum Type
    {
        INQUAD, 
        LINEAR, 
        INOUTSINE, 
        INSINE, 
        OUTBACK, 
        INOUTQUAD, 
        OUTQUAD, 
        OUTQUBIC, 
        INQUBIC, 
        OUTSINE;
        
        static {
            final Type[] $values = new Type[0xC9 ^ 0xC3];
            $values[0] = Type.LINEAR;
            $values[1] = Type.INSINE;
            $values[2] = Type.OUTSINE;
            $values[3] = Type.INOUTSINE;
            $values[4] = Type.INQUAD;
            $values[5] = Type.OUTQUAD;
            $values[6] = Type.INOUTQUAD;
            $values[7] = Type.INQUBIC;
            $values[8] = Type.OUTQUBIC;
            $values[9] = Type.OUTBACK;
            $VALUES = $values;
        }
    }
}
