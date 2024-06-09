// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.anime;

public class Animate
{
    private float value;
    private float min;
    private float max;
    private float speed;
    private float time;
    private boolean reversed;
    private Easing ease;
    
    public Animate() {
        this.ease = Easing.LINEAR;
        this.value = 0.0f;
        this.min = 0.0f;
        this.max = 1.0f;
        this.speed = 50.0f;
        this.reversed = false;
    }
    
    public void reset() {
        this.time = this.min;
    }
    
    public Animate update() {
        if (this.reversed) {
            if (this.time > this.min) {
                this.time -= Delta.DELTATIME * 0.001f * this.speed;
            }
        }
        else if (this.time < this.max) {
            this.time += Delta.DELTATIME * 0.001f * this.speed;
        }
        this.time = this.clamp(this.time, this.min, this.max);
        this.value = this.getEase().ease(this.time, this.min, this.max, this.max);
        return this;
    }
    
    public Animate setValue(final float value) {
        this.value = value;
        return this;
    }
    
    public Animate setMin(final float min) {
        this.min = min;
        return this;
    }
    
    public Animate setMax(final float max) {
        this.max = max;
        return this;
    }
    
    public Animate setSpeed(final float speed) {
        this.speed = speed;
        return this;
    }
    
    public Animate setReversed(final boolean reversed) {
        this.reversed = reversed;
        return this;
    }
    
    public Animate setEase(final Easing ease) {
        this.ease = ease;
        return this;
    }
    
    public float getValue() {
        return this.value;
    }
    
    public float getMin() {
        return this.min;
    }
    
    public float getMax() {
        return this.max;
    }
    
    public float getSpeed() {
        return this.speed;
    }
    
    public boolean isReversed() {
        return this.reversed;
    }
    
    public Easing getEase() {
        return this.ease;
    }
    
    private float clamp(final float num, final float min, final float max) {
        return (num < min) ? min : ((num > max) ? max : num);
    }
}
