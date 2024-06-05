// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.gui.animation.animations;

import gg.childtrafficking.smokex.gui.element.Element;
import gg.childtrafficking.smokex.gui.animation.Easing;
import gg.childtrafficking.smokex.gui.animation.Animation;

public class SmoothMoveAnimation extends Animation
{
    private final Easing easing;
    
    public SmoothMoveAnimation(final float x, final float y, final long duration, final Easing easing) {
        super(duration);
        this.x = x;
        this.y = y;
        this.easing = easing;
    }
    
    private float easeOutExpo(final double x) {
        return (float)(1.0 - Math.pow(1.0 - x, 4.0));
    }
    
    private float easeInExpo(final double x) {
        return (x == 0.0) ? 0.0f : ((float)Math.pow(2.0, 10.0 * x - 10.0));
    }
    
    private float easeInOutExpo(final double x) {
        return (x == 0.0) ? 0.0f : ((float)((x == 1.0) ? 1.0 : ((x < 0.5) ? (Math.pow(2.0, 20.0 * x - 10.0) / 2.0) : ((2.0 - Math.pow(2.0, -20.0 * x + 10.0)) / 2.0))));
    }
    
    @Override
    public boolean process(final double partialTicks, final Element element) {
        if (element.isAtPosition(this.x, this.y)) {
            return true;
        }
        float completion = Math.min(this.timerUtil.elapsed() / (float)this.duration, 1.0f);
        switch (this.easing) {
            case EASE_OUT: {
                completion = this.easeOutExpo(completion);
                break;
            }
            case EASE_IN: {
                completion = this.easeInExpo(completion);
                break;
            }
            case EASE_IN_OUT: {
                completion = this.easeInOutExpo(completion);
                break;
            }
        }
        if (completion == 1.0f) {
            element.setPosition(this.x, this.y);
        }
        else {
            element.setPosition(this.startX + (this.x - this.startX) * completion, this.startY + (this.y - this.startY) * completion);
        }
        return false;
    }
}
