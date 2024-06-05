// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.gui.animation.animations;

import gg.childtrafficking.smokex.gui.element.Element;
import gg.childtrafficking.smokex.gui.animation.Easing;
import gg.childtrafficking.smokex.gui.animation.Animation;

public class SmoothSizeAnimation extends Animation
{
    private final Easing easing;
    
    public SmoothSizeAnimation(final float width, final float height, final long duration, final Easing easing) {
        super(duration);
        this.width = width;
        this.height = height;
        this.easing = easing;
    }
    
    private float easeOutExpo(final double x) {
        return (float)((x == 1.0) ? 1.0 : (1.0 - Math.pow(2.0, -10.0 * x)));
    }
    
    private float easeInExpo(final double x) {
        return (x == 0.0) ? 0.0f : ((float)Math.pow(2.0, 10.0 * x - 10.0));
    }
    
    private float easeInOutExpo(final double x) {
        return (x == 0.0) ? 0.0f : ((float)((x == 1.0) ? 1.0 : ((x < 0.5) ? (Math.pow(2.0, 20.0 * x - 10.0) / 2.0) : ((2.0 - Math.pow(2.0, -20.0 * x + 10.0)) / 2.0))));
    }
    
    @Override
    public boolean process(final double partialTicks, final Element element) {
        if (element.getWidth() == this.width && element.getHeight() == this.height) {
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
            element.setSize(this.width, this.height);
        }
        else {
            element.setSize(this.startWidth + (this.width - this.startWidth) * completion, this.startHeight + (this.height - this.startHeight) * completion);
        }
        return false;
    }
}
